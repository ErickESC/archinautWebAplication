package mx.uam.izt.archinautInterface.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.izt.archinautInterface.bussines.ReportService;
import mx.uam.izt.archinautInterface.mongodb.model.fileCronoResults;

@RestController
@Slf4j
public class ReportsController {
	
	@Autowired
	private ReportService reportService;
	
	/**
	 * 
	 * @return status OK and the chronological data
	 */
	@ApiOperation(
			value = "Regresa todos los datos ya procesados de acuerdo a la herramienta seleccionada",
			notes = "Regresa un json con una lista de los datos procesados"
			)
	@GetMapping(path = "/report/{idPoject}/tool/{toolName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll(@PathVariable("idPoject") String idPoject, @PathVariable("toolName") String toolName) {
		
		List <fileCronoResults> result = reportService.retriveToolAnalysis(toolName);
		
		log.info("datos procesados");
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}
	
	/**
	 * 
	 * @return status OK and the tool list
	 */
	@ApiOperation(
			value = "Regresa una lista de las herramientas utilizadas en el proyecto",
			notes = "Regresa un json con una lista de herramientas"
			)
	@GetMapping(path = "/report/tool/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveEvo() {
		
		log.info("lista de herramientas");
		
		List <String> result = reportService.retriveToolList();
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}
}
