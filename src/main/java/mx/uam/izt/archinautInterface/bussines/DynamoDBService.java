package mx.uam.izt.archinautInterface.bussines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.izt.archinautInterface.data.DynamoDBRepository;
import mx.uam.izt.archinautInterface.dynamodb.model.Messages;

@Service
@Slf4j
public class DynamoDBService {
	
	@Autowired
	DynamoDBRepository dbRepo;
	
	/*
	 * @return Lista de los Informes asociados al id
	 */
	public List<Messages> retreveAll(String id){
		log.info("Buscando reportes asociados a "+id);
		List<Messages>  reportList = new ArrayList<>();
		reportList = dbRepo.getDetails(id);
		
		log.info("Procesando reportes");//Acomodarlos cronologicamente
		List<Messages>  cronoList = new ArrayList<>();
		
		
		/*Collections.sort(reportList);
		
		
		cronoList.add(reportList.get(0));
		
		Messages latest = reportList.get(0);
		String[] aux, aux2;
		String cad;
		int date, older;
		
		//Se asigna el primer reporte como el mas viejo
		aux = reportList.get(0).getDate().split(" ");
		aux2 = aux[0].split("/");
		cad = aux2[0]+aux2[1]+aux2[2];
		date = Integer.valueOf(cad);
		older = date;
		
		for(int i=1; i<reportList.size(); i++) {
			aux = reportList.get(i).getDate().split(" ");
			aux2 = aux[0].split("/");
			cad = aux2[0]+aux2[1]+aux2[2];
			date = Integer.valueOf(cad);
			log.info(""+date);
		}*/
		return reportList;
	}

}
