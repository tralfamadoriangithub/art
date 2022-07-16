# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.1/maven-plugin/reference/html/#build-image)
* [Quartz Scheduler](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#io.quartz)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#messaging.kafka)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#data.sql.jpa-and-spring-data)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### REST

C:\Users\sligh>curl localhost:8080/info/3 -- Return null
                                          -- Return null result

C:\Users\sligh>curl localhost:8080/info/3 -- Return new
{"id":null}                               -- Return new result

### Spring JPA
https://java-master.com/spring-boot-%D0%BF%D1%80%D0%B8%D0%BC%D0%B5%D1%80-%D1%81-postgres-%D0%B8-jpa/

### Docker

POSTGRES

https://habr.com/ru/post/578744/

docker run --name [ContainerName] -e POSTGRES_PASSWORD=[password] -d postgres

docker run --name [ContainerName] -p 5432:5432 -e POSTGRES_PASSWORD=[password] -d postgres