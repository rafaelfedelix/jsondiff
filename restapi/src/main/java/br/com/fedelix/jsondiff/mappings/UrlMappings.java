package br.com.fedelix.jsondiff.mappings;

import br.com.fedelix.jsondiff.exception.NoDataException;
import br.com.fedelix.jsondiff.model.DiffResponse;
import br.com.fedelix.jsondiff.services.DiffService;
import br.com.fedelix.jsondiff.services.PersistenceService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spark.Request;

import static spark.Spark.before;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.route.HttpMethod.before;

@Component
public class UrlMappings {

    private static final Logger LOG = LoggerFactory.getLogger(UrlMappings.class);
    private DiffService diffService;
    private PersistenceService persistenceService;
    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    public UrlMappings(DiffService diffService, PersistenceService persistenceService) {
        this.diffService = diffService;
        this.persistenceService = persistenceService;
    }

    public void mapEndpoints() {

        before((request, response) -> response.type("application/json"));

        post("/v1/diff/:id/left", (req, res) -> {
            persistenceService.saveOnLeft(getId(req), req.body());
            res.status(201);
            return "saved";
        });

        post("/v1/diff/:id/right", (req, res) -> {
            persistenceService.saveOnRight(getId(req), req.body());
            res.status(201);
            return "saved";
        });

        get("/v1/diff/:id", (req, res) -> {
            DiffResponse diff = diffService.getDiff(getId(req));
            return gson.toJson(diff);
        });

        exception(IllegalArgumentException.class, (exception, request, response) -> {
            DiffResponse diff = new DiffResponse(exception.getMessage());
            response.body(gson.toJson(diff));
        });

        exception(NoDataException.class, (exception, request, response) -> {
            DiffResponse diff = new DiffResponse(exception.getMessage());
            response.body(gson.toJson(diff));
        });

        exception(Exception.class, (exception, request, response) -> {
            LOG.error("An error has ocurred", exception);
            response.status(500);
            response.body("An error has ocurred");
        });
    }

    private Long getId(Request req) {
        Long id;
        try {
            id = Long.parseLong(req.params(":id"));
        } catch(IllegalArgumentException exception) {
            throw new IllegalArgumentException("Missing parameters");
        }
        return id;
    }
}