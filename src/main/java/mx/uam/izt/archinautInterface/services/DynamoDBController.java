package mx.uam.izt.archinautInterface.services;

import java.util.ArrayList;
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
import mx.uam.izt.archinautInterface.bussines.DynamoDBService;
import mx.uam.izt.archinautInterface.dynamodb.model.Messages;

@RestController
@Slf4j
public class DynamoDBController {
	
	@Autowired
	DynamoDBService dbService;
	
	/**
	 * 
	 * @return status ok y lista de los reportes
	 */
	@ApiOperation(
			value = "Regresa todos los reportes asociados al id pasado",
			notes = "Regresa un json con una lista de los informes asociados al id pasado"
			)
	@GetMapping(path = "/reports/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieve(@PathVariable("id") String id){
		log.info("Regresando reportes asociados a "+id);
		
		List<Messages>  reportList = new ArrayList<>();
		reportList = dbService.retreveAll(id);
		
		if(reportList != null) {
			return ResponseEntity.status(HttpStatus.OK).body(reportList);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron reportes asociados");
		}
	}

}
