package com.iqexception.fxhelper.config;

import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteType;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultExecuteListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class JooqConfig {

//    @Bean
//    public DSLContext dslContext(org.jooq.Configuration configuration) {
//        return configuration.dsl();
//    }

    @Bean
    public DefaultConfigurationCustomizer configureJooq(){
        return configuration ->
                configuration.setExecuteListener(new StatisticsListener(), new PrettyPrinter(),new DeleteOrUpdateWithoutWhereListener());
    }
    // Extending DefaultExecuteListener, which provides empty implementations for all methods...
    public static class StatisticsListener extends DefaultExecuteListener {

        /**
         * Generated UID
         */
        private static final long                     serialVersionUID = 7399239846062763212L;

        public static final Map<ExecuteType, Integer> STATISTICS       = new ConcurrentHashMap<>();

        @Override
        public void start(ExecuteContext ctx) {
            STATISTICS.compute(ctx.type(), (k, v) -> v == null ? 1 : v + 1);
        }
    }
    private class PrettyPrinter extends DefaultExecuteListener {

        private final Logger LOG = LoggerFactory.getLogger(getClass());


        /**
         * Hook into the query execution lifecycle before executing queries
         */
        @Override
        public void executeStart(ExecuteContext ctx) {

            // Create a new DSLContext for logging rendering purposes
            // This DSLContext doesn't need a connection, only the SQLDialect...
            DSLContext create = DSL.using(ctx.dialect(),

                    // ... and the flag for pretty-printing
                    new Settings().withRenderFormatted(true));

            // If we're executing a query
            if (ctx.query() != null) {
                LOG.debug(create.renderInlined(ctx.query()));
            }

            // If we're executing a routine
            else if (ctx.routine() != null) {
                LOG.debug(create.renderInlined(ctx.routine()));
            }
        }
    }
    private class DeleteOrUpdateWithoutWhereListener extends DefaultExecuteListener {
        @Override
        public void renderEnd(ExecuteContext ctx) {
            if (ctx.sql().matches("^(?i:(UPDATE|DELETE)(?!.* WHERE ).*)$")) {
                throw new RuntimeException("DELETE OR UPDATE WITHOUT WHERE CLAUSE! sql:" + ctx.sql());
            }
        }
    }
}
