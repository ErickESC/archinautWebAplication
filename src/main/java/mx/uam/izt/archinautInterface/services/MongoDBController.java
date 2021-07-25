package mx.uam.izt.archinautInterface.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import mx.uam.izt.archinautInterface.data.MongoDBRepository;
import mx.uam.izt.archinautInterface.mongodb.model.AnalysisResult;

@RestController
@Slf4j
public class MongoDBController {
	
	@Autowired
	MongoDBRepository mongoRepository;
	
	@ApiOperation(
			value = "Recibe json",
			notes = "Recibe un json del analysis"
			)
	@PostMapping(path = "/mongo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody AnalysisResult archResult) {
		
		log.info("Analysis: " + archResult);
		
		mongoRepository.save(archResult);
		
		return ResponseEntity.status(HttpStatus.OK).body(archResult);
		
	}
	
	@ApiOperation(
			value = "Regresa todos los datos ya procesados para Depens",
			notes = "Regresa un json con una lista de los datos procesados"
			)
	@GetMapping(path = "/mongo/retrieve", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		List<AnalysisResult> archResult = mongoRepository.findAll();
		
		log.info("datos procesados:" + archResult);
		
		return ResponseEntity.status(HttpStatus.OK).body(archResult);
		
	}
}
