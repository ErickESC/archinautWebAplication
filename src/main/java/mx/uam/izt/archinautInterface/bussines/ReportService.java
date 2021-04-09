package mx.uam.izt.archinautInterface.bussines;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.izt.archinautInterface.mongodb.model.Messages;

@Service
@Slf4j
public class ReportService {
	
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
		
		return crono;
	}
	
	/**
	 * 
	 * @return Lista de los Informes para Scc
	 */
	public List <List<String[]>> retriveScc(String id){
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
		log.info("Buscando estadisticas planas para Scc");
		
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
			int Dpartners, DoPartners, TDependencies, promDP=0, promDOP=0, promTD=0;
			
			//Guardamos las variables de Depends
			Dpartners = Integer.parseInt(analysisPC[8]);
			DoPartners = Integer.parseInt(analysisPC[9]);
			TDependencies = Integer.parseInt(analysisPC[10]);
			
			//Recorre los reportes para buscar como han cambiado esos valores para poder sacar un promedio
			for(int j=0; j<crono.size(); j++) {
				analysisPC = crono.get(j).get(i);
				promDP += Integer.parseInt(analysisPC[8]);
				promDOP += Integer.parseInt(analysisPC[9]);
				promTD += Integer.parseInt(analysisPC[10]);
			}
			//Saca promedios
			promDP = promDP/crono.size();
			promDOP = promDOP/crono.size();
			promTD = promTD/crono.size();
			
			//Si el promedio es igual al primer valor significa que siempre ha tenido los mismos valores
			if(promDP == Dpartners && promDOP == DoPartners && promTD == TDependencies) {
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
	 * Remueve el archivo cuyo reporte tenga los 3 Css planos
	 */
	public List<List<String[]>> removeFlatSCC(List<List<String[]>> crono){
		int i, Tfiles;
		
		Tfiles = 1557;
		
		log.info("procesando estadisticas planas para Depends");
		for(i=1; i<Tfiles/*crono.get(0).size()*/;i++) {
		
			String[] analysisPC = crono.get(0).get(i);//Guarda el analisis por clase en un arreglo independiente
			int Dpartners, DoPartners, TDependencies, promDP=0, promDOP=0, promTD=0;
			
			//Guardamos las variables de SCC
			Dpartners = Integer.parseInt(analysisPC[4]);
			DoPartners = Integer.parseInt(analysisPC[5]);
			TDependencies = Integer.parseInt(analysisPC[6]);
			
			//Recorre los reportes para buscar como han cambiado esos valores para poder sacar un promedio
			for(int j=0; j<crono.size(); j++) {
				analysisPC = crono.get(j).get(i);
				promDP += Integer.parseInt(analysisPC[4]);
				promDOP += Integer.parseInt(analysisPC[5]);
				promTD += Integer.parseInt(analysisPC[6]);
			}
			//Saca promedios
			promDP = promDP/crono.size();
			promDOP = promDOP/crono.size();
			promTD = promTD/crono.size();
			
			//Si el promedio es igual al primer valor significa que siempre ha tenido los mismos valores
			if(promDP == Dpartners && promDOP == DoPartners && promTD == TDependencies) {
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
	 * Remueve el archivo si Revisions, CoChange Partners, Bug Commits y Changed lines siempre han sido cero
	 */
	@SuppressWarnings("rawtypes")
	public List<List> removeZeros(){
		List <List> crono = new ArrayList<List>();
		return crono;
	}
}