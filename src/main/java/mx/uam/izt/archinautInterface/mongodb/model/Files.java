package mx.uam.izt.archinautInterface.mongodb.model;

import java.util.HashMap;

import lombok.Data;

@Data
public class Files {
	
	private String fileName;
	
	private HashMap<Integer, Double> toolResult;//Integer(Key to know the tool) and Double(tool result) HashMap to save the result per file
}
