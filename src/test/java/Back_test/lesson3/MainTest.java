package Back_test.lesson3;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MainTest extends BaseTest{

    private static String urlComplexSearch = "https://api.spoonacular.com/recipes/complexSearch";
    private static String urlRecipeCuisine = "https://api.spoonacular.com/recipes/cuisine";

    @Test
    void getRecipeWithQueryPasta() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("query", "pasta")
                .queryParam("maxFat", 25)
                .queryParam("number", 2)
                .when()
                .get(urlComplexSearch)
                .body()
                .jsonPath();

        assertThat(response.get("offset"), equalTo(0));
        assertThat(response.get("results[0].nutrition.nutrients[0].name"), equalTo("Fat"));
    }

    @Test
    void getRecipeWithSugar() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("type", "main course")
                .queryParam("maxSugar", 2)
                .queryParam("number", 1)
                .when()
                .get(urlComplexSearch)
                .body()
                .jsonPath();

        assertThat(response.get("number"), equalTo(1));
        float checkSugar = response.get("results[0].nutrition.nutrients[0].amount");
        assertThat((checkSugar < 1), equalTo(true));
    }

    @Test
    void getRecipeNumberItem() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("number", 2)
                .when()
                .get(urlComplexSearch)
                .body()
                .jsonPath();

        List<String> number = response.get("results");
        assertThat(number.size(), equalTo(2));
    }

    @Test
    void getRecipeExEggs() {
        JsonPath response = given()
                .queryParam("apiKey", getApiKey())
                .queryParam("includeIngredients", "pork")
                .when()
                .get(urlComplexSearch)
                .body()
                .jsonPath();

        assertThat(response.get("results[0].id"), equalTo(660306));
    }

    @Test
    void postRecipeCusine() {
        given()
                .queryParam("apiKey", getApiKey())
                .multiPart("title", "Toast with tomatoes")
                .multiPart("ingredientList", "3 kg tomatoes")
                .when()
                .post(urlRecipeCuisine)
                .then().log().all()
                .body("confidence", equalTo(0.0F));
    }
}
