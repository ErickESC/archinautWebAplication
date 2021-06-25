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
	
	
	private String projectId;
	@Id
	private String commitID;
	
	private Date date;
	
	private HashMap<Integer, String> metricsMap;
	
	private List<File> files;

}
