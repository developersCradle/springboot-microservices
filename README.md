
<p align="center">
    <img id="nordea" src="nordeaLogo.gif" width=400>
</p>


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
- [![Netty](https://img.shields.io/badge/Netty-grey?style=for-the-badge&logo=googleearth&logoColor=FFFFFF)](https://projectreactor.io/docs/netty/1.1.21/reference/index.html).
    - ✅**Netty**✅ is suited for Microservices Architecture, for its non-blocking I/O client-server nature.
- [![Lombok](https://img.shields.io/badge/Lombok-green?style=for-the-badge)](https://docs.spring.io/spring-framework/reference/web/webflux.html). 
    - ✅**Lombok**✅ For reducing boilerplate code.

- We are making `application.yml` for this microservices.
    - If this microservice would ran in different environment. 

- Domain classes represents classes inside business logic.
- DTO classes represents REST API.

## How to run the application.

# Container

Just start the containers by running the following command:

```bash
docker compose up
```

# Maven 


...

<!-- Remove these when all points ready here

<details>
<summary id="problem1">Weird Feature 1.</summary>

//TODO tee tähän johtopäätös https://countriesnow.space/api/v0.1/countries/population

- I came to notice when making **POST** request to the address of `https://countriesnow.space/api/v0.1/countries/population` it would work for **PostMan**, but not for **ReactorNetty**.

- Tool to catch the request were **Request Catcher**, it helped me to distinguish if there were some error in the request what **ReactorNetty** was making. URL of catcher `https://test.requestcatcher.com/`. **POST** didn't work for some reason and could not get any stream of data back from **Web Client** using DTO classes.
    - I Noticed the only difference mainly was headers. Left picture from **ReactorNetty** request and right from **PostMan**, which worked. I tried to change **User-Agent** to `User-Agent: PostmanRuntime/7.42.0` in **ReactorNetty** so it would work, but my luck failed. 

<p id="error" align="center">
    <img src="doneFromNettyHeaders.PNG" style="float:left; margin-right:10px;" width="400"  height="100">
    <img src="doneFromPostManHeaders.PNG" style="float:left;" width="400" height="100">
</p>

- Due to the inspections how PostMan had it working with this API. It had following settings `Accept: */*`. 

- Luckily returning `Mono<String>` from **POST** function and setting `@Data` with POST param DTO class with the changing **NettyReactor** headers to `"Accept", MediaType.ALL_VALUE` from `"Accept", MediaType.APPLICATION_JSON_VALUE`(since API gives JSON), gave me positive surprise.


```
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.defaultHeader(
        		"Accept", MediaType.ALL_VALUE)
        		.build();
    }
```

- With DTO class.

```
@Data
@AllArgsConstructor
public class ParamClass {
	String country;
}
```
- Below positive surprise. I was not crazy and seeing things.

<img  src="positveSupriseAboutPOSTapi.PNG" alt="alt text" width="600"/>

- Also, WebClient started to worked normally after right Header information `.doOnSuccess(result -> System.out.println("Response: " + result));` gave me `Response: Moved Permanently. Redirecting to /api/v0.1/countries/population/q?country=Finland`.

- All thought it was saying **redirecting**, i could not catch redirect message in network tab. Maybe it was due **ReactorNetty** needs to be configured to process these one, but for now i decided to just use th given **hint** as API query and move forward with the task :)

<img  src="noRedirectCaptured.PNG" alt="alt text" width="600"/>

</details>
 -->