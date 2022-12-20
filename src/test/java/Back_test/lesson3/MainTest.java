package Back_test.lesson3;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class MainTest extends BaseTest{

    @Test
    void getRecipeWithQueryPasta() {
        given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "pasta")
                .queryParam("maxFat", 25)
                .queryParam("number", 2)
                .when()
                .get("https://api.spoonacular.com/recipes/complexSearch")
                .then()
                .statusCode(200);
    }


}
