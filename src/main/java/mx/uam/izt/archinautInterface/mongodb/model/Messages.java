package mx.uam.izt.archinautInterface.mongodb.model;

import java.util.List;

import lombok.Data;

@Data
public class Messages {
	
	private String id;
	
	private String idCommit;
	
	private String analysis;
	
	private String date;
	
	private List<Files> files;

}
