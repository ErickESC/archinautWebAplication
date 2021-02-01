package mx.uam.izt.archinautInterface.services;

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
import mx.uam.izt.archinautInterface.model.Datas;

@RestController
@Slf4j
public class DpndsController {
	
	@Autowired
	private ReportService reportService;
	
	/**
	 * 
	 * @return status ok y lista de datos ya procesados para Depends
	 */
	@ApiOperation(
			value = "Regresa todos los datos ya procesados para Depens",
			notes = "Regresa un json con una lista de los datos procesados"
			)
	@GetMapping(path = "/report/dpnds/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll(@PathVariable("id") String id) {
		
		Iterable <Datas> result = reportService.retriveDpnds(id);
		
		log.info("datos procesados para Depends");
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}
}
