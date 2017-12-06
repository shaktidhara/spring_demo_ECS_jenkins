FROM uken/microservices-jre-docker-alpine:v1.4.0
ADD /target/spring_demo*.jar spring_demo.jar
ADD /libyjpagent.so libyjpagent.so
ENTRYPOINT exec java -agentpath:/libyjpagent.so ${JAVA_OPTS:--Xmx256m} -jar /spring_demo.jar
