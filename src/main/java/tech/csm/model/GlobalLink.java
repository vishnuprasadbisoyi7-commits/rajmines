package tech.csm.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "GLOBAL_LINK")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GLOBAL_ID")
    private Integer globalId;

    @Column(name = "GLOBAL_NAME", nullable = false, length = 200)
    private String globalName;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "ORDER_NO")
    private Integer orderNo;

    @Column(name = "STATUS", length = 1)
    private String status;

    // One Global → Many Primary Links
    @OneToMany(mappedBy = "globalLink" /*cascade = CascadeType.ALL*/)
    private List<PrimaryLink> primaryLinks;

	public Integer getGlobalId() {
		return globalId;
	}

	public void setGlobalId(Integer globalId) {
		this.globalId = globalId;
	}

	public String getGlobalName() {
		return globalName;
	}

	public void setGlobalName(String globalName) {
		this.globalName = globalName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<PrimaryLink> getPrimaryLinks() {
		return primaryLinks;
	}

	public void setPrimaryLinks(List<PrimaryLink> primaryLinks) {
		this.primaryLinks = primaryLinks;
	}

	@Override
	public String toString() {
		return "GlobalLink [globalId=" + globalId + ", globalName=" + globalName + ", description=" + description
				+ ", orderNo=" + orderNo + ", status=" + status + ", primaryLinks=" + primaryLinks + "]";
	}
    
    
}
