package org.cgm.data;

import org.cgm.data.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import io.quarkus.test.vertx.UniAsserter;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

@QuarkusTest
@TestReactiveTransaction
public class PatientRepositoryTest {

    @Inject
    PatientRepository patientRepository;

    UUID uuid = UUID.fromString("d7a8fcf7-30bb-4a5c-9f7c-16311e1c3cfa");

    @Test
    void should_get_patient_by_uuid(UniAsserter asserter) {
        asserter.assertThat(() -> this.patientRepository.findByUuid(uuid),
                patientEntity -> assertThat(patientEntity).isNotNull().hasFieldOrPropertyWithValue("uuid", uuid));
    }

    @Test
    void should_return_null_when_patient_not_found(UniAsserter asserter) {

        asserter.assertThat(() -> this.patientRepository.findByUuid(UUID.randomUUID()),
                result -> assertThat(result).isNull());

    }

    @Test
    void should_get_all_patients(UniAsserter asserter) {

        asserter.assertThat(() -> this.patientRepository.getAll(),
                patients -> assertThat(patients).isNotNull().isNotEmpty().hasSize(3));
    }

}
