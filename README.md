
# nw-backend

낭만웨딩 플랫폼 백엔드 프로젝트


## Badges

[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](https://choosealicense.com/licenses/mit/)
[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)
[![AGPL License](https://img.shields.io/badge/license-AGPL-blue.svg)](http://www.gnu.org/licenses/agpl-3.0)


## Prerequisite
- Name : nw-backend
- Language : Java
- Build System : Gradle v8.2.1
- Gradle DSL : Groovy
- JDK : Eclipse Temurin (AdoptOpenJDK HotSpot v17.0.7)
## Installation

Install nw-backend with java

```bash
  git clone git@github.com:lab-cherry/nw-backend.git
  cd nw-backend
```

## Environment Variables

To run this project, you will need to edit the following environment variables to your src/main/resources/application.properties_sample copy to src/main/resources/application.properties file

- base
  - `frontend.host`
  - `server.port`
- database
  - `spring.data.mongodb.uri`
- cache
  - `spring.data.redis.host`
- email
  - `spring.mail.host`
  - `spring.mail.port`
  - `spring.mail.username`
  - `spring.mail.password`

## API Reference
- [swagger.json](./swagger/swagger.json)
## Authors

- [taking](https://github.com/taking)
- [yby654](https://github.com/yby654)
- [hhhaeri](https://github.com/hhhaeri)

