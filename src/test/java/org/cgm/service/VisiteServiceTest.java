
package org.cgm.service;

import org.cgm.TestHelpers;
import org.cgm.core.exceptions.patient.PatientNotFound;
import org.cgm.core.exceptions.visite.VisiteNotFound;
import org.cgm.core.models.visite.Visite;
import org.cgm.data.entity.PatientEntity;
import org.cgm.data.entity.VisiteEntity;
import org.cgm.data.repository.PatientRepository;
import org.cgm.data.repository.VisiteRepository;
import org.junit.jupiter.api.Test;
import io.quarkus.test.InjectMock;
import io.quarkus.test.TestReactiveTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.UniAsserter;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import jakarta.inject.Inject;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import java.util.*;

@QuarkusTest
public class VisiteServiceTest {

    @Inject
    VisiteService visiteService;

    @InjectMock
    PatientRepository patientRepository;

    @InjectMock
    VisiteRepository visiteRepository;

    UUID uuid = UUID.fromString("c8f2e57b-8f5c-421a-bbda-3cf9df08c5b2");
    UUID patientUuid = UUID.fromString("d7a8fcf7-30bb-4a5c-9f7c-16311e1c3cfa");

    PatientEntity patientEntity = new PatientEntity();
    VisiteEntity visiteEntity = new VisiteEntity();

    @Test
    void should_get_visite_by_uuid() {

        visiteEntity.setUuid(uuid);

        when(visiteRepository.findByUuid(any())).thenReturn(Uni.createFrom().item(visiteEntity));

        var result = visiteService.get(uuid).subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertSubscribed()
                .assertCompleted()
                .getItem();

        assertThat(result).isNotNull()
                .isInstanceOf(Visite.class)
                .hasFieldOrPropertyWithValue("uuid", uuid);

        verify(visiteRepository, times(1)).findByUuid(uuid);
    }

    @Test
    void should_throw_exception_when_patient_not_found() {

        when(visiteRepository.findByUuid(any())).thenReturn(Uni.createFrom().nullItem());

        visiteService.get(uuid).subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertSubscribed()
                .assertFailedWith(VisiteNotFound.class);

        verify(visiteRepository, times(1)).findByUuid(uuid);
        verifyNoMoreInteractions(visiteRepository);
    }

    @Test
    void should_get_all_visites() {

        visiteEntity.setUuid(uuid);

        when(visiteRepository.getAll()).thenReturn(Uni.createFrom().item(List.of(visiteEntity)));

        var result = visiteService.getAll().subscribe().withSubscriber(UniAssertSubscriber.create())
                .assertSubscribed()
                .assertCompleted()
                .getItem();

        assertThat(result).isNotNull()
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("uuid", uuid);

        verify(visiteRepository, times(1)).getAll();
    }

    @Test
    void should_create_visite() {
        visiteEntity.setUuid(uuid);

        when(patientRepository.findByUuid(any())).thenReturn(Uni.createFrom().item(patientEntity));
        when(visiteRepository.persistAndFlush(any())).thenReturn(Uni.createFrom().item(visiteEntity));

        var result = visiteService.create(TestHelpers.createVisiteAddRequest(patientUuid)).subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertSubscribed()
                .assertCompleted()
                .getItem();

        assertThat(result).isNotNull()
                .isInstanceOf(Visite.class)
                .hasFieldOrPropertyWithValue("uuid", uuid);

        verify(patientRepository, times(1)).findByUuid(any());
        verify(visiteRepository, times(1)).persistAndFlush(any());
    }

    @Test
    void should_throw_exception_when_create_visite_for_unknown_patient() {

        when(patientRepository.findByUuid(any())).thenReturn(Uni.createFrom().nullItem());

        visiteService.create(TestHelpers.createVisiteAddRequest(patientUuid)).subscribe()
                .withSubscriber(UniAssertSubscriber.create())
                .assertSubscribed()
                .assertFailedWith(PatientNotFound.class);

        verify(patientRepository, times(1)).findByUuid(any());
        verifyNoMoreInteractions(patientRepository);
        verifyNoMoreInteractions(visiteRepository);
    }

    @Test
    @TestReactiveTransaction
    void should_update_visite(UniAsserter asserter) {

        visiteEntity.setUuid(uuid);

        when(visiteRepository.findByUuid(any())).thenReturn(Uni.createFrom().item(visiteEntity));

        asserter.assertThat(() -> visiteService.update(uuid, TestHelpers.createVisiteUpdateRequest()),
                result -> {
                    assertThat(result).isNotNull()
                            .isInstanceOf(Visite.class)
                            .hasFieldOrPropertyWithValue("uuid", uuid)
                            .hasFieldOrPropertyWithValue("reason", "Reason Updated");

                    verify(visiteRepository, times(1)).findByUuid(uuid);
                });
    }

    @Test
    @TestReactiveTransaction
    void should_throw_exception_when_update_unknown_visite(UniAsserter asserter) {

        when(visiteRepository.findByUuid(any())).thenReturn(Uni.createFrom().nullItem());

        asserter.assertFailedWith(() -> visiteService.update(uuid, TestHelpers.createVisiteUpdateRequest()), VisiteNotFound.class);
    }
}
