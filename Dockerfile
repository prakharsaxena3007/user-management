FROM openjdk:17
WORKDIR /opt
ENV PORT 8081
EXPOSE 8081
COPY target/*.jar /opt/userManagementApp.jar
ENTRYPOINT exec java $JAVA_OPTS -jar userManagementApp.jar
