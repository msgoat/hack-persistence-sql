package group.msg.at.cloud.cloudtrain.adapter.rest;

import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.Enumeration;

/**
 * REST endpoint exposing internal information about the current environment and the current request context.
 */
@RequestScoped
@Path("v1/inspect")
public class InspectResource {

    /**
     * Always returns an empty plain text response with HTTP status code 200.
     */
    @GET
    @Path("environment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response inspectEnvironment() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        System.getenv().forEach((k,v) -> builder.add(k,v));
        JsonObject environment = builder.build();
        return Response.ok(environment).build();
    }

    /**
     * Always returns an empty plain text response with HTTP status code 200.
     */
    @GET
    @Path("request")
    public Response inspectRequest(@Context HttpServletRequest request) {
        JsonObjectBuilder entityBuilder = Json.createObjectBuilder();
        entityBuilder.add("requestURI", request.getRequestURI());
        entityBuilder.add("servletPath", request.getServletPath());
        JsonArrayBuilder headersBuilder = Json.createArrayBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            JsonObjectBuilder headerBuilder = Json.createObjectBuilder();
            headerBuilder.add(headerName, headerValue);
            headersBuilder.add(headerBuilder.build());
        }
        entityBuilder.add("headers", headersBuilder.build());
        JsonObject entity = entityBuilder.build();
        return Response.ok(entity).build();
    }
}
