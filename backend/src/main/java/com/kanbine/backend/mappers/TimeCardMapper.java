package com.kanbine.backend.mappers;

import com.kanbine.backend.dto.TimeCardDTO;
import com.kanbine.backend.models.TimeCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TimeCardMapper {
    TimeCardMapper INSTANCE = Mappers.getMapper(TimeCardMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "assignmentId", source = "assignment.id")
    TimeCardDTO toTimeCardDTO(TimeCard timeCard);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "assignment", ignore = true)
    TimeCard toTimeCard(TimeCardDTO timeCardDTO);
}
