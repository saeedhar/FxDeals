FROM openjdk:17
COPY target/clustered.data.warehouse-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java","-jar","clustered.data.warehouse-0.0.1-SNAPSHOT.jar"]