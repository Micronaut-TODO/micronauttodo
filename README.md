# Micronaut TODO

## Testing

### JIT Tests

To run tests, execute the gradle task: 

```
./gradlew test
```

## Local Development



## JIT 



### Test Coverage

```
./gradlew jacocoTestReport
```

### Run the application locally

```
./gradlew run
```

### Run a FAT JAR

```
./gradlew shadowJar
```

Start a MySQL database with: 

```
docker run -it --rm -p 3307:3306 -e MYSQL_DATABASE=db -e MYSQL_USER=sherlock -e MYSQL_PASSWORD=elementary -e MYSQL_ALLOW_EMPTY_PASSWORD=true mysql:8
```

Start the application with environment variables pointing to the database: 

```
export DATASOURCES_DEFAULT_URL=jdbc:mysql://localhost:3307/db
export DATASOURCES_DEFAULT_USERNAME=sherlock
export DATASOURCES_DEFAULT_PASSWORD=elementary
java -jar build/libs/micronauttodo-0.1-all.jar 
```

## GraalVM

### Native Image Testing

To run native tests execute: 

```
sdk use java 21.0.4-graal
./gradlew nativeTest
```

## Native Image Run 

Generate the GraalVM native image of the application with: 

```
sdk use java 21.0.4-graal
./gradlew nativeCompile
```

Start a MySQL database with:

```
docker run -it --rm -p 3307:3306 -e MYSQL_DATABASE=db -e MYSQL_USER=sherlock -e MYSQL_PASSWORD=elementary -e MYSQL_ALLOW_EMPTY_PASSWORD=true mysql:8
```

Start the application with environment variables pointing to the database:

```
export DATASOURCES_DEFAULT_URL=jdbc:mysql://localhost:3307/db
export DATASOURCES_DEFAULT_USERNAME=sherlock
export DATASOURCES_DEFAULT_PASSWORD=elementary
build/native/nativeCompile/micronauttodo 
```


## Documentation

### Gradle Plugins
- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)

### Micronaut Framework

- [User Guide](https://docs.micronaut.io/latest/guide/index.html)
- [API Reference](https://docs.micronaut.io/latest/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/latest/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

#### Micronaut Modules

- [Micronaut JTE Views documentation](https://micronaut-projects.github.io/micronaut-views/latest/guide/#jte)
- [Micronaut Security documentation](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html)
- [Micronaut Data JDBC documentation](https://micronaut-projects.github.io/micronaut-data/latest/guide/index.html#jdbc)
- [Micronaut Serialization documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)
- [Micronaut Test Resources documentation](https://micronaut-projects.github.io/micronaut-test-resources/latest/guide/)
- [Micronaut Hikari JDBC Connection Pool documentation](https://micronaut-projects.github.io/micronaut-sql/latest/guide/index.html#jdbc)
- [Micronaut OpenAPI Support documentation](https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html)

- [https://www.openapis.org](https://www.openapis.org)
- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)
- [Micronaut Flyway Database Migration documentation](https://micronaut-projects.github.io/micronaut-flyway/latest/guide/index.html)

## Third-party dependencies
- [https://jte.gg/](https://jte.gg/)
- [https://flywaydb.org/](https://flywaydb.org/)
