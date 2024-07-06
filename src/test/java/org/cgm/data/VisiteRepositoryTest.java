package org.cgm.data;

import org.cgm.data.repository.VisiteRepository;
import org.junit.jupiter.api.Test;
import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import io.quarkus.test.vertx.UniAsserter;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

@QuarkusTest
@TestReactiveTransaction
public class VisiteRepositoryTest {

    @Inject
    VisiteRepository visiteRepository;

    UUID uuid = UUID.fromString("c8f2e57b-8f5c-421a-bbda-3cf9df08c5b2");

    @Test
    void should_get_patient_by_uuid(UniAsserter asserter) {
        asserter.assertThat(() -> this.visiteRepository.findByUuid(uuid),
                patientEntity -> assertThat(patientEntity).isNotNull().hasFieldOrPropertyWithValue("uuid", uuid));
    }

    @Test
    void should_return_null_when_patient_not_found(UniAsserter asserter) {

        asserter.assertThat(() -> this.visiteRepository.findByUuid(UUID.randomUUID()),
                result -> assertThat(result).isNull());

    }

    @Test
    void should_get_all_patients(UniAsserter asserter) {
        asserter.assertThat(() -> this.visiteRepository.getAll(),
                visites -> assertThat(visites).isNotNull().isNotEmpty().hasSize(3));
    }
}
