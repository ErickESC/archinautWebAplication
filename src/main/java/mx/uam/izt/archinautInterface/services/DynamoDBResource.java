package mx.uam.izt.archinautInterface.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import mx.uam.izt.archinautInterface.data.DynamoDBRepository;
import mx.uam.izt.archinautInterface.dynamodb.model.Messages;

@RestController
public class DynamoDBResource {
	
	@Autowired
	DynamoDBRepository dbRepo;
	
	/**
	 * 
	 * @return status ok y lista de los reportes
	 */
	@ApiOperation(
			value = "Regresa todos los reportes asociados al id pasado",
			notes = "Regresa un json con una lista de los informes asociados al id pasado"
			)
	@GetMapping(value="getAllValues")
	public List<Messages> getAllValues(){
		List<Messages>  reportList = new ArrayList<>();
		reportList = dbRepo.getDetails();
		
		return reportList;
	}

}
