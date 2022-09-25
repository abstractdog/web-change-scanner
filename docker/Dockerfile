FROM maven:3.5.2-jdk-8-alpine AS maven_build
MAINTAINER bodorlaszlo0202@gmail.com
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn -s settings.xml clean package

FROM tomcat:8.5.50-jdk8-openjdk-slim AS tomcat

FROM abstractdog/web-change-scanner
COPY --from=tomcat /usr/local/tomcat/ /usr/local/tomcat/
COPY --from=tomcat /usr/local/openjdk-8 /usr/local/openjdk-8
COPY --from=maven_build /build/target/web-change-scanner.war /usr/local/tomcat/webapps/web-change-scanner.war

ENV CATALINA_HOME /usr/local/tomcat
ENV JAVA_HOME /usr/local/openjdk-8
ENV PATH $JAVA_HOME/bin:$CATALINA_HOME/bin:$PATH
CMD ["catalina.sh", "run"]

