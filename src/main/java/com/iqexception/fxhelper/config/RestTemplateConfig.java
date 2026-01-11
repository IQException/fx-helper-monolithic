package com.iqexception.fxhelper.config;


import org.apache.hc.client5.http.HttpRoute;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.HttpHost;

import org.apache.hc.core5.util.TimeValue;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


@Configuration
public class RestTemplateConfig {


    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public RestTemplateCustomizer getRestTemplateCustomizer() {
        return new AppRestTemplateCustomizer();
    }

    private static class AppRestTemplateCustomizer implements RestTemplateCustomizer {
        @Override
        public void customize(RestTemplate restTemplate) {
            restTemplate.setRequestFactory(clientHttpRequestFactory());
            restTemplate.getInterceptors().add(new CustomClientHttpRequestInterceptor());
        }

        public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();

            clientHttpRequestFactory.setHttpClient(httpClient());
            return clientHttpRequestFactory;
        }


        //region config httpclient
        private final Logger LOG = LoggerFactory.getLogger(RestTemplateConfig.class);

        private static final int MAX_TOTAL_CONNECTIONS = 2048;

        private static final int MAX_ROUTE_CONNECTIONS = 32;

        private static final int MAX_LOCALHOST_CONNECTIONS = 64;

        private static final int CONNECT_TIMEOUT = 1000;

        private static final int REQUEST_TIMEOUT = 1000;

        private static final int SOCKET_TIMEOUT = 1000;


        public PoolingHttpClientConnectionManager poolingConnectionManager() {

            PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();

            // set a total amount of connections across all HTTP routes
            poolingConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
            // set a maximum amount of connections for each HTTP route in pool
            poolingConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

            // increase the amounts of connections if the host is localhost
            HttpHost localhost = new HttpHost("http://localhost", 8080);
            poolingConnectionManager.setMaxPerRoute(new HttpRoute(localhost), MAX_LOCALHOST_CONNECTIONS);

            return poolingConnectionManager;
        }


        public CloseableHttpClient httpClient() {
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(Timeout.ofMilliseconds(CONNECT_TIMEOUT))
                    .setConnectionRequestTimeout(Timeout.ofMilliseconds(REQUEST_TIMEOUT)).
                    setResponseTimeout(Timeout.ofMilliseconds(SOCKET_TIMEOUT)).build();
            return HttpClients.custom().setDefaultRequestConfig(requestConfig)
                    .setConnectionManager(poolingConnectionManager()).evictIdleConnections(TimeValue.of(30, TimeUnit.SECONDS)).build();
        }

        //endregion
    }

    private static class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

        private final Logger LOG = LoggerFactory.getLogger(CustomClientHttpRequestInterceptor.class);

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

            // log the http request
            LOG.info("URI: {}, Method: {}, Headers: {},Params:{}", request.getURI(),
                    request.getMethod().name(), request.getHeaders(), request.getURI().getQuery());
            LOG.debug("Request Body :{}", new String(body, StandardCharsets.UTF_8));
            ClientHttpResponse response = execution.execute(request, body);
            LOG.info("Status: {},Headers: {}", response.getStatusCode(), response.getHeaders());
            return response;
        }

    }
}
