## medge-backend
- Description : 낭만웨딩 플랫폼 벡엔드
- Developer : [Maintainers](/MAINTAINERS.md)

---

### Prerequisite
- Name : nw-backend
- Language : Java
- Build System : Gradle v8.2.1
- Gradle DSL : Groovy
- JDK : Eclipse Temurin (AdoptOpenJDK HotSpot v17.0.7)

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
  testImplementation 'org.mockito:mockito-core:2.8.8'
  testImplementation 'org.junit.jupiter:junit-jupiter-api:5.8.1'
  testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.8.1'
  developmentOnly 'org.springframework.boot:spring-boot-devtools:3.0.2'
}
```
#### Project
```
dependencies {
  implementation 'org.springframework.boot:spring-boot-starter-web:3.0.2'
  implementation 'org.springframework.boot:spring-boot-starter-security:3.0.2'
  implementation 'org.springframework.security:spring-security-config:6.0.1'
  implementation 'org.springframework.boot:spring-boot-starter-data-mongodb:2.5.4'
  implementation 'org.mongodb:mongodb-driver-sync:4.8.2'
  implementation 'org.springframework.boot:spring-boot-starter-validation:3.0.2'
  implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0'
  implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
  runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.5'
  runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.5'
}
```

#### Tree
```
taking@develop:~/nw-backend$ tree .
.
├── Dockerfile
├── LICENSE
├── MAINTAINERS.md
├── README.md
├── application.properties_docker
├── build.gradle
├── docker-compose.yml
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── nw
│   ├── build.gradle
│   └── src
│       └── main
│           ├── java
│           │   └── lab
│           │       └── cherry
│           │           └── nw
│           │               ├── NwApplication.java
│           │               ├── configuration
│           │               │   ├── Initalizer.java
│           │               │   ├── WebSecurityConfiguration.java
│           │               │   ├── bean
│           │               │   │   ├── BeanConfig.java
│           │               │   │   ├── MongoConfig.java
│           │               │   │   └── OpenApiConfig.java
│           │               │   └── filter
│           │               │       ├── JwtFilter.java
│           │               │       └── RequestLoggingFilter.java
│           │               ├── controller
│           │               │   ├── AuthController.java
│           │               │   ├── OrgController.java
│           │               │   └── UserController.java
│           │               ├── error
│           │               │   ├── ErrorResponse.java
│           │               │   ├── ResultResponse.java
│           │               │   ├── enums
│           │               │   │   ├── ErrorCode.java
│           │               │   │   └── SuccessCode.java
│           │               │   ├── exception
│           │               │   │   ├── CustomException.java
│           │               │   │   └── EntityNotFoundException.java
│           │               │   └── handler
│           │               │       ├── CustomAccessDeniedHandler.java
│           │               │       ├── GlobalExceptionHandler.java
│           │               │       └── UnauthorizedHandler.java
│           │               ├── model
│           │               │   ├── OrgEntity.java
│           │               │   ├── RoleEntity.java
│           │               │   └── UserEntity.java
│           │               ├── repository
│           │               │   ├── OrgRepository.java
│           │               │   ├── RoleRepository.java
│           │               │   └── UserRepository.java
│           │               ├── service
│           │               │   ├── AuthService.java
│           │               │   ├── Impl
│           │               │   │   ├── AuthServiceImpl.java
│           │               │   │   ├── OrgServiceImpl.java
│           │               │   │   ├── RoleServiceImpl.java
│           │               │   │   ├── TokenServiceImpl.java
│           │               │   │   └── UserServiceImpl.java
│           │               │   ├── OrgService.java
│           │               │   ├── RoleService.java
│           │               │   ├── TokenService.java
│           │               │   ├── UserService.java
│           │               │   └── security
│           │               │       └── CustomUserDetailsService.java
│           │               └── util
│           │                   ├── Common.java
│           │                   ├── FormatConverter.java
│           │                   └── Security
│           │                       ├── AccessToken.java
│           │                       └── SecretKey.java
│           └── resources
│               ├── application.properties
│               ├── application.properties_sample
│               └── static
│                   └── index.html
└── settings.gradle

26 directories, 57 files
```


### How-to
#### First
 - roles : ROLE_ADMIN, ROLE_USER 와 같이 등록


#### Swagger
 - 

