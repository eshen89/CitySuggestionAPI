# Airgraft City Suggestion API
- This is Airgraft technical interview implementation

### Installation & Startup

1. Clone repo with `git clone https://github.com/eshen89/CitySuggestionAPI.git` and checkout `develop` branch
2. Download City data:
   - [CA.zip](http://download.geonames.org/export/dump/CA.zip)
   - [US.zip](http://download.geonames.org/export/dump/US.zip)
3. Unzip packages then move `XX.txt` under `/CitySuggestionAPI/src/main/resources/static` directory, the directory should contain `CA.txt` and `US.txt`
4. Run `mvn spring-boot:run`

### API
```HTTP GET /suggestion?q=Toronto&lat=43.6&long=-79.666```

| Request Param  | Description | Data Type  | Required |
| ------------- | ------------- | ------------- | ------------- |
| q  | Search text query, must be the prefix of a city  | String | True
| lat  | Latitude  | Double | False
| long  | Longitude  | Double | False

#### Sample Response:
```
{
    "suggestions": [
        {
            "name": "Toronto CA",
            "latitude": 43.60012,
            "longitude": -79.66632,
            "score": 0.99
        },
        {
            "name": "Toronto City Hall CA",
            "latitude": 43.65347,
            "longitude": -79.38416,
            "score": 0.99
        }
    ]
}
```
Note: 

The search results ordered in descending order based on the confidence score.

This API doesn't support suffix searching, put city suffix may return undesired results.

### Bonus Answer

- Can you think of more scoring parameters to make the suggestions better? Add them to your documentation
  - Some parameters help to narrow down the search area, for instance `country_code` `zipcode` `radius`
- Sketch out a design for per user API keys and billing for our future city-suggestion-service startup. 
  What are the implications for scalability of your implementation?
  - Please see `/CitySuggestionAPI/src/main/resources/images/api_billing_model.jpg`

### Libraries

- Java 11
- Spring Boot 2
- Junit 5
- Maven

### IDE

- IntelliJ