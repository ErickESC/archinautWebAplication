package mx.uam.izt.archinautInterface.bussines;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.izt.archinautInterface.dynamodb.model.Messages;

@Service
@Slf4j
public class DependsService {
	
	@Autowired
	DynamoDBService dbService;
	
	/**
	 * 
	 * @return Lista de los Informes para Depends
	 */
	public List <List<String[]>> retriveDpnds(String id){
		log.info("Regresando arreglo con datos");
		String[] classe, analysisPC;
		
		List <List<String[]>> crono = new ArrayList<List<String[]>>();
		
		Iterable <Messages> informes = dbService.retreveAll(id);
		
		Iterator<Messages> it = informes.iterator();
		
		//Se guardan los datos por cada archivo del reporte en una lista
		while(it.hasNext()) {
			classe = it.next().getAnalysis().split("\n");//Separamos en files el analisis
			List<String[]> report = new ArrayList<>();
			for(int i=0; i<classe.length;i++) {
					analysisPC = classe[i].split(",");//Separamos el file en cada uno de los datos
					report.add(analysisPC);//Guarda cada reporte de cada clase en una lista
			}
			crono.add(report);//Guarda cada reporte en orden cronologico
		}
		
		//Comienza logica
		log.info("Buscando estadisticas planas para Depends");
		crono = removeFlatDpnds(crono);
		
		log.info("Buscando estadisticas encima del promedio para Depends");
		crono = removeMinums(crono);
		
		return crono;
	}
	
	/*
	 * Remueve el archivo cuyo reporte tenga los 3 depends planos 
	 */
	public List<List<String[]>> removeFlatDpnds(List<List<String[]>> crono){
		
		int i, Tfiles;
		
		Tfiles = 1557;
		
		log.info("procesando estadisticas planas para Depends");
		for(i=1; i<Tfiles/*crono.get(0).size()*/;i++) {
		
			String[] analysisPC = crono.get(0).get(i);//Guarda el analisis por clase en un arreglo independiente
			int TDependencies, promTD=0;
			
			//Guardamos el total de dependencias inicial
			TDependencies = Integer.parseInt(analysisPC[10]);
			
			//Recorre los reportes para buscar como han cambiado esos valores para poder sacar un promedio
			for(int j=0; j<crono.size(); j++) {
				analysisPC = crono.get(j).get(i);
				promTD += Integer.parseInt(analysisPC[10]);
			}
			//Saca promedio
			promTD = promTD/crono.size();
			
			//Si el promedio es igual al primer valor significa que siempre ha tenido los mismos valores
			if(promTD == TDependencies) {
				//Elimina las clases con estadisticas plana
				for(int k=0; k<crono.size(); k++) {
					crono.get(k).remove(i);
				}
				//Eliminar la siguiente linea cuando El agregar clases sea soportado
				i--;
				Tfiles--;
			}
		}
		
		return crono;
	}
	
	/*
	 * Remueve el archivo si el total de dependencias del ultimo reporte es menor al promedio
	 */
	public List<List<String[]>> removeMinums(List<List<String[]>> crono){
		log.info("procesando estadisticas arriba del promedio");
		
		int i,promTD=0,lnght;
		
		lnght = crono.size()-1;
		//Se hace respectivo al numero de archivos que hay en el ultimo reporte
		
		//Comienza promedio
		for(i=1; i<crono.get(lnght).size();i++) {
			
			String[] analysisPC = crono.get(lnght).get(i);//Guarda el analisis por clase en un arreglo independiente
			
			//Comienzan las sumas
			promTD += Integer.parseInt(analysisPC[10]);
		}
		
		//Saca promedio
		promTD = promTD/crono.get(lnght).size();
		
		//Comienza eliminacion
		for(i=1; i<410/*crono.get(lnght).size()*/;i++) {
			
			if(i==crono.get(lnght).size()) {
				break;
			}
			
			String[] analysisPC = crono.get(lnght).get(i);//Guarda el analisis por clase en un arreglo independiente
			
			int TDependencies;
			
			TDependencies = Integer.parseInt(analysisPC[10]);
			
			//Se eliminan si el total de dependencias es menor que el promedio
			if(TDependencies <= promTD) {
				
				for(int k=0; k<crono.size(); k++) {
					crono.get(k).remove(i);
				}
				i--;
			}
		}
		
		return crono;
	}
}