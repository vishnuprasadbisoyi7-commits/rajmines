package tech.csm.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateUserStatusRequestDto {

    private Integer userId;
    private String status;
    private Integer performedByUserId;
}
