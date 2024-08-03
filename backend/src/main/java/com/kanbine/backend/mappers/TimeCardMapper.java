package com.kanbine.backend.mappers;

import com.kanbine.backend.dto.request.TimeCardRequest;
import com.kanbine.backend.dto.response.TimeCardResponse;
import com.kanbine.backend.models.TimeCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TimeCardMapper {
    TimeCardMapper INSTANCE = Mappers.getMapper(TimeCardMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "assignmentId", source = "assignment.id")
    TimeCardResponse toTimeCardResponse(TimeCard timeCard);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "assignment", ignore = true)
    TimeCard toTimeCard(TimeCardRequest timeCardRequest);
}
