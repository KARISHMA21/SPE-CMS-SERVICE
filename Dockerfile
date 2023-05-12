FROM openjdk:18
ADD target/cms_service-0.0.1-SNAPSHOT.jar cms_service-docker.jar
ENTRYPOINT ["java","-jar","/cms_service-docker.jar"]

