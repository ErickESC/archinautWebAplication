package mx.uam.izt.archinautInterface.bussines;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.izt.archinautInterface.dynamodb.model.Messages;
import mx.uam.izt.archinautInterface.model.Datas;

@Service
@Slf4j
public class ReportService {
	
	@Autowired
	DynamoDBService dbService;
	
	/**
	 * 
	 * @param Id
	 * @return Informe al que le pertenece la matricula
	 *
	public Informe retrive(String IdCommit){
		log.info("Llamado a regresar el informe "+IdCommit);
		
		Message informeOpt = dbService.findByIdCommit(IdCommit);
		return informeOpt;
	}*/
	
	
	/**
	 * 
	 * @return Lista de los Informes para Depends
	 */
	public Iterable <Datas> retriveDpnds(String id){
		log.info("Regresando arreglo con datos");
		String report, aux="";
		Datas dato = new Datas();
		
		List <Datas> lista = new ArrayList<>();
		List <Datas> listaAux = new ArrayList<>();
		
		Iterable <Messages> informes = dbService.retreveAll(id);
		
		Iterator<Messages> it = informes.iterator();
		
		//Se guardan los datos por cada archivo del reporte en una lista
		while(it.hasNext()) {
			report = it.next().getAnalysis().toString();
			for(int i=0; i<report.length();i++) {
				if(report.charAt(i)!= '\n') {
					aux = aux+report.charAt(i);
				}else {
					dato.setIdCommit(it.next().getIdCommit());
					dato.setInfo(aux);
					lista.add(dato);
				}
			}
		}
		
		for(int i=0; i<lista.size();i++) {
			
			String info;
			
			info=lista.get(i).getInfo();
			
			String[] datos = info.split(",");
			
			//Comienza Logica para Depends
			if(Integer.parseInt(datos[12])>2 && Integer.parseInt(datos[11])>0) {
				if(!listaAux.contains(lista.get(i))) {
					listaAux.add(lista.get(i));
				}
			}
		}
		return listaAux;
	}
	
	/**
	 * 
	 * @return Lista de los Informes para Scc
	 */
	public Iterable <Datas> retriveScc(String id){
		log.info("Regresando arreglo con datos");
		String report, aux="";
		Datas dato = new Datas();
		
		List <Datas> lista = new ArrayList<>();
		List <Datas> listaAux = new ArrayList<>();
		
		Iterable <Messages> informes = dbService.retreveAll(id);
		
		Iterator<Messages> it = informes.iterator();
		
		//Se guardan los datos por cada archivo del reporte en una lista
		while(it.hasNext()) {
			report = it.next().getAnalysis().toString();
			for(int i=0; i<report.length();i++) {
				if(report.charAt(i)!= '\n') {
					aux = aux+report.charAt(i);
				}else {
					dato.setIdCommit(it.next().getIdCommit());
					dato.setInfo(aux);
					lista.add(dato);
				}
			}
		}
		
		for(int i=0; i<lista.size();i++) {
			
			String info;
			
			info=lista.get(i).getInfo();
			
			String[] datos = info.split(",");
			
			//Comienza Logica para SCC
			if(Integer.parseInt(datos[12])>2 && Integer.parseInt(datos[11])>0) {
				if(!listaAux.contains(lista.get(i))) {
					listaAux.add(lista.get(i));
				}
			}
		}
		return listaAux;
	}	
}