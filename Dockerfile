#
FROM openjdk:17
EXPOSE 8084
COPY ./target/cms_service.jar ./
WORKDIR ./
ENTRYPOINT ["java","-jar","/cms_service.jar"]
#EXPOSE 8082
#ADD target/admin_service-jar-with-dependencies.jar admin_service-jar-with-dependencies.jar
#