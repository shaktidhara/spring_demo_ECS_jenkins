FROM java:8
ADD /target/spring_demo*.jar spring_demo.jar
ENTRYPOINT exec java -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.port=3333 ${JAVA_OPTS:--Xmx256m} -jar /spring_demo.jar
