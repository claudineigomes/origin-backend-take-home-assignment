# Origin Backend Take-Home Assignment
Origin offers its users an insurance package personalized to their specific needs without requiring the user to understand anything about insurance. This allows Origin to act as their *de facto* insurance advisor.

Origin determines the user’s insurance needs by asking personal & risk-related questions and gathering information about the user’s vehicle and house. Using this data, Origin determines their risk profile for **each** line of insurance and then suggests an insurance plan (`"economic"`, `"regular"`, `"responsible"`) corresponding to her risk profile.

For this assignment, you will create a simple version of that application by coding a simple API endpoint that receives a JSON payload with the user information and returns her risk profile (JSON again) – you don’t have to worry about the frontend of the application.

## The input
First, the would-be frontend of this application asks the user for her **personal information**. Then, it lets her add her **house** and **vehicle**. Finally, it asks her to answer 3 binary **risk questions**. The result produces a JSON payload, posted to the application’s API endpoint, like this example:

```JSON
{
  "age": 35,
  "dependents": 2,
  "house": {"ownership_status": "owned"},
  "income": 0,
  "marital_status": "married",
  "risk_questions": [0, 1, 0],
  "vehicle": {"year": 2018}
}
```

### User attributes
All user attributes are required:

- Age (an integer equal or greater than 0).
- The number of dependents (an integer equal or greater than 0).
- Income (an integer equal or greater than 0).
- Marital status (`"single"` or `"married"`).
- Risk answers (an array with 3 booleans).

### House
Users can have 0 or 1 house. When they do, it has just one attribute: `ownership_status`, which can be `"owned"` or `"mortgaged"`.

### Vehicle
Users can have 0 or 1 vehicle. When they do, it has just one attribute: a positive integer corresponding to the `year` it was manufactured.

## The risk algorithm
The application receives the JSON payload through the API endpoint and transforms it into a *risk profile* by calculating a *risk score* for each line of insurance (life, disability, home & auto) based on the information provided by the user.

First, it calculates the *base score* by summing the answers from the risk questions, resulting in a number ranging from 0 to 3. Then, it applies the following rules to determine a *risk score* for each line of insurance.

1. If the user doesn’t have income, vehicles or houses, she is ineligible for disability, auto, and home insurance, respectively.
2. If the user is over 60 years old, she is ineligible for disability and life insurance.
3. If the user is under 30 years old, deduct 2 risk points from all lines of insurance. If she is between 30 and 40 years old, deduct 1.
4. If her income is above $200k, deduct 1 risk point from all lines of insurance. 
5. If the user's house is mortgaged, add 1 risk point to her home score and add 1 risk point to her disability score. 
6. If the user has dependents, add 1 risk point to both the disability and life scores. 
7. If the user is married, add 1 risk point to the life score and remove 1 risk point from disability. 
8. If the user's vehicle was produced in the last 5 years, add 1 risk point to that vehicle’s score.

This algorithm results in a final score for each line of insurance, which should be processed using the following ranges:

- **0 and below** maps to **“economic”**.
- **1 and 2** maps to **“regular”**.
- **3 and above** maps to **“responsible”**.


## The output
Considering the data provided above, the application should return the following JSON payload:

```JSON
{
    "auto": "regular",
    "disability": "ineligible",
    "home": "economic",
    "life": "regular"
}
```

## Criteria
You may use any language and framework provided that you build a solid system with an emphasis on code quality, simplicity, readability, maintainability, and reliability, particularly regarding architecture and testing. We'd prefer it if you used Python, but it's just that – a preference.

Be aware that Origin will mainly take into consideration the following evaluation criteria:
* How clean and organized your code is;
* If you implemented the business rules correctly;
* How good your automated tests are (qualitative over quantitative).

Other important notes:
* Develop a extensible score calculation engine
* Add to the README file: (1) instructions to run the code; (2) what were the main technical decisions you made; (3) relevant comments about your project 
* You must use English in your code and also in your docs

This assignment should be doable in less than one day. We expect you to learn fast, **communicate with us**, and make decisions regarding its implementation & scope to achieve the expected results on time.

It is not necessary to build the screens a user would interact with, however, as the API is intended to power a user-facing application, we expect the implementation to be as close as possible to what would be necessary in real-life. Consider another developer would get your project/repository to evolve and implement new features from exactly where you stopped. 


# How run the code
To run this project, you need installed in your computer:

JDK 1.8 or later

After installed, open your Terminal and enter in project folder and type

*./mvnw spring-boot:run*

After that, the project is going to run in the port *8080*.

Then, open your browser in the url http://localhost:8080/swagger-ui.html
Just for a swagger limitation, I created a POST for the /risk/calculate method to enable the UI to call the method. But already has the GET mapping too. 

![How to run the code](doc/screen.gif)

## Technical comments
With the need for several calculations to be performed to deliver the result of the risk calculation, a calculator was developed that uses formulas in its calculations using input attributes as parameters.

```
ScoreCalculator
return InitializeFormula()
.then(AgeFormula())
.then(DependentsFormula())
.then(HouseFormula())
.then(IncomeFormula())
.then(MaritalFormula())
.then(VehicleFormula())
.execute(riskCalculationRequest)
```

The first formula to be applied is the Initialization formula. It applies the first rule that all scores are initialized with the sum of the values ​​that arrive in the attributes of the risk responses.

And afterwards, all other formulas are applied. Each one of them has its own rule, be it about Age, Vehicle, among others.

Each formula returns a list of an object that contains the values ​​to be added or subtracted from each risk value.

ScoreCalculationDetail
```
val autoScoreValue: Int, <-- value to be added or subtracted from the initial value for the auto risk
val disabilityScore Value: Int, <-- value to be added or subtracted from the initial value for the risk of disability
val homeScoreValue: Int, <-- value to be added or subtracted from the initial value for home risk
val lifeScoreValue: Int <-- value to be added or subtracted from the initial value for life risk
```

That way, we manage to separate each rule in its specific place and we don't mix the rules. And if you need to develop new rules or change them, you can do it with little effort.

And after all the formulas applied, we carry out the calculations and classifications of the result.

## Heroku deploy

If you have any trouble executing the project locally, you could check the swagger interface in the heroku environment.
Just enter in the url {https://origin-backend-take-home-assig.herokuapp.com/swagger-ui.html#/risk-calculation-controller/postUsingPOST} and wait to the page load and then execute the test.


