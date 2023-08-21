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

#### Tree
```
ubuntu@oracle:~/nw-backend$ tree .
.
├── README.md
├── nw
│   ├── build.gradle
│   └── src
│       └── main
│          ├── java
│          │   └── lab
│          │      └── cherry
│          │          └── nw
│          │              ├── NwApplication.java
│          │              ├── configuration
│          │              │   ├── bean
│          │              │   │   ├── BeanConfig.java
│          │              │   │   └── CorsConfig.java
│          │              │   └── security
│          │              │      ├── WebSecurityConfiguration.java
│          │              │      └── jwt
│          │              │          ├── CustomAccessDeniedHandler.java
│          │              │          ├── JwtFilter.java
│          │              │          └── UnauthorizedHandler.java
│          │              ├── controller
│          │              │   ├── AuthController.java
│          │              │   └── UserController.java
│          │              ├── error
│          │              │   ├── ErrorResponse.java
│          │              │   ├── enums
│          │              │   │   └── ErrorCode.java
│          │              │   ├── exception
│          │              │   │   ├── CustomException.java
│          │              │   │   └── EntityNotFoundException.java
│          │              │   └── handler
│          │              │      └── GlobalExceptionHandler.java
│          │              ├── model
│          │              │   ├── BaseEntity.java
│          │              │   ├── RoleEntity.java
│          │              │   ├── UserEntity.java
│          │              │   └── dto
│          │              │      ├── UserLoginDto.java
│          │              │      └── UserRegisterDto.java
│          │              ├── repository
│          │              │   ├── RoleRepository.java
│          │              │   └── UserRepository.java
│          │              ├── service
│          │              │   ├── AuthService.java
│          │              │   ├── Impl
│          │              │   │   ├── AuthServiceImpl.java
│          │              │   │   └── UserServiceImpl.java
│          │              │   ├── UserService.java
│          │              │   └── security
│          │              │      └── CustomUserDetailsService.java
│          │              └── util
│          │                  ├── FormatConverter.java
│          │                  └── Security
│          │                      ├── AccessToken.java
│          │                      ├── SecretKey.java
│          │                      └── jwt
│          │                          ├── IJwtTokenHelper.java
│          │                          ├── IJwtTokenProvider.java
│          │                          ├── JwtTokenHelper.java
│          │                          └── JwtTokenProvider.java
│          └── resources
│              ├── application.properties_sample
│              └── static
│                  └── index.html
├── .gitignore
├── application.proerties_docker
├── docker-compose.yml
├── Dockerfile
├── build.gradle
└── settings.gradle
```


### How-to
#### First
 - roles : ROLE_ADMIN, ROLE_USER 와 같이 등록


#### Swagger
 - 

