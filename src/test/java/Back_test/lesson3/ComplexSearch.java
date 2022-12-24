package Back_test.lesson3;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class ComplexSearch extends BaseTest{

    private static String urlComplexSearch = "https://api.spoonacular.com/recipes/complexSearch";

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
    void getRecipeWithQueryPasta() {
        given()
                .spec(requestSpecification)
                .queryParam("query", "pasta")
                .queryParam("maxFat", 25)
                .queryParam("number", 2)
                .when()
                .get(urlComplexSearch)
                .then()
                .spec(responseSpecification)
                .body("offset", equalTo(0))
                .body("results[0].nutrition.nutrients[0].name", equalTo("Fat"));
    }

    @Test
    void getRecipeWithSugar() {
        given()
                .spec(requestSpecification)
                .queryParam("type", "main course")
                .queryParam("maxSugar", 2)
                .queryParam("number", 1)
                .when()
                .get(urlComplexSearch)
                .then()
                .spec(responseSpecification)
                .body("number", equalTo(1))
                .body("results[0].nutrition.nutrients[0].amount", lessThan(1F));
    }

    @Test
    void getRecipeNumberItem() {
        JsonPath response = given()
                .spec(requestSpecification)
                .queryParam("number", 2)
                .when()
                .get(urlComplexSearch)
                .jsonPath();

        List<String> number = response.get("results");
        assertThat(number.size(), equalTo(2));
    }

    @Test
    void getRecipeExEggs() {
        given()
                .spec(requestSpecification)
                .queryParam("includeIngredients", "pork")
                .when()
                .get(urlComplexSearch)
                .then()
                .spec(responseSpecification)
                .body("results[0].id", equalTo(660306));
    }
}
