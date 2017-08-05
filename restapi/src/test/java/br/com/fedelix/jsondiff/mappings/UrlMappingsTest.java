package br.com.fedelix.jsondiff.mappings;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

public class UrlMappingsTest {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 4567;
        RestAssured.basePath = "/v1/diff";
    }

    @Test
    public void shouldSaveOnLeft() {
        given().body("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=")
                .when()
                .post("/1/left")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldSaveOnRight() {
        given().body("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzMWUyIjogNDU2Cn0=")
                .when()
                .post("/1/right")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldGetDiff() {
        shouldSaveOnLeft();
        shouldSaveOnRight();
        given().when()
                .get("/1")
                .then()
                .body("message", equalTo("Differences found"))
                .body("differences*.length", hasItems(1, 3));
    }

    @Test
    public void shouldNotFindData() {
        given().when()
                .get("/123456")
                .then()
                .body("message", equalTo("No data found for id 123456"));
    }

    @Test
    public void shouldGetDifferentLengthMessage() {
        given().body("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=").when().post("/2/left");
        given().body("").when().post("/2/right");
        given().when()
                .get("/2")
                .then()
                .log().all()
                .body("message", equalTo("Left and right have different length"));
    }

    @Test
    public void shouldGetEqualsMessage() {
        given().body("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=").when().post("/3/left");
        given().body("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=").when().post("/3/right");
        given().when()
                .get("/3")
                .then()
                .log().all()
                .body("message", equalTo("Left and right are equal"));
    }

    @Test
    public void shouldGetMissingParametersMessage() {
        given().when()
                .post("/x/left")
                .then()
                .log().all()
                .body("message", equalTo("Missing parameters"));
    }
}