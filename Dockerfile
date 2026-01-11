FROM amazoncorretto:8-alpine

ADD target/*.jar /fx-helper.jar

ENTRYPOINT java -jar /fx-helper.jar

EXPOSE 8080
EXPOSE 443

# docker run --name fx -d -p 8080:443 -v $HOME:/var/db fx-helper