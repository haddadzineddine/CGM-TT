package org.acme.core.mappers;

import org.acme.core.models.patient.Patient;
import org.acme.core.models.visite.Visite;
import org.acme.core.models.visite.VisiteAddRequest;
import org.acme.core.models.visite.VisiteCreationRequest;
import org.acme.core.models.visite.VisiteUpdateRequest;
import org.acme.data.entity.VisiteEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Mapper(componentModel = ComponentModel.JAKARTA)
public interface VisiteMapper {

    @Mapping(target = "uuid", ignore = true)
    VisiteEntity visiteCreationRequestToVisitetEntity(VisiteCreationRequest visiteCreationRequest);

    Visite visiteEntityToVisite(VisiteEntity patientEntity);

    VisiteCreationRequest visiteAddRequestToVisiteCreationRequest(VisiteAddRequest visiteAddRequest, Patient patient);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "patient", ignore = true)
    void visiteUpdateRequestToVisiteEntity(VisiteUpdateRequest visiteUpdateRequest,
            @MappingTarget VisiteEntity visiteEntity);

}
