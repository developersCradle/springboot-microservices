## Microservice challenge with  additional experiments.


## Challenge description: COUNTRY SERVICE

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
	- You could use for example the following service: https://countriesnow.space/.
6. Test the implementation as well as it's needed from your perspective
7. All the implementations must be runnable locally with our own computer. Write needed instructions to README.md file.
8. Publish all sources code and relevant files in github or similar service and send the link to the repo so that the implementation can be reviewed.

You get bonus points if:
	- You use and understand reactor (https://www.baeldung.com/reactor-core)
	- You create a separate wep application which utilizes the created REST API and shows the relevant country information in a browser.

