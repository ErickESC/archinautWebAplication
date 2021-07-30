package mx.uam.izt.archinautInterface.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.izt.archinautInterface.bussines.ConfigurationService;
import mx.uam.izt.archinautInterface.mongodb.model.ConfigurationsList;

@RestController
@Slf4j
public class ConfigurationsController {
	@Autowired
	private ConfigurationService configurationService;
	
	/**
	 * 
	 * @return status OK and the metrics configurations
	 */
	@ApiOperation(
			value = "Retrieves the file threshold configuration per metric",
			notes = "Retrieves a Json with the list of thresholds and files"
			)
	@GetMapping(path = "/tool/configuration/project/{idProject}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll(@PathVariable("idProject") String idProject) {
		
		ConfigurationsList result = configurationService.retrieveAll(idProject);
		
		if(result != null) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Error saving configurations");
		}
	}
	
	
	/**
	 * 
	 * @param metric name and project name
	 * @return status ok y configuracion actualizada, status no content en caso contrario, status conflict en caso de error
	 */
	@ApiOperation(
			value = "Actualiza lista de metricas",
			notes = "Permite actualizar los datos de una lista de metricas existente en la DB"
			)
	@PutMapping(path = "/tool/configuration/update/project/{idProject}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@PathVariable("idProject") String idProject, @RequestBody ConfigurationsList newConfigList) {
		
		log.info("Recibí llamada a update con "+ newConfigList);
		
		ConfigurationsList configList = configurationService.saveMetricConfiguration(newConfigList, idProject);
		
		if(configList != null) {
			return ResponseEntity.status(HttpStatus.OK).body(configList);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating configurations");
		}
		
	}
}
