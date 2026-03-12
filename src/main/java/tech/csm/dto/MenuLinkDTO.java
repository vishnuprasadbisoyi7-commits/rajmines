package tech.csm.dto;

import java.util.List;

import lombok.Data;

@Data
public class MenuLinkDTO {

	private Long id;
    private String name;
    private String icon;
    private String route;
    private boolean isGlobal;
    private boolean checked;
    private List<MenuLinkDTO> children;
	
}
