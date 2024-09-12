## Challenge description: COUNTRY SERVICE.

Implement a microservice which provides a list of countries and, in addition, provides more detailed information per country.

- Requirements:
1. Use spring-boot.
	- https://start.spring.io/.
	- https://spring.io/guides/gs/spring-boot/.
2. Use maven.
	- https://spring.io/guides/gs/maven/.
	- https://www.baeldung.com/maven.
3. Use best practices and microservice approach.
4. Implement the following REST API with spring-boot:
 
```
GET /countries/
response:
          {
                   "countries": [
                             {
                                       "name": "Finland",
                                       "country_code": "FI"
                             },
                             ...
                   ]
          }
```
```
GET /countries/{name}
response:
          {
                   "name": "Finland",
                   "country_code": "FI",
                   "capital": "Helsinki",
                   "population": 5491817,
                   "flag_file_url": "<url to the flag file>"
          }
```

5. Country service must fetch the relevant information for countries from some other service.
	- You could use for example the following service: `https://countriesnow.space/`.
6. Test the implementation as well as it's needed from your perspective.
7. All the implementations must be runnable locally with our own computer. Write needed instructions to README.md file.
8. Publish all sources code and relevant files in github or similar service and send the link to the repo so that the implementation can be reviewed.

You get bonus points if:

- You use and understand [Reactor](https://www.baeldung.com/reactor-core).
- You create a separate wep application which utilizes the created REST API and shows the relevant country information in a browser.

## Architecture Explanation

- [![Spring Boot](https://img.shields.io/static/v1?style=for-the-badge&message=Spring+Boot&color=6DB33F&logo=Spring+Boot&logoColor=FFFFFF&label=)](https://spring.io/), [![WebFlux](https://img.shields.io/badge/Spring%20WebFlux-grey?style=for-the-badge&logo=spring)](https://docs.spring.io/spring-framework/reference/web/webflux.html) and [![Project Reactor](https://img.shields.io/badge/Project_Reactor-grey?style=for-the-badge&logo=react&logoColor=FFFFFF)](https://projectreactor.io/).
    - Since this was in context of **Reactor**. I decided to use reactive approach, **Spring WebFlux**. 
- [![WebFlux-WebClient](https://img.shields.io/badge/WebClient-grey?style=for-the-badge&logo=spring)](https://docs.spring.io/spring-framework/reference/web/webflux-webclient.html).
    - ✅ **WebClient** ✅ Interacting with 3rd party endpoints, WebClient was chosen for making non-blocking & asynchronous endpoint.
    - ❌ **RestTemplate** ❌ Older way to make calls in Spring and synchronous. [RestTemplate](https://www.baeldung.com/rest-template).
    - ❌ **Feign Client** ❌ Also synchronous. [Feign Client](https://www.baeldung.com/spring-boot-feignclient-vs-webclient).
- [![Netty](https://img.shields.io/badge/Netty-grey?style=for-the-badge&logo=googleearth&logoColor=FFFFFF)](https://projectreactor.io/docs/netty/1.1.21/reference/index.html)
    - ✅**Netty**✅ is suited for Microservices Architecture, for its non-blocking I/O client-server nature.
- [![Lombok](https://img.shields.io/badge/Lombok-green?style=for-the-badge)](https://docs.spring.io/spring-framework/reference/web/webflux.html) 
    - ✅**Lombok**✅ For reducing boilerplate code.

- We are making `application.yml` for this microservices.
    - If this microservice would ran in different environment. 

## How to run the application.

Just start the containers by running the following command:

```bash
docker compose up
```
