package mx.uam.izt.archinautInterface.services;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.izt.archinautInterface.bussines.ReportService;
import mx.uam.izt.archinautInterface.mongodb.model.AnalysisResult;
import mx.uam.izt.archinautInterface.mongodb.model.FileCronoResults;

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
	@GetMapping(path = "/tool/{toolName}/report/results", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll(@PathVariable("toolName") String toolName) {
		
		List <FileCronoResults> result = reportService.retriveToolAnalysis(toolName);
		
		log.info("datos procesados");
		
		if(result != null) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Error getting analysis");
		}	
	}
	
	/**
	 * 
	 * @return status OK and the tool list
	 */
	@ApiOperation(
			value = "Regresa una lista de las herramientas utilizadas en el proyecto",
			notes = "Regresa un json con una lista de herramientas"
			)
	@GetMapping(path = "tool/list/report/{idPoject}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveEvo(@PathVariable("idPoject") String idPoject) {
		
		log.info("lista de herramientas");
		
		List <String> result = reportService.retriveToolList(idPoject);
		
		if(result != null) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Error getting metrics list");
		}
	}
	
	/**
	 * 
	 * @return status OK
	 * @throws Exception 
	 */
	@ApiOperation(
			value = "Recibe json de Archinaut",
			notes = "Recibe un json del analysis recien salido de archinaut"
			)
	@PostMapping(path = "/report/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody JSONObject archResult) throws Exception {
		
		AnalysisResult newAnalysisResult = reportService.saveArchinautReport(archResult);
		
		if(newAnalysisResult != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(newAnalysisResult);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error saving archinaut report");
		}
		
	}
}
