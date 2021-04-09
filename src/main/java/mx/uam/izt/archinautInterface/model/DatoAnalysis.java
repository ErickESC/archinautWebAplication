package mx.uam.izt.archinautInterface.model;

import lombok.Data;

@Data
public class DatoAnalysis {
	
	@DynamoDBAttribute(attributeName = "ARCH_Revisions")
	private String ARCH_Revisions;
	
	@DynamoDBAttribute(attributeName = "SCC_LOC")
	private String SCC_LOC;
	
	@DynamoDBAttribute(attributeName = "DV8_UnstableInterface")
	private String DV8_UnstableInterface;
	
	@DynamoDBAttribute(attributeName = "S101_ClassTangles")
	private String S101_ClassTangles;
	
	@DynamoDBAttribute(attributeName = "DES_Complexity")
	private String DES_Complexity;
	
	@DynamoDBAttribute(attributeName = "ARCH_Depends on Partners")
	private String ARCH_DependsonPartners;
	
	@DynamoDBAttribute(attributeName = "ARCH_Dependent Partners")
	private String ARCH_DependentPartners;
	
	@DynamoDBAttribute(attributeName = "DV8_IsCenter")
	private String DV8_IsCenter;
	
	@DynamoDBAttribute(attributeName = "DV8_isUnstableInterface")
	private String DV8_isUnstableInterface;
	
	@DynamoDBAttribute(attributeName = "DV8_PresentInIssues")
	private String DV8_PresentInIssues;
	
	@DynamoDBAttribute(attributeName = "SQ_SecuritySpots")
	private String SQ_SecuritySpots;
	
	@DynamoDBAttribute(attributeName = "DV8_Clique")
	private String DV8_Clique;
	
	@DynamoDBAttribute(attributeName = "S101_PkgTangles")
	private String S101_PkgTangles;
	
	@DynamoDBAttribute(attributeName = "DV8_Crossing")
	private String DV8_Crossing;
	
	@DynamoDBAttribute(attributeName = "SQ_Bugs")
	private String SQ_Bugs;
	
	@DynamoDBAttribute(attributeName = "DV8_ChangeCount")
	private String DV8_ChangeCount;
	
	@DynamoDBAttribute(attributeName = "DV8_UnhealthyInheritance")
	private String DV8_UnhealthyInheritance;
	
	@DynamoDBAttribute(attributeName = "DV8_TargetChurn")
	private String DV8_TargetChurn;
	
	@DynamoDBAttribute(attributeName = "ARCH_Churn")
	private String ARCH_Churn;
	
	@DynamoDBAttribute(attributeName = "DES_Design Smells")
	private String DES_DesignSmells;
	
	@DynamoDBAttribute(attributeName = "SQ_Issues")
	private String SQ_Issues;
	
	@DynamoDBAttribute(attributeName = "DV8_PackageCycle")
	private String DV8_PackageCycle;
	
	@DynamoDBAttribute(attributeName = "S101_Fat")
	private String S101_Fat;
	
	@DynamoDBAttribute(attributeName = "SQ_CodeSmells")
	private String SQ_CodeSmells;
	
	@DynamoDBAttribute(attributeName = "DV8_ModularityViolation")
	private String DV8_ModularityViolation;
	
	@DynamoDBAttribute(attributeName = "DES_Size")
	private String DES_Size;
	
	@DynamoDBAttribute(attributeName = "DV8_LOC")
	private String DV8_LOC;
	
	@DynamoDBAttribute(attributeName = "ARCH_CoChange Partners")
	private String ARCH_CoChangePartners;
	
	@DynamoDBAttribute(attributeName = "ARCH_Bug Commits")
	private String ARCH_BugCommits;
	
	@DynamoDBAttribute(attributeName = "DV8_TotalIssues")
	private String DV8_TotalIssues;
	
	@DynamoDBAttribute(attributeName = "SCC_COMPLEXITY")
	private String SCC_COMPLEXITY;
	
	@DynamoDBAttribute(attributeName = "ARCH_Total Dependencies")
	private String ARCH_TotalDependencies;
	
	@DynamoDBAttribute(attributeName = "SQ_Vulnerabilities")
	private String SQ_Vulnerabilities;
	
	@DynamoDBAttribute(attributeName = "SCC_CLOC")
	private String SCC_CLOC;
	
	@DynamoDBAttribute(attributeName = "DV8_TargetChangeCount")
	private String DV8_TargetChangeCount;
	
	@DynamoDBAttribute(attributeName = "DV8_ChangeChurn")
	private String DV8_ChangeChurn;

}
