package br.com.fedelix.jsondiff.mappings;

import br.com.fedelix.jsondiff.exception.NoDataException;
import br.com.fedelix.jsondiff.model.DiffData;
import br.com.fedelix.jsondiff.repository.JsonRepository;
import br.com.fedelix.jsondiff.services.DiffService;
import br.com.fedelix.jsondiff.services.PersistenceService;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import spark.Spark;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;

public class UrlMappingsIT {

    private static JsonRepository jsonRepository = mock(JsonRepository.class);
    private static DiffService diffService = new DiffService(jsonRepository);
    private static PersistenceService persistenceService = new PersistenceService(jsonRepository);

    @Test
    public void shouldSaveOnLeft() {
        given().body("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=")
                .when()
                .post("/1/left")
                .then()
                .statusCode(201);
    }

    @Test
    public void shouldSaveOnRight() {
        given().body("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzMWUyIjogNDU2Cn0=")
                .when()
                .post("/1/right")
                .then()
                .statusCode(201);
    }

    @Test
    public void shouldGetDiff() {
        // given
        DiffData leftDiffData = new DiffData("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=");
        DiffData rightDiffData = new DiffData("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzMWUyIjogNDU2Cn0=");
        Mockito.when(jsonRepository.getLeftById(Mockito.anyLong())).thenReturn(leftDiffData);
        Mockito.when(jsonRepository.getRightById(Mockito.anyLong())).thenReturn(rightDiffData);
        shouldSaveOnLeft();
        shouldSaveOnRight();
        // when
        given().when()
                .get("/1")
                .then()
                .body("message", equalTo("Differences found"))
                .body("differences*.length", hasItems(1, 3));
    }

    @Test
    public void shouldNotFindData() {
        // given
        Mockito.when(jsonRepository.getLeftById(Mockito.any())).thenThrow(new NoDataException("No data found for id 123456"));
        // when
        given().when()
                .get("/123456")
                .then()
                .body("message", equalTo("No data found for id 123456"));
    }

    @Test
    public void shouldGetDifferentLengthMessage() {
        // given
        DiffData leftDiffData = new DiffData("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=");
        DiffData rightDiffData = new DiffData("");
        Mockito.when(jsonRepository.getLeftById(Mockito.anyLong())).thenReturn(leftDiffData);
        Mockito.when(jsonRepository.getRightById(Mockito.anyLong())).thenReturn(rightDiffData);
        given().body("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=").when().post("/2/left");
        given().body("").when().post("/2/right");
        // when
        given().when()
                .get("/2")
                .then()
                .body("message", equalTo("Left and right have different length"));
    }

    @Test
    public void shouldGetEqualsMessage() {
        // given
        DiffData diffData = new DiffData("diff");
        Mockito.when(jsonRepository.getLeftById(Mockito.anyLong())).thenReturn(diffData);
        Mockito.when(jsonRepository.getRightById(Mockito.anyLong())).thenReturn(diffData);
        given().body("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=").when().post("/3/left");
        given().body("ewogICJ0ZXN0ZSI6IDEyMywKICAidGVzdGUyIjogMzIxCn0=").when().post("/3/right");
        // when
        given().when()
                .get("/3")
                .then()
                .body("message", equalTo("Left and right are equal"));
    }

    @Test
    public void shouldGetMissingParametersMessage() {
        given().when()
                .post("/x/left")
                .then()
                .body("message", equalTo("Missing parameters"));
    }

    @BeforeClass
    public static void setUp() throws Exception {
        Spark.port(4567);
        Spark.threadPool(1000, 1000,60000);
        UrlMappings urlMappings = new UrlMappings(diffService, persistenceService);
        urlMappings.mapEndpoints();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 4567;
        RestAssured.basePath = "/v1/diff";
    }
}