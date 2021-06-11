package mx.uam.izt.archinautInterface.mongodb.model;

import java.util.List;

import lombok.Data;

@Data
public class fileCronoResults {
	private String fileName;
	
	private List<Double> results;
}
