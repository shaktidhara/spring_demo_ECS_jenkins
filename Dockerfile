FROM java:8
ADD /target/spring_demo*.jar spring_demo.jar
ADD /glowroot/glowroot.jar glowroot.jar
ADD /glowroot/config/admin.json admin.json
ENTRYPOINT exec java ${JAVA_OPTS:--Xmx256m} -jar /spring_demo.jar
