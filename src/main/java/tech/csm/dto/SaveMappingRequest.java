package tech.csm.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SaveMappingRequest {

	private Long userId;
    private List<Long> selectedLinkIds;
	
}
