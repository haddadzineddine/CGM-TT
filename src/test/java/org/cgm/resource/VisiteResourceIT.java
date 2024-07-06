package org.cgm.resource;

import org.cgm.TestHelpers;
import org.cgm.config.CgmTestProfile;
import org.cgm.core.enumeration.VisiteType;
import org.cgm.core.models.visite.Visite;
import org.cgm.core.models.visite.VisiteAddRequest;
import org.cgm.core.models.visite.VisiteUpdateRequest;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.*;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestProfile(CgmTestProfile.class)
@TestHTTPEndpoint(VisiteResource.class)
public class VisiteResourceIT {
        UUID uuid = UUID.fromString("c8f2e57b-8f5c-421a-bbda-3cf9df08c5b2");
        UUID patientUuid = UUID.fromString("d7a8fcf7-30bb-4a5c-9f7c-16311e1c3cfa");

        @Test
        void should_get_visite_by_uuid() {

                Visite visite = given()
                                .when()
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .get("/" + uuid.toString())
                                .then()
                                .statusCode(OK)
                                .extract().body()
                                .as(Visite.class);

                assertThat(visite).isNotNull()
                                .hasFieldOrPropertyWithValue("uuid", uuid);
        }

        @Test
        void should_return_not_found_for_unknown_uuid() {
                get("/" + UUID.randomUUID().toString())
                                .then()
                                .statusCode(NOT_FOUND);
        }

        @Test
        void should_return_all_visites() {
                Visite[] visites = given()
                                .when()
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .get("/")
                                .then()
                                .statusCode(OK)
                                .extract().body()
                                .as(Visite[].class);

                assertThat(visites).isNotNull()
                                .hasSize(3);
        }

        @Test
        void should_create_visite_for_existing_patient() {
                VisiteAddRequest visiteAddRequest = TestHelpers.createVisiteAddRequest(patientUuid);

                Visite createdPatient = given()
                                .when()
                                .body(visiteAddRequest)
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .post()
                                .then()
                                .statusCode(CREATED)
                                .extract().body()
                                .as(Visite.class);

                assertThat(createdPatient).isNotNull()
                                .hasFieldOrPropertyWithValue("reason", "Reason")
                                .hasFieldOrPropertyWithValue("type", VisiteType.AT_HOME);
        }

        @Test
        void should_return_not_found_when_creating_visite_for_unknown_patient() {
                VisiteAddRequest visiteAddRequest = TestHelpers.createVisiteAddRequest(UUID.randomUUID());

                given()
                                .when()
                                .body(visiteAddRequest)
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .post()
                                .then()
                                .statusCode(NOT_FOUND);
        }

        @Test
        void should_update_visite() {
                VisiteUpdateRequest visiteUpdateRequest = TestHelpers.createVisiteUpdateRequest();

                Visite updatedVisite = given()
                                .when()
                                .body(visiteUpdateRequest)
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .patch("/" + uuid.toString())
                                .then()
                                .statusCode(OK)
                                .extract().body()
                                .as(Visite.class);

                assertThat(updatedVisite).isNotNull()
                                .hasFieldOrPropertyWithValue("reason", "Reason Updated")
                                .hasFieldOrPropertyWithValue("type", VisiteType.AT_HOME);
        }

        @Test
        void should_return_not_found_when_updating_unknown_visite() {
                VisiteUpdateRequest visiteUpdateRequest = TestHelpers.createVisiteUpdateRequest();

                given()
                                .when()
                                .body(visiteUpdateRequest)
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .patch("/" + UUID.randomUUID().toString())
                                .then()
                                .statusCode(NOT_FOUND);
        }

}
