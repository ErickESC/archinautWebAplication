package mx.uam.izt.archinautInterface.bussines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.izt.archinautInterface.data.MongoDBRepository;
import mx.uam.izt.archinautInterface.mongodb.model.AnalysisResult;
import mx.uam.izt.archinautInterface.mongodb.model.File;
import mx.uam.izt.archinautInterface.mongodb.model.fileCronoResults;

@Service
@Slf4j
public class ReportService {
	
	@Autowired
	MongoDBRepository mongoRepository;
	
	//To save if the file is decreasing or improving
	private List<String> evolution = new ArrayList<>();
	
	private List <AnalysisResult> reports;
	
	/**
	 * 
	 * @return tool used list
	 */
	public List <String> retriveToolList(){
		List<String> toolList = new LinkedList<>();
		
		this.reports = mongoRepository.findAll();
		
		Iterator<AnalysisResult> it = reports.iterator();
		
		while(it.hasNext()) {
			AnalysisResult aux = it.next();
			HashMap<Integer, String> toolMap = aux.getMetricsMap();
			for(String tool : toolMap.values()) {
				if(!(toolList.contains(tool))) {
					toolList.add(tool);
				}
			}
		}
		
		return toolList;
	}
	
	/**
	 * 
	 * @return resports matriz that matches with the name
	 */
	public List <fileCronoResults> retriveToolAnalysis(String toolName){
		
		List <fileCronoResults> cronoResults = new ArrayList<fileCronoResults>();//Matriz that is going to be retrieved
		
		Iterator<AnalysisResult> it = this.reports.iterator();
		
		List<String> fileNames = new ArrayList<>();
		
		//For each report gather the results per file
		while(it.hasNext()) {
			AnalysisResult aux = it.next();
			HashMap<Integer, String> toolMap = aux.getMetricsMap();
			
			List<File> file = aux.getFiles();
			Iterator<File> itFiles = file.iterator();
			
			Integer key = -1;//If the tool was not used on the report will keep the -1 value
			
			for (Entry<Integer, String> entry : toolMap.entrySet()) {//Look for the key into the tool map from the report at the moment
				if(toolName.equals(entry.getValue())) {
			    	key = entry.getKey();
			    	break;
			    } 
			}
			
			
			if(key != -1) {//If tool was used then gather the data, if toll was not used then just skips the report
				while(itFiles.hasNext()) {//for each file saves the name and the result
					File auxFile = itFiles.next();
					
					HashMap<Integer, Double> toolResult = auxFile.getMetricResult();
					
					Double result = toolResult.get(key);
					
					if(fileNames.contains(auxFile.getFileName())) {//If the name is on the names list then the object already exist
						
						for(int i=0; i<cronoResults.size(); i++) {
							fileCronoResults auxCrono = cronoResults.get(i);
							if((auxFile.getFileName()).equals(auxCrono.getFileName())) {
								log.info("Encontre File: "+auxCrono.getFileName());
								//Create and fill the object
								fileCronoResults newFileResult = new fileCronoResults();
								List<Double> lastResults = auxCrono.getResults();
								lastResults.add(result);
								
								newFileResult.setFileName(auxFile.getFileName());
								newFileResult.setResults(lastResults);
								
								cronoResults.set(i, newFileResult);//Saves the result on the real object
								log.info("Objeto Final: "+cronoResults.get(i));
								break;
							}
						}
					}else {//Then the object does not exist and must be created
						//Create and fill the object
						fileCronoResults newFile = new fileCronoResults();
						List<Double> newResults = new ArrayList<>();
						newResults.add(result);
						
						newFile.setFileName(auxFile.getFileName());
						newFile.setResults(newResults);
						//Adds the new file to the crono results list
						cronoResults.add(newFile);
						//Adds the file name to the file names list
						fileNames.add(auxFile.getFileName());
					}
				}
			}
		}
		
		return cronoResults;
	}
	
	/*
	 * Removes the file if its report has flat metrics on depends
	 */
	public List<List<String[]>> removeFlatDpnds(List<List<String[]>> crono){
		
		int i, Tfiles;
		
		Tfiles = 1557;
		
		for(i=1; i<Tfiles/*crono.get(0).size()*/;i++) {
		
			String[] analysisPC = crono.get(0).get(i);//Saves the analysis per class into an independent array
			int TDependencies, promTD=0;
			
			//Save the total dependencies at the beginning
			TDependencies = Integer.parseInt(analysisPC[10]);
			
			//Searches into the project to get the average
			for(int j=0; j<crono.size(); j++) {
				analysisPC = crono.get(j).get(i);
				promTD += Integer.parseInt(analysisPC[10]);
			}
			//Gets Average
			promTD = promTD/crono.size();
			
			//If average is equal to the first metric then it is a flat metric
			if(promTD == TDependencies) {
				//Earases flat metrics
				for(int k=0; k<crono.size(); k++) {
					crono.get(k).remove(i);
				}
				i--;
				Tfiles--;
			}
		}
		
		return crono;
	}
	
	/*
	 * Removes files which its average is lower than average
	 */
	public List<List<String[]>> removeMinums(List<List<String[]>> crono){
		log.info("procesando estadisticas arriba del promedio");
		
		int i,promTD=0,lngth;
		
		lngth = crono.size()-1;//Actualizar
		
		//Starts Average
		for(i=1; i<crono.get(lngth).size();i++) {
			
			String[] analysisPC = crono.get(lngth).get(i);//Saves the analysis into an array
			
			//Starts summation
			promTD += Integer.parseInt(analysisPC[10]);
		}
		
		//Gets Average
		promTD = promTD/crono.get(lngth).size();
		
		//starts Elimination
		for(i=1; i<405/*crono.get(lnght).size()*/;i++) {
			if(i==crono.get(lngth).size()) {
				break;
			}
			
			String[] analysisPC = crono.get(lngth).get(i);//Saves the analysis
			
			int TDependencies;
			
			TDependencies = Integer.parseInt(analysisPC[10]);
			
			//Removes if total is lower than average
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
					
					String[] analysisPC = crono.get(j).get(i);
					
					//CStarts summation
					varianza = (float) (varianza + Math.pow(2,(Integer.parseInt(analysisPC[10])-promTD)));
				}
				
				varianza = varianza/lngth;
				
				//Pendiente
				pendiente = (y2-y1)/lngth;
				
				//Arbol de decision
				if(pendiente == 0 || (pendiente < 1 && pendiente > -1)) {
					if(varianza < 200) {
						//Stable
						evolution.add("Stable");
					}else if(pendiente == 0) {
						//Compares first point with last point
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