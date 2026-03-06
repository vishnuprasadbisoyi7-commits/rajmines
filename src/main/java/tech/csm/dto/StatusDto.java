package tech.csm.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StatusDto {

    private String statusCode;
    private String description;
}
