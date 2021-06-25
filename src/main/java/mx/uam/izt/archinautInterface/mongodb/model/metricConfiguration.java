package mx.uam.izt.archinautInterface.mongodb.model;

import lombok.Data;

@Data
public class metricConfiguration {
	
	private String metricName;
	
	private Double threshold;
}
