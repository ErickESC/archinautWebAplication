package mx.uam.izt.archinautInterface.mongodb.model;

import java.util.List;

import lombok.Data;

@Data
public class Files {
	
	private String fileName;
	
	private List<Datos> datos;
}
