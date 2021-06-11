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
import mx.uam.izt.archinautInterface.mongodb.model.Files;
import mx.uam.izt.archinautInterface.mongodb.model.fileCronoResults;

@Service
@Slf4j
public class ReportService {
	
	@Autowired
	MongoDBRepository mongoRepository;
	
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
			HashMap<Integer, String> toolMap = aux.getToolMap();
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
			HashMap<Integer, String> toolMap = aux.getToolMap();
			
			List<Files> files = aux.getFiles();
			Iterator<Files> itFiles = files.iterator();
			
			Integer key = -1;//If the tool was not used on the report will keep the -1 value
			
			for (Entry<Integer, String> entry : toolMap.entrySet()) {//Look for the key into the tool map from the report at the moment
				if(toolName.equals(entry.getValue())) {
			    	key = entry.getKey();
			    	break;
			    } 
			}
			
			
			if(key != -1) {//If tool was used then gather the data, if toll was not used then just skips the report
				while(itFiles.hasNext()) {//for each file saves the name and the result
					Files auxFile = itFiles.next();
					
					HashMap<Integer, Double> toolResult = auxFile.getToolResult();
					
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