package org.cgm.service;

import org.cgm.core.exceptions.patient.PatientNotFound;
import org.cgm.core.models.patient.Patient;
import org.cgm.core.models.patient.PatientAddRequest;
import org.cgm.data.entity.PatientEntity;
import org.cgm.data.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import jakarta.inject.Inject;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import java.util.*;
import org.cgm.TestHelpers;

@QuarkusTest
public class PatientServiceTest {

        @Inject
        PatientService patientService;

        @InjectMock
        PatientRepository patientRepository;

        UUID uuid = UUID.fromString("d7a8fcf7-30bb-4a5c-9f7c-16311e1c3cfa");

        PatientEntity patientEntity = new PatientEntity();

        @Test
        void should_get_patient_by_uuid() {

                patientEntity.setUuid(uuid);

                when(patientRepository.findByUuid(any())).thenReturn(Uni.createFrom().item(patientEntity));

                var result = patientService.get(uuid).subscribe().withSubscriber(UniAssertSubscriber.create())
                                .assertSubscribed()
                                .assertCompleted()
                                .getItem();

                assertThat(result).isNotNull()
                                .isInstanceOf(Patient.class)
                                .hasFieldOrPropertyWithValue("uuid", uuid);

                verify(patientRepository, times(1)).findByUuid(uuid);
        }

        @Test
        void should_throw_exception_when_patient_not_found() {

                when(patientRepository.findByUuid(any())).thenReturn(Uni.createFrom().nullItem());

                patientService.get(uuid).subscribe().withSubscriber(UniAssertSubscriber.create())
                                .assertSubscribed()
                                .assertFailedWith(PatientNotFound.class);

                verify(patientRepository, times(1)).findByUuid(uuid);
                verifyNoMoreInteractions(patientRepository);
        }

        @Test
        void should_get_all_patients() {

                patientEntity.setUuid(uuid);

                when(patientRepository.getAll()).thenReturn(Uni.createFrom().item(List.of(patientEntity)));

                var result = patientService.getAll().subscribe().withSubscriber(UniAssertSubscriber.create())
                                .assertSubscribed()
                                .assertCompleted()
                                .getItem();

                assertThat(result).isNotNull()
                                .hasSize(1)
                                .first()
                                .hasFieldOrPropertyWithValue("uuid", uuid);

                verify(patientRepository, times(1)).getAll();
        }

        @Test
        void should_create_patient() {
                patientEntity.setUuid(uuid);

                PatientAddRequest patientAddRequest = TestHelpers.createAddRequest();
                when(patientRepository.persistAndFlush(any())).thenReturn(Uni.createFrom().item(patientEntity));

                var result = patientService.create(patientAddRequest).subscribe()
                                .withSubscriber(UniAssertSubscriber.create())
                                .assertSubscribed()
                                .assertCompleted()
                                .getItem();

                assertThat(result).isNotNull()
                                .hasFieldOrPropertyWithValue("uuid", uuid);

                verify(patientRepository, times(1)).persistAndFlush(any());
        }

}
