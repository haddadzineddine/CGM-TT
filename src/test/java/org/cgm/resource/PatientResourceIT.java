package org.cgm.resource;

import org.cgm.TestHelpers;
import org.cgm.config.CgmTestProfile;
import org.cgm.core.models.patient.Patient;
import org.cgm.core.models.patient.PatientAddRequest;
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
@TestHTTPEndpoint(PatientResource.class)
public class PatientResourceIT {

    @Test
    void should_get_patient_by_uuid() {

        UUID uuid = UUID.fromString("d7a8fcf7-30bb-4a5c-9f7c-16311e1c3cfa");

        Patient patient = given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get("/" + uuid.toString())
                .then()
                .statusCode(OK)
                .extract().body()
                .as(Patient.class);

        assertThat(patient).isNotNull()
                .hasFieldOrPropertyWithValue("uuid", uuid);
    }

    @Test
    void should_return_not_found_for_unknown_uuid() {
        get("/" + UUID.randomUUID().toString())
                .then()
                .statusCode(NOT_FOUND);
    }

    @Test
    void should_return_all_patients() {
        Patient[] patients = given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get("/")
                .then()
                .statusCode(OK)
                .extract().body()
                .as(Patient[].class);

        assertThat(patients).isNotNull()
                .hasSize(3);
    }

    @Test
    void should_create_patient() {
        PatientAddRequest patientAddRequest = TestHelpers.createAddRequest();

        Patient createdPatient = given()
                .when()
                .body(patientAddRequest)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .post()
                .then()
                .statusCode(CREATED)
                .extract().body()
                .as(Patient.class);

        assertThat(createdPatient).isNotNull()
                .hasFieldOrPropertyWithValue("name", "Mohamed")
                .hasFieldOrPropertyWithValue("surname", "Ali");
    }
}
