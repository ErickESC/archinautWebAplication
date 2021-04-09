package mx.uam.izt.archinautInterface.dynamodb.model;

import java.io.Serializable;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

import lombok.Data;

@Data
public class Messages implements Serializable{
	public static final long serialVersionUID = 165165486816897L;
	
	@DynamoDBHashKey(attributeName = "id")
	private String id;
	
	@DynamoDBAttribute(attributeName = "idCommit")
	private String idCommit;
	
	@DynamoDBAttribute(attributeName = "analysis")
	private String analysis;
	
	@DynamoDBAttribute(attributeName = "date")
	private String date;
	
	@DynamoDBAttribute(attributeName = "files")
	private List<Datos> files;
	
    // Additional properties go here.

    @DynamoDBDocument
    @Data
    public static class Datos {
    	
    	@DynamoDBAttribute(attributeName = "Filename")
    	private String filename;
    	
    	@DynamoDBAttribute(attributeName = "ARCH_Revisions")
    	private String arch_revisions;
    	
    	@DynamoDBAttribute(attributeName = "SCC_LOC")
    	private String scc_loc;
    	
    	@DynamoDBAttribute(attributeName = "DV8_UnstableInterface")
    	private String dv8_UnstableInterface;
    	
    	@DynamoDBAttribute(attributeName = "S101_ClassTangles")
    	private String s101_ClassTangles;
    	
    	@DynamoDBAttribute(attributeName = "DES_Complexity")
    	private String des_Complexity;
    	
    	@DynamoDBAttribute(attributeName = "ARCH_Depends on Partners")
    	private String arch_DependsonPartners;
    	
    	@DynamoDBAttribute(attributeName = "ARCH_Dependent Partners")
    	private String arch_DependentPartners;
    	
    	@DynamoDBAttribute(attributeName = "DV8_IsCenter")
    	private String dv8_IsCenter;
    	
    	@DynamoDBAttribute(attributeName = "DV8_isUnstableInterface")
    	private String dv8_isUnstableInterface;
    	
    	@DynamoDBAttribute(attributeName = "DV8_PresentInIssues")
    	private String dv8_PresentInIssues;
    	
    	@DynamoDBAttribute(attributeName = "SQ_SecuritySpots")
    	private String sq_SecuritySpots;
    	
    	@DynamoDBAttribute(attributeName = "DV8_Clique")
    	private String dv8_Clique;
    	
    	@DynamoDBAttribute(attributeName = "S101_PkgTangles")
    	private String s101_PkgTangles;
    	
    	@DynamoDBAttribute(attributeName = "DV8_Crossing")
    	private String dv8_Crossing;
    	
    	@DynamoDBAttribute(attributeName = "SQ_Bugs")
    	private String sq_Bugs;
    	
    	@DynamoDBAttribute(attributeName = "DV8_ChangeCount")
    	private String dv8_ChangeCount;
    	
    	@DynamoDBAttribute(attributeName = "DV8_UnhealthyInheritance")
    	private String dv8_UnhealthyInheritance;
    	
    	@DynamoDBAttribute(attributeName = "DV8_TargetChurn")
    	private String dv8_TargetChurn;
    	
    	@DynamoDBAttribute(attributeName = "ARCH_Churn")
    	private String arch_Churn;
    	
    	@DynamoDBAttribute(attributeName = "DES_Design Smells")
    	private String des_DesignSmells;
    	
    	@DynamoDBAttribute(attributeName = "SQ_Issues")
    	private String sq_Issues;
    	
    	@DynamoDBAttribute(attributeName = "DV8_PackageCycle")
    	private String dv8_PackageCycle;
    	
    	@DynamoDBAttribute(attributeName = "S101_Fat")
    	private String s101_Fat;
    	
    	@DynamoDBAttribute(attributeName = "SQ_CodeSmells")
    	private String sq_CodeSmells;
    	
    	@DynamoDBAttribute(attributeName = "DV8_ModularityViolation")
    	private String dv8_ModularityViolation;
    	
    	@DynamoDBAttribute(attributeName = "DES_Size")
    	private String des_Size;
    	
    	@DynamoDBAttribute(attributeName = "DV8_LOC")
    	private String dv8_LOC;
    	
    	@DynamoDBAttribute(attributeName = "ARCH_CoChange Partners")
    	private String arch_CoChangePartners;
    	
    	@DynamoDBAttribute(attributeName = "ARCH_Bug Commits")
    	private String arch_BugCommits;
    	
    	@DynamoDBAttribute(attributeName = "DV8_TotalIssues")
    	private String dv8_TotalIssues;
    	
    	@DynamoDBAttribute(attributeName = "SCC_COMPLEXITY")
    	private String scc_COMPLEXITY;
    	
    	@DynamoDBAttribute(attributeName = "ARCH_Total Dependencies")
    	private String arch_TotalDependencies;
    	
    	@DynamoDBAttribute(attributeName = "SQ_Vulnerabilities")
    	private String sq_Vulnerabilities;
    	
    	@DynamoDBAttribute(attributeName = "SCC_CLOC")
    	private String scc_CLOC;
    	
    	@DynamoDBAttribute(attributeName = "DV8_TargetChangeCount")
    	private String dv8_TargetChangeCount;
    	
    	@DynamoDBAttribute(attributeName = "DV8_ChangeChurn")
    	private String dv8_ChangeChurn;
    	
    }

}
