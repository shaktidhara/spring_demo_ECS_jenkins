FROM openjdk:8
RUN mkdir /spring_demo
ADD target/spring_demo.jar /spring_demo
WORKDIR /spring_demo
CMD java -jar spring_demo.jar
