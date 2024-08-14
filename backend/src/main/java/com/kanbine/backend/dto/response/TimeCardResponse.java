package com.kanbine.backend.dto.response;

import com.kanbine.backend.dto.base.TimeCardDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Data Transfer Object (DTO) representing a time card response.
 * This DTO extends {@link TimeCardDTO} and is used for sending time card data in responses.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TimeCardResponse extends TimeCardDTO {

}
