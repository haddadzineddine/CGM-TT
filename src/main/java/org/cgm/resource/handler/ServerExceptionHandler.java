package org.cgm.resource.handler;

import org.cgm.core.enumeration.ErrorEnum;
import org.cgm.core.exceptions.config.CgmException;
import org.cgm.core.models.Error;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import java.util.Arrays;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ServerExceptionHandler {

    @ConfigProperty(name = "service.name")
    String serviceName;

    @ServerExceptionMapper
    public Uni<Response> exceptionHandler(Exception exception) {

        if (exception instanceof CgmException cgmException) {

            ErrorEnum error = cgmException.getError();
            Response.Status status = this.findStatus(error);

            return Uni.createFrom().item(Response.status(status)
                    .entity(Arrays.asList(Error.builder()
                            .code(error.getCode())
                            .message(error.getMessage())
                            .description(cgmException.getDescription())
                            .build()))
                    .build());

        }

        if (exception instanceof WebApplicationException webApplicationException) {
            return Uni.createFrom()
                    .item(Response.status(webApplicationException.getResponse().getStatus())
                            .entity(webApplicationException.getResponse().getEntity()).build());
        }

        return Uni.createFrom()
                .item(Response.status(Response.Status.SERVICE_UNAVAILABLE.getStatusCode())
                        .entity("Ooops, something went wrong!")
                        .build());

    }

    private Response.Status findStatus(ErrorEnum errorEnum) {
        return switch (errorEnum) {
            case E00001, E00002 -> Response.Status.NOT_FOUND;
            default -> Response.Status.SERVICE_UNAVAILABLE;
        };
    }

}
