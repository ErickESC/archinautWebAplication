package mx.uam.izt.archinautInterface.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
			value = "Retrieves all the reports saved in the DB",
			notes = "Retrieves a Json with a list of the reports"
			)
	@GetMapping(path = "/mongo/retrieve", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		List<AnalysisResult> archResult = mongoRepository.findAll();
		
		log.info("datos procesados:" + archResult);
		
		return ResponseEntity.status(HttpStatus.OK).body(archResult);
		
	}
}
