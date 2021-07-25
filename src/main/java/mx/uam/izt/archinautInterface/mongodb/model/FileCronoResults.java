package mx.uam.izt.archinautInterface.mongodb.model;

import java.util.List;

import lombok.Data;

@Data
public class FileCronoResults {
	private String fileName;
	
	private List<Double> results;
	//To save if the file is decreasing or improving
	private String tendency;
}
