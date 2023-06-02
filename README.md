## 낭만웨딩(?) Backend

---

### Prerequisite
- Name : 낭만웨딩(?) Backend
- Language : Java
- Build System : Gradle v7.4
- Gradle DSL : Groovy
- JDK : Eclipse Temurin (AdoptOpenJDK HotSpot v17.0.6)

### Plugins
```
plugins {
    id 'org.springframework.boot' version '3.0.2'
    id 'io.spring.dependency-management' version '1.1.0'
    id 'idea'
    id 'java'
}
```

### Dependencies
#### Common
```
  dependencies {
        compileOnly 'org.projectlombok:lombok:1.18.26'
        annotationProcessor 'org.projectlombok:lombok:1.18.26'
        testImplementation 'org.springframework.boot:spring-boot-starter-test:3.0.2'
        testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
        testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
        developmentOnly 'org.springframework.boot:spring-boot-devtools:3.0.2'
    }
```
#### Project
```
dependencies {
    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-web', version: '3.0.2'
    implementation group: 'org.springframework.boot', name: 'spring-boot-gradle-plugin', version: '3.0.2'
    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-security', version: '3.0.2'
    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-data-jpa', version: '3.0.2'
    implementation group: 'org.springdoc', name: 'springdoc-openapi-starter-webmvc-ui', version: '2.0.2'
    runtimeOnly group: 'org.mariadb.jdbc', name: 'mariadb-java-client', version: '3.1.2'
}
```

### How-to
#### Swagger
 - 

