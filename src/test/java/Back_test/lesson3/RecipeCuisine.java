package Back_test.lesson3;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RecipeCuisine extends BaseTest{

    private static String urlRecipeCuisine = "https://api.spoonacular.com/recipes/cuisine";

    @BeforeEach
    void BeforeTest() {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .build();
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", getApiKey())
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    @Test
    void postRecipeCusine() {
        AddMealReq addMealReq = new AddMealReq();
        addMealReq.setTitle("Toast with tomatoes");
        addMealReq.setIngredientList("3 kg tomatoes");

        AddMealResponse addMealResponse = given()
                .spec(requestSpecification)
                .body(addMealReq)
                .when()
                .post(urlRecipeCuisine)
                .then().log().all()
                .spec(responseSpecification)
                .extract()
                .response()
                .body()
                .as(AddMealResponse.class);
        assertThat(addMealResponse.getConfidence(), equalTo(0.0));
    }
}
