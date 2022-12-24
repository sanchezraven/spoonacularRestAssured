package Back_test.lesson3;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public abstract class BaseTest {

    ResponseSpecification responseSpecification = null;
    RequestSpecification requestSpecification = null;

    private final String apiKey = "2880e1ace9384ea2935be356123682ce";
    //RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    public String getApiKey() {
        return apiKey;
    }
}
