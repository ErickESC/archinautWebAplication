package mx.uam.izt.archinautInterface.bussines;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.izt.archinautInterface.data.ConfigurationsRepository;
import mx.uam.izt.archinautInterface.mongodb.model.configurationsList;
import mx.uam.izt.archinautInterface.mongodb.model.metricConfiguration;

@Service
@Slf4j
public class ConfigurationService {
	
	@Autowired
	ConfigurationsRepository configRepository;
	
	/**
	 * 
	 * @return saves and metric configurations
	 */
	public configurationsList create(configurationsList configsList){
		
		return configRepository.save(configsList);
	}
	
	/**
	 * 
	 * @return saves a metric configuration
	 */
	public configurationsList saveMetricConfiguration(metricConfiguration newConfig, String projectName){
		Optional<configurationsList> configsList = configRepository.findById(projectName);
		
		List<metricConfiguration> metricsConfig = configsList.get().getMetricsConfigurations();
		
		if(metricsConfig.isEmpty()) {
			log.info("Estaba vacia agregue: "+ metricsConfig);
			configsList.get().getMetricsConfigurations().add(newConfig);
		}else {
			log.info("No estaba vacia agregue: "+ metricsConfig);
			for(int i=0; i<metricsConfig.size(); i++) {
				if(newConfig.getMetricName() == metricsConfig.get(i).getMetricName()) {
					configsList.get().getMetricsConfigurations().set(i, newConfig);
				}else {
					configsList.get().getMetricsConfigurations().add(newConfig);
				}
			}
		}
		
		return configRepository.save(configsList.get());
	}
	/**
	 * 
	 * @return retrieve the configuration of a metric
	 */
	public metricConfiguration retrieveMetricConfiguration(String metricName, String projectName){
		Optional<configurationsList> configsList = configRepository.findById(projectName);
		
		List<metricConfiguration> metricsConfig = configsList.get().getMetricsConfigurations();
		
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
	public configurationsList retrieveAll(String projectName){
		Optional<configurationsList> configsList = configRepository.findById(projectName);

		return configsList.get();
	}
}