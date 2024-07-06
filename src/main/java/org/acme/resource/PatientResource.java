package org.acme.resource;

import java.util.UUID;
import org.acme.core.models.patient.Patient;
import org.acme.core.models.patient.PatientAddRequest;
import org.acme.service.PatientService;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.OBJECT;

@WithSession
@Path("/patients")
public class PatientResource {

    @Inject
    PatientService patientService;

    @GET
    @Path("/{uuid}")
    @Operation(summary = "API used to get patient")
    @APIResponse(responseCode = "200", description = "Patient found", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Patient.class, type = OBJECT)))
    public Uni<Response> get(@PathParam("uuid") UUID uuid) {
        return patientService.get(uuid)
                .map(event -> Response.ok(event).status(Response.Status.CREATED).build());
    }

    @GET
    @Operation(summary = "API used to get patients")
    @APIResponse(responseCode = "200", description = "Patients found", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Patient[].class, type = OBJECT)))
    public Uni<Response> getAll() {
        return patientService.getAll()
                .map(event -> Response.ok(event).status(Response.Status.CREATED).build());
    }

    @POST
    @Operation(summary = "API used to create an patient")
    @APIResponse(responseCode = "201", description = "Patient created", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Patient.class, type = OBJECT)))
    @APIResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Response.class, type = OBJECT)))
    public Uni<Response> create(@Valid PatientAddRequest patientAddRequest) {
        return patientService.create(patientAddRequest)
                .map(event -> Response.ok(event).status(Response.Status.CREATED).build());
    }

}
