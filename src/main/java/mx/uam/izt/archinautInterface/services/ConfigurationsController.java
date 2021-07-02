package mx.uam.izt.archinautInterface.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
			value = "Regresa todos las configuraciones por metrica",
			notes = "Regresa un json con una lista de los datos procesados"
			)
	@GetMapping(path = "/tool/configuration/project/{projectName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll(@PathVariable("projectName") String projectName) {
		
		ConfigurationsList result = configurationService.retrieveAll(projectName);
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}
	
	/**
	 * 
	 * @param nueva lista de cinfiguraciones
	 * @return status creado y lista creada
	 */
	@ApiOperation(
			value = "Crear lista de configuraciones e metricas",
			notes = "Permite crear un nuevo pokemon"
			)
	@PostMapping(path = "/tool/configuration/list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody ConfigurationsList newConfigList) {
		
		log.info("Recibí llamada a create con "+newConfigList);
		
		ConfigurationsList configList = configurationService.create(newConfigList);
		
		if(configList != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(configList);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear pokemon");
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
	@PutMapping(path = "/tool/configuration/update/project/{projectName}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> update(@PathVariable("projectName") String projectName, @RequestBody ConfigurationsList newConfigList) {
		
		log.info("Recibí llamada a update con "+ newConfigList);
		
		ConfigurationsList configList = configurationService.saveMetricConfiguration(newConfigList, projectName);
				
		return ResponseEntity.status(HttpStatus.OK).body(configList);

	}
}
