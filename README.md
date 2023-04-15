# Gerenciamento De Pessoas

API simples para gerenciar Pessoas.

Documentação: http://localhost:8080/swagger-ui/index.html

## Funcionalidades
- Criar uma pessoa
- Editar uma pessoa
- Consultar uma pessoa
- Listar pessoas
- Criar endereço para pessoa
- Listar endereços da pessoa
- Poder informar qual endereço é o principal da pessoa

## Requisições
| URL | Método HTTP | Descrição |
| --- | --- | --- |
| http://localhost:8080/pessoas | POST | Criar uma pessoa |
| http://localhost:8080/pessoas/{id} | PUT | Editar uma pessoa |
| http://localhost:8080/pessoas/{id} | GET | Consultar uma pessoa |
| http://localhost:8080/pessoas | GET | Listar todas as pessoas |
| http://localhost:8080/pessoas/{idPessoa}/enderecos | POST | Criar endereço para pessoa |
| http://localhost:8080/pessoas/{idPessoa}/enderecos | GET | Listar endereços da pessoa |
| http://localhost:8080/pessoas/{idPessoa}/enderecos | PATCH | Informar qual é o endereço principal da pessoa |

## Tecnologias utilizadas
- [Java 19](https://www.oracle.com/java/)
- [Spring Boot 3](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Maven](https://maven.apache.org/)
- [OpenAPI](https://swagger.io/specification/)
- [H2](https://www.h2database.com/html/main.html)
- [JUnit5](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito](https://site.mockito.org/)

## Autor

Gustavo da Silva Cruz

https://www.linkedin.com/in/gustavo-silva-cruz-20b128bb/
