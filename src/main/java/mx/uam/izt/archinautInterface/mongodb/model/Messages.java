package mx.uam.izt.archinautInterface.mongodb.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Messages {
	
	private String projectId;
	
	private String commitID;
	
	private Date date;
	
	private List<Files> files;

}
