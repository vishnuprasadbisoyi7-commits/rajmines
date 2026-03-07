package tech.csm.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "FUNCTION_MASTER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FunctionMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FUNCTION_ID")
    private Integer functionId;

    @Column(name = "FUNCTION_NAME", nullable = false, length = 200)
    private String functionName;

    @Column(name = "FUNCTION_URL", length = 500)
    private String functionUrl;

    @Column(name = "STATUS", length = 1)
    private String status;

    // One Function → Many Primary Links
    @OneToMany(mappedBy = "functionMaster", cascade = CascadeType.ALL)
    private List<PrimaryLink> primaryLinks;

	public Integer getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
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
		return "FunctionMaster [functionId=" + functionId + ", functionName=" + functionName + ", functionUrl="
				+ functionUrl + ", status=" + status + ", primaryLinks=" + primaryLinks + "]";
	}
    
    
}
