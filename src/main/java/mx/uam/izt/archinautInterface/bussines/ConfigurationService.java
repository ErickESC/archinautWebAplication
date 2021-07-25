package mx.uam.izt.archinautInterface.bussines;

import java.util.ArrayList;
import java.util.HashMap;
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
	public ConfigurationsList create(String idProject, HashMap<Integer, String> metricsMap){
		
		ConfigurationsList newConfigsList = new ConfigurationsList();
		
		List<MetricConfiguration> newMetricsConfigurations = new ArrayList<>();
		
		if(configRepository.existsById(idProject)) {
			newConfigsList = configRepository.findById(idProject).get();
			newMetricsConfigurations = newConfigsList.getMetricsConfigurations();
			
			int i;
			
			for(String metric : metricsMap.values()) {
				for(i=0; i<newMetricsConfigurations.size(); i++) {
					if(newMetricsConfigurations.get(i).getMetricName() == metric) {
						break;
					}
				}
				if(i==newMetricsConfigurations.size()) {//It means that does not contain the metric
					MetricConfiguration newMetricConfiguration = new MetricConfiguration();
					newMetricConfiguration.setMetricName(metric);
					newMetricConfiguration.setThreshold(0.0);
					
					newMetricsConfigurations.add(newMetricConfiguration);
				}
			}
			newConfigsList.setMetricsConfigurations(newMetricsConfigurations);
			
			return configRepository.save(newConfigsList);
			
		}else {//It is a new config list
			for(String metric : metricsMap.values()) {
				MetricConfiguration newMetricConfiguration = new MetricConfiguration();
				newMetricConfiguration.setMetricName(metric);
				newMetricConfiguration.setThreshold(0.0);
				
				newMetricsConfigurations.add(newMetricConfiguration);
			}
			
			newConfigsList.setProjectName(idProject);
			newConfigsList.setMetricsConfigurations(newMetricsConfigurations);
			
			return configRepository.save(newConfigsList);
		}
		
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