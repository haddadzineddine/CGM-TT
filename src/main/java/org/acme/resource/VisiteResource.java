package org.acme.resource;

import org.acme.core.models.visite.Visite;
import org.acme.core.models.visite.VisiteAddRequest;
import org.acme.core.models.visite.VisiteUpdateRequest;
import org.acme.service.VisiteService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import java.util.UUID;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.OBJECT;

@WithSession
@Path("/visites")
public class VisiteResource {

    @Inject
    VisiteService visiteService;

    @GET
    @Path("/{uuid}")
    @Operation(summary = "API used to get visite")
    @APIResponse(responseCode = "200", description = "Visite found", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Visite.class, type = OBJECT)))
    public Uni<Response> get(@PathParam("uuid") UUID uuid) {
        return visiteService.get(uuid)
                .map(event -> Response.ok(event).status(Response.Status.CREATED).build());
    }

    @GET
    @Operation(summary = "API used to get visites")
    @APIResponse(responseCode = "200", description = "Visites found", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Visite[].class, type = OBJECT)))
    public Uni<Response> getAll() {
        return visiteService.getAll()
                .map(event -> Response.ok(event).status(Response.Status.CREATED).build());
    }

    @PATCH
    @Operation(summary = "API used to update a Visite using his UUID")
    @APIResponse(responseCode = "200", description = "Visite is updated", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Visite.class, type = OBJECT)))
    @Path("/{uuid}")
    public Uni<Response> update(@PathParam("uuid") UUID uuid, VisiteUpdateRequest visiteUpdateRequest) {
        return this.visiteService.update(uuid, visiteUpdateRequest)
                .onItem().transform(team -> Response.ok(team).status(Response.Status.OK).build());
    }

    @POST
    @Operation(summary = "API used to create an visite")
    @APIResponse(responseCode = "201", description = "Visite created", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Visite.class, type = OBJECT)))
    @APIResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Response.class, type = OBJECT)))
    public Uni<Response> create(@Valid VisiteAddRequest visiteAddRequest) {
        return visiteService.create(visiteAddRequest)
                .map(event -> Response.ok(event).status(Response.Status.CREATED).build());
    }
}
