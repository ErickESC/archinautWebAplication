package mx.uam.izt.archinautInterface.mongodb.model;

import java.util.HashMap;

import lombok.Data;

@Data
public class File {
	
	private String fileName;
	
	private HashMap<Integer, Double> metricResult;//Integer(Key to know the tool) and Double(tool result) HashMap to save the result per file
}
