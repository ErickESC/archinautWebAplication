package mx.uam.izt.archinautInterface.mongodb.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class configurationsList {
	@Id
	private String projectName;
	
	private List<metricConfiguration> metricsConfigurations;
}
