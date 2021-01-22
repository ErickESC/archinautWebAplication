package mx.uam.izt.archinautInterface.services;

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
import mx.uam.izt.archinautInterface.bussines.InformeService;
import mx.uam.izt.archinautInterface.model.Informe;

@RestController
@Slf4j
public class InformeController {
	
	@Autowired
	private InformeService informeService;
	
	
	/**
	 * 
	 * @param nuevoInforme
	 * @return status creado y informe creado, status bad request en caso contrario
	 */
	@ApiOperation(
			value = "Crear Informe",
			notes = "Permite crear un nuevo Informe"
			)
	@PostMapping(path = "/informes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> create(@RequestBody Informe nuevoInforme) {
		
		log.info("Recibí llamada a create con "+nuevoInforme);
		
		Informe informe = informeService.create(nuevoInforme);
		
		if(informe != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(informe);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se puede crear informe");
		}
	}
	
	/**
	 * 
	 * @return status ok y lista de informes
	 */
	@ApiOperation(
			value = "Regresa todos los informes",
			notes = "Regresa un json con una lista de los informes en la BD"
			)
	@GetMapping(path = "/informes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> retrieveAll() {
		
		log.info("Resibi llama a retrive all");
		
		informeService.rellenaBD();
		
		Iterable <Informe> result = informeService.retriveAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}
}
