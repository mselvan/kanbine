package com.kanbine.backend.mappers;

import com.kanbine.backend.dto.request.TimeCardRequest;
import com.kanbine.backend.dto.response.TimeCardResponse;
import com.kanbine.backend.models.TimeCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between TimeCard entities and DTOs.
 * Provides mapping methods to convert TimeCardRequest to TimeCard,
 * and TimeCard to TimeCardResponse.
 */
@Mapper
public interface TimeCardMapper {

    TimeCardMapper INSTANCE = Mappers.getMapper(TimeCardMapper.class);

    /**
     * Converts a TimeCard entity to a TimeCardResponse DTO.
     * Maps the user and assignment entities to their corresponding IDs.
     *
     * @param timeCard the TimeCard entity.
     * @return the corresponding TimeCardResponse DTO.
     */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "assignmentId", source = "assignment.id")
    TimeCardResponse toTimeCardResponse(TimeCard timeCard);

    /**
     * Converts a TimeCardRequest DTO to a TimeCard entity.
     * Ignores the user and assignment fields during the mapping.
     *
     * @param timeCardRequest the DTO containing time card details.
     * @return the corresponding TimeCard entity.
     */
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "assignment", ignore = true)
    TimeCard toTimeCard(TimeCardRequest timeCardRequest);
}
