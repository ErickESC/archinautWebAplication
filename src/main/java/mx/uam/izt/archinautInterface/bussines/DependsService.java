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
	
	private List<String> evolution = new ArrayList<>();
	
	/*
	 * Regresa la lista con el resultavo de la evolucion
	 */
	public List<String> retrieveEvo(){
		return evolution;
	}
	
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
		
		log.info("Calculando las medidas de dispersion");
		calculateMeasures(crono);
		
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
		
		int i,promTD=0,lngth;
		
		lngth = crono.size()-1;
		//Se hace respectivo al numero de archivos que hay en el ultimo reporte
		
		//Comienza promedio
		for(i=1; i<crono.get(lngth).size();i++) {
			
			String[] analysisPC = crono.get(lngth).get(i);//Guarda el analisis por clase en un arreglo independiente
			
			//Comienzan las sumas
			promTD += Integer.parseInt(analysisPC[10]);
		}
		
		//Saca promedio
		promTD = promTD/crono.get(lngth).size();
		
		//Comienza eliminacion
		for(i=1; i<405/*crono.get(lnght).size()*/;i++) {
			/*
			 * Eliminar sigueinte linea y modificar siguiente linea cuanod soporte agregar archivos
			 */
			if(i==crono.get(lngth).size()) {
				break;
			}
			
			String[] analysisPC = crono.get(lngth).get(i);//Guarda el analisis por clase en un arreglo independiente
			
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
	
	/*
	 * Genera la Varianza, Pendente, Desviacion Estandar, Media, Coeficiente de varianza para cada grafica
	 */
	public void calculateMeasures(List<List<String[]>> crono){
		log.info("Calculando Varianza, Pendinte, Desviacion Estandar, Media y Coeficiente de varianza para cada grafica");
		
		int i,lngth, numMuestra;
		
		lngth = crono.size()-1;
		numMuestra = 400;
			
			for(i=1; i<numMuestra;i++){
				
				float promTD=0, varianza=0, pendiente, y1=0, y2=0, min, penUlt = 0;
				
				String[] analysisAux = crono.get(0).get(i);
				
				min = Integer.parseInt(analysisAux[10]);
				
				//Comienzan calculos
				for(int j=0; j<crono.size();j++) {
					
					String[] analysisPC = crono.get(j).get(i);//Guarda el analisis por clase en un arreglo independiente
					
					//Comienzan las sumas
					promTD += Integer.parseInt(analysisPC[10]);
					
					//Guardamos llas coordenadas del primer y ultimo punto para despues calcular la pendiente
					if(j==0) {
						y1 = Integer.parseInt(analysisPC[10]);
					}
					if(j==lngth) {
						y2 = Integer.parseInt(analysisPC[10]);
					}
					if(j == lngth-1) {
						penUlt = Integer.parseInt(analysisPC[10]);
					}
					//Comparamos para guardar el minimo
					if(min > Integer.parseInt(analysisPC[10])) {
						min = Integer.parseInt(analysisPC[10]);
					}
				}
				
				//Saca promedio
				promTD = promTD/crono.size();
				
				//Varianza
				for(int j=0; j<crono.size();j++) {
					
					String[] analysisPC = crono.get(j).get(i);//Guarda el analisis por clase en un arreglo independiente
					
					//Comienzan las sumas
					varianza = (float) (varianza + Math.pow(2,(Integer.parseInt(analysisPC[10])-promTD)));
				}
				
				varianza = varianza/lngth;
				
				//Pendiente
				pendiente = (y2-y1)/lngth;
				
				//Arbol de decision
				if(pendiente == 0 || (pendiente < 1 && pendiente > -1)) {
					if(varianza < 200) {
						//Estable
						evolution.add("Stable");
					}else if(pendiente == 0) {
						//Comparamos el punto minimo con el punto inicial
						if(y1 > min) {
							if(penUlt <= y2) {
								//Unsucces Refactor
								evolution.add("Unsucces refactor 1");
							}else {
								//Unsucces Refactor
								evolution.add("Unsucces refactor 1 but improving");
							}
						}else {
							//Succes refactor
							evolution.add("Succes refactor 1 but without improvements");
						}
					}else if(pendiente < 0){
						if(penUlt <= y2) {
							//Unsucces Refactor
							evolution.add("Unsucces refactor 2");
						}else {
							//Unsucces Refactor
							evolution.add("Unsucces refactor 2 but improving");
						}
					}else {
						if(penUlt < y2) {
							//Succes refactor
							evolution.add("Succes refactor 2 but degrading");
						}else if(penUlt > y2) {
							//Succes refactor
							evolution.add("Succes refactor 2 and improving");
						}else {
							//Succes refactor
							evolution.add("Succes refactor 2 but without improvements");
						}
					}
				}else if(pendiente > 20) {
					if(varianza >5500) {
						if(penUlt <= y2) {
							//Unsucces Refactor
							evolution.add("Unsucces refactor 3");
						}else {
							//Unsucces Refactor
							evolution.add("Unsucces refactor 3 but improving");
						}
					}else {
						//High degrading
						evolution.add("High degrading");
					}
				}else if(pendiente > 0) {
					//Degrading
					evolution.add("Degrading");	
				}else if(pendiente < 20){
					if(varianza > 5500) {
						if(penUlt < y2) {
							//Succes refactor
							evolution.add("Succes refactor 3 but degrading");
						}else if(penUlt > y2) {
							//Succes refactor
							evolution.add("Succes refactor 3 and improving");
						}else {
							//Succes refactor
							evolution.add("Succes refactor 3 but without improvements");
						}
					}else {
						//High Improving
						evolution.add("High improving");
					}
				}else {
					//Improving
					evolution.add("Improving");
				}
			}
	}
}