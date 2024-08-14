package com.kanbine.backend.dto.response;

import com.kanbine.backend.dto.base.AssignmentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Data Transfer Object (DTO) representing an assignment response.
 * This DTO extends {@link AssignmentDTO} and is used for sending assignment data in responses.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AssignmentResponse extends AssignmentDTO {

}
