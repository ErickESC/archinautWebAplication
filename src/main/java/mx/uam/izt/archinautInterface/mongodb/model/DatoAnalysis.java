package mx.uam.izt.archinautInterface.mongodb.model;

import lombok.Data;

@Data
public class DatoAnalysis {

	private String ARCH_Revisions;

	private String SCC_LOC;

	private String DV8_UnstableInterface;

	private String S101_ClassTangles;

	private String DES_Complexity;

	private String ARCH_DependsonPartners;

	private String ARCH_DependentPartners;

	private String DV8_IsCenter;

	private String DV8_isUnstableInterface;

	private String DV8_PresentInIssues;

	private String SQ_SecuritySpots;

	private String DV8_Clique;

	private String S101_PkgTangles;

	private String DV8_Crossing;

	private String SQ_Bugs;

	private String DV8_ChangeCount;

	private String DV8_UnhealthyInheritance;

	private String DV8_TargetChurn;

	private String ARCH_Churn;

	private String DES_DesignSmells;

	private String SQ_Issues;

	private String DV8_PackageCycle;

	private String S101_Fat;

	private String SQ_CodeSmells;

	private String DV8_ModularityViolation;

	private String DES_Size;

	private String DV8_LOC;

	private String ARCH_CoChangePartners;

	private String ARCH_BugCommits;

	private String DV8_TotalIssues;

	private String SCC_COMPLEXITY;

	private String ARCH_TotalDependencies;

	private String SQ_Vulnerabilities;

	private String SCC_CLOC;
	
	private String DV8_TargetChangeCount;

	private String DV8_ChangeChurn;

}
