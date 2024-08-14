package com.kanbine.backend.dto.request;

import com.kanbine.backend.dto.base.AssignmentDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Data Transfer Object (DTO) representing an assignment creation or update request.
 * This DTO extends {@link AssignmentDTO} and is used during assignment creation or update operations.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AssignmentRequest extends AssignmentDTO {
}
