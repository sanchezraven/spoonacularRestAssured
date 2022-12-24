package Back_test.lesson3;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RecipeCuisine extends BaseTest{

    private static String urlRecipeCuisine = "https://api.spoonacular.com/recipes/cuisine";

    @BeforeEach
    void BeforeTest() {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .build();
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", getApiKey())
                .log(LogDetail.ALL)
                .build();
    }

    @Test
    void postRecipeCusine() {
        given()
                .spec(requestSpecification)
                .multiPart("title", "Toast with tomatoes")
                .multiPart("ingredientList", "3 kg tomatoes")
                .when()
                .post(urlRecipeCuisine)
                .then()
                .spec(responseSpecification)
                .body("confidence", equalTo(0.0F));
    }
}
