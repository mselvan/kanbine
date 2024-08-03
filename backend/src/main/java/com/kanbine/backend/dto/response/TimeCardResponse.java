package com.kanbine.backend.dto.response;

import com.kanbine.backend.dto.base.TimeCardDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper=true)
public class TimeCardResponse extends TimeCardDTO {

}
