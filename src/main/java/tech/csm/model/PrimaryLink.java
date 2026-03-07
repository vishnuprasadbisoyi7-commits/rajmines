package tech.csm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PRIMARY_LINK")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrimaryLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRIMARY_ID")
    private Integer primaryId;

    @Column(name = "PRIMARY_NAME", length = 200)
    private String primaryName;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "ORDER_NO")
    private Integer orderNo;

    @Column(name = "STATUS", length = 1)
    private String status;

    @ManyToOne
    @JoinColumn(name = "GLOBAL_ID", nullable = false)
    @JsonIgnoreProperties("primaryLinks")
    private GlobalLink globalLink;

    @ManyToOne
    @JoinColumn(name = "FUNCTION_ID", nullable = false)
    @JsonIgnoreProperties("primaryLinks")
    private FunctionMaster functionMaster;

	public Integer getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(Integer primaryId) {
		this.primaryId = primaryId;
	}

	public String getPrimaryName() {
		return primaryName;
	}

	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
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

	public GlobalLink getGlobalLink() {
		return globalLink;
	}

	public void setGlobalLink(GlobalLink globalLink) {
		this.globalLink = globalLink;
	}

	public FunctionMaster getFunctionMaster() {
		return functionMaster;
	}

	public void setFunctionMaster(FunctionMaster functionMaster) {
		this.functionMaster = functionMaster;
	}

	@Override
	public String toString() {
		return "PrimaryLink [primaryId=" + primaryId + ", primaryName=" + primaryName + ", description=" + description
				+ ", orderNo=" + orderNo + ", status=" + status + ", globalLink=" + globalLink + ", functionMaster="
				+ functionMaster + "]";
	} 
    
    
}
