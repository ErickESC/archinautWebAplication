package mx.uam.izt.archinautInterface.bussines;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.izt.archinautInterface.data.ConfigurationsRepository;
import mx.uam.izt.archinautInterface.mongodb.model.ConfigurationsList;
import mx.uam.izt.archinautInterface.mongodb.model.MetricConfiguration;

@Service
public class ConfigurationService {
	
	@Autowired
	ConfigurationsRepository configRepository;
	
	/**
	 * 
	 * @return saves and metric configurations
	 */
	public ConfigurationsList create(ConfigurationsList configsList){
		
		return configRepository.save(configsList);
	}
	
	/**
	 * 
	 * @return saves a metric configuration
	 */
	public ConfigurationsList saveMetricConfiguration(ConfigurationsList newConfigList, String projectName){
		
		Optional<ConfigurationsList> configsList = configRepository.findById(projectName);
		
		if(configsList != null) {
			return configRepository.save(newConfigList);
		}else {
			return null;
		}
	}
	/**
	 * 
	 * @return retrieve the configuration of a metric
	 */
	public MetricConfiguration retrieveMetricConfiguration(String metricName, String projectName){
		Optional<ConfigurationsList> configsList = configRepository.findById(projectName);
		
		List<MetricConfiguration> metricsConfig = configsList.get().getMetricsConfigurations();
		
		for(int i=0; i<metricsConfig.size(); i++) {
			if(metricName == metricsConfig.get(i).getMetricName()) {
				return metricsConfig.get(i);
			}
		}
		return null;
	}
	/**
	 * 
	 * @return retrieve all the configurations
	 */
	public ConfigurationsList retrieveAll(String projectName){
		Optional<ConfigurationsList> configsList = configRepository.findById(projectName);

		return configsList.get();
	}
}