package mx.uam.izt.archinautInterface.bussines;

import java.util.ArrayList;
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
		
		//Mensajes auxiliares
		Messages objlist, objAct;
		
		//Se guarda el primer reporte
		cronoList.add(reportList.get(0));
		
		for(int i=1; i<reportList.size(); i++) {
			
			objAct = reportList.get(i);
			
			//Variables donde se guardaran los ASCII
			int asciilist=0, asciiAct=0;
			
			//Sumamos los ASCII de cada string hasta el caracter de los minutos
			//Lo anterior es debido a que en vez de buscar los numeros e ir comparando, trae el mismo resultado sumar los ASCII y despues compararlos
			
			//Saca ascii de la fecha del objeto en turno
			for(int k=0; k<16;k++) {
				asciiAct += objAct.getDate().codePointAt(k);
			}
			
			//Compara con los que ya estan en la lista
			for(int j=0; j<cronoList.size();j++) {
				
				objlist = cronoList.get(j);
				
				//Ascii del objeto en cronoList
				for(int l=0; l<16;l++) {
					asciilist += objlist.getDate().codePointAt(l);
				}
				
				//Compara si es menor se incerta antes
				if(asciiAct < asciilist) {
					if(j==0) {
						cronoList.add(0, objAct);
					}else {
						cronoList.add(j-1, objAct);
					}
					break;
				}else if(asciiAct == asciilist) {//Si llego a este punto es porque son iguales y debemos comprarar los segundos
					
					int seg1, seg2;
					
					seg1 = objlist.getDate().codePointAt(17) + objlist.getDate().codePointAt(18);
					seg2 = objAct.getDate().codePointAt(17) + objAct.getDate().codePointAt(18);
					
					//Compara si es mayor
					if(seg2 > seg1) {
						cronoList.add(j+1, objAct);//Se inserta despues
					}else {//Entonces es menor
						if(j==0) {
							cronoList.add(0, objAct);
						}else {
							cronoList.add(j-1, objAct);
						}
					}
					break;
				}
			}
		}
		log.info("Termino de acomodar cronologicamente");
		
		return cronoList;
	}

}
