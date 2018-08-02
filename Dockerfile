FROM uken/microservices-jre-docker-alpine:v1.6.0
ADD /target/spring_demo*.jar spring_demo.jar
ENTRYPOINT exec java -agentpath:/libyjpagent.so=port=10001,exceptions=disable ${JAVA_OPTS:--Xmx256m} -jar /spring_demo.jar
