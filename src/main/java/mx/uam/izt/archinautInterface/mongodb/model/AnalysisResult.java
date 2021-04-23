package mx.uam.izt.archinautInterface.mongodb.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class AnalysisResult {//Analysis Result
	@Id
	private String projectId;
	
	private String commitID;
	
	private Date date;
	
	private HashMap<Integer, String> toolMap;
	
	private List<Files> files;

}
