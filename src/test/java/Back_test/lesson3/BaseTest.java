package Back_test.lesson3;

import io.restassured.RestAssured;

public abstract class BaseTest {

    private final String apiKey = "2880e1ace9384ea2935be356123682ce";
    //RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    public String getApiKey() {
        return apiKey;
    }
}
