package com.kanbine.backend.dto.request;

import com.kanbine.backend.dto.base.TimeCardDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Data Transfer Object (DTO) representing a time card creation or update request.
 * This DTO extends {@link TimeCardDTO} and is used during time card creation or update operations.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TimeCardRequest extends TimeCardDTO {
}
