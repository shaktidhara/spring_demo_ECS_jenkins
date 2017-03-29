FROM java:8
ADD /target/spring_demo*.jar spring_demo.jar
ENTRYPOINT exec java ${JAVA_OPTS:--Xmx1g} -jar /spring_demo.jar
