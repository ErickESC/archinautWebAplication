package mx.uam.izt.archinautInterface.mongodb.model;

import lombok.Data;

@Data
public class MetricConfiguration {
	
	private String metricName;
	
	private Double threshold;
}
