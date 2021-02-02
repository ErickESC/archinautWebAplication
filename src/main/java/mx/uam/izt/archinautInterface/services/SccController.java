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

@RestController
@Slf4j
public class SccController {
	
	@Autowired
	private ReportService reportService;
	
	/**
	 * 
	 * @return status ok y lista de datos ya procesados para SCC
	 */
	@SuppressWarnings("rawtypes")
	@ApiOperation(
			value = "Regresa todos los datos ya procesados para SCC",
			notes = "Regresa un json con una lista de los datos procesados"
			)
	@GetMapping(path = "/report/scc/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll(@PathVariable("id") String id) {
		
		List <List> result = reportService.retriveScc(id);
		
		log.info("datos procesados para SCC");
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}
}
