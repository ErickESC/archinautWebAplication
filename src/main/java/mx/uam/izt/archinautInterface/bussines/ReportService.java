package mx.uam.izt.archinautInterface.bussines;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.izt.archinautInterface.data.MongoDBRepository;
import mx.uam.izt.archinautInterface.mongodb.model.AnalysisResult;
import mx.uam.izt.archinautInterface.mongodb.model.File;
import mx.uam.izt.archinautInterface.mongodb.model.FileCronoResults;

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
	public List <FileCronoResults> retriveToolAnalysis(String toolName){
		
		List <FileCronoResults> cronoResults = new ArrayList<FileCronoResults>();//Matriz that is going to be retrieved
		
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
							FileCronoResults auxCrono = cronoResults.get(i);
							if((auxFile.getFileName()).equals(auxCrono.getFileName())) {
								log.info("Encontre File: "+auxCrono.getFileName());
								//Create and fill the object
								FileCronoResults newFileResult = new FileCronoResults();
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
						FileCronoResults newFile = new FileCronoResults();
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
		
		cronoResults = removeFlatMetrics(cronoResults);
		cronoResults = calculateMeasures(cronoResults);
		
		return cronoResults;
	}
	
	/*
	 * Removes the file if its report has flat metrics on depends
	 */
	public List <FileCronoResults> removeFlatMetrics(List <FileCronoResults> cronoResults){
		
		Iterator<FileCronoResults> it = cronoResults.iterator();
		log.info(""+cronoResults);
		while(it.hasNext()) {
			FileCronoResults currentFile = it.next();
			List<Double> results = currentFile.getResults();
			
			int lgnth=results.size();;
			Double average=0.0, lastValue=0.0;
			
			//Gets the average
			for(int j=0; j<results.size(); j++) {
				if(results.get(j) != null) {
					average += results.get(j);
					lastValue = results.get(j);
				}else {
					lgnth -= 1;
				}
			}
			average = average/lgnth;
			
			//If average is equal to the first metric then it is a flat metric
			if(average == lastValue) {
				cronoResults.remove(currentFile);
			}
		}
		
		return cronoResults;
	}
	
	/*
	 * Genera la Varianza, Pendente, Desviacion Estandar, Media, Coeficiente de varianza para cada grafica
	 */
	public List <FileCronoResults> calculateMeasures(List <FileCronoResults> cronoResults){
		log.info("Calculando Varianza, Pendinte, Desviacion Estandar, Media y Coeficiente de varianza para cada grafica");
		
		for(int i=0; i<cronoResults.size(); i++) {
			FileCronoResults currentFile = cronoResults.get(i);
			List<Double> results = currentFile.getResults();
			
			Double average=0.0, variance=0.0, slope, y1=0.0, y2=0.0, min=0.0, bfrLast = 0.0;
			
			int lgnth=results.size(), lastIndex=0;
			
			//finds the first real value
			for(int j=0; j<results.size(); j++) {
				if(results.get(j) != null) {
					//Save as min the first element
					min = results.get(j);
					//Save the first and last element to calculate the slope
					y1 = results.get(j);
					
					break;
				}
			}
			
			//Saves the element before the last element
			bfrLast = results.get(results.size()-2);
			//Begins the algebra
			//Gets the average and the minus
			for(int j=0; j<results.size(); j++) {
				if(results.get(j) != null) {
					average += results.get(j);
					//compare to get minus
					if(min > results.get(j)) {
						min = results.get(j);
					}
					lastIndex = j;
				}else {
					lgnth -= 1;
				}
			}
			average = average/lgnth;//Average
			
			y2 = results.get(lastIndex-1);//Saves the last real value
			
			//Variance
			for(int j=0; j<results.size(); j++) {
				if(results.get(j) != null) {
					variance = (variance + Math.pow(2,(results.get(j)-average)));
				}
			}

			variance = variance/lgnth;
				
			//Slope
			slope = (y2-y1)/lgnth;
			
			//Decision three
			if(slope == 0 || (slope < 1 && slope > -1)) {
				if(variance < 200) {
					//Stable
					currentFile.setTendency("Stable");
					cronoResults.set(i, currentFile);
				}else if(slope == 0) {
					//Compares first point with last point
					if(y1 > min) {
						if(bfrLast <= y2) {
							//Unsucces Refactor
							currentFile.setTendency("Unsucces refactor 1");
							cronoResults.set(i, currentFile);
						}else {
							//Unsucces Refactor
							currentFile.setTendency("Unsucces refactor 1 but improving");
							cronoResults.set(i, currentFile);
						}
					}else {
						//Succes refactor
						currentFile.setTendency("Succes refactor 1 but without improvements");
						cronoResults.set(i, currentFile);
					}
				}else if(slope < 0){
					if(bfrLast <= y2) {
						//Unsucces Refactor
						currentFile.setTendency("Unsucces refactor 2");
						cronoResults.set(i, currentFile);
					}else {
						//Unsucces Refactor
						currentFile.setTendency("Unsucces refactor 2 but improving");
						cronoResults.set(i, currentFile);
					}
				}else {
					if(bfrLast < y2) {
						//Succes refactor
						currentFile.setTendency("Succes refactor 2 but degrading");
						cronoResults.set(i, currentFile);
					}else if(bfrLast > y2) {
						//Succes refactor
						currentFile.setTendency("Succes refactor 2 and improving");
						cronoResults.set(i, currentFile);
					}else {
						//Succes refactor
						currentFile.setTendency("Succes refactor 2 but without improvements");
						cronoResults.set(i, currentFile);
					}
				}
			}else if(slope > 20) {
				if(variance >5500) {
					if(bfrLast <= y2) {
						//Unsucces Refactor
						currentFile.setTendency("Unsucces refactor 3");
						cronoResults.set(i, currentFile);
					}else {
						//Unsucces Refactor
						currentFile.setTendency("Unsucces refactor 3 but improving");
						cronoResults.set(i, currentFile);
					}
				}else {
					//High degrading
					currentFile.setTendency("High degrading");
					cronoResults.set(i, currentFile);
				}
			}else if(slope > 0) {
				//Degrading
				currentFile.setTendency("Degrading");
				cronoResults.set(i, currentFile);
			}else if(slope < 20){
				if(variance > 5500) {
					if(bfrLast < y2) {
						//Succes refactor
						currentFile.setTendency("Succes refactor 3 but degrading");
						cronoResults.set(i, currentFile);
					}else if(bfrLast > y2) {
						//Succes refactor
						currentFile.setTendency("Succes refactor 3 and improving");
						cronoResults.set(i, currentFile);
					}else {
						//Succes refactor
						currentFile.setTendency("Succes refactor 3 but without improvements");
						cronoResults.set(i, currentFile);
					}
				}else {
					//High Improving
					currentFile.setTendency("High improving");
					cronoResults.set(i, currentFile);
				}
			}else {
				//Improving
				currentFile.setTendency("Improving");
				cronoResults.set(i, currentFile);
			}
		}
		return cronoResults;
	}
	
	/*
	 * Parses an ArchinautObject into an AnalysisResult object
	 */
	@SuppressWarnings("rawtypes")
	public AnalysisResult parseArchinautObject(JSONObject archResult) throws Exception{
		
		// getting idProject, idCommit and date
        String idProject = (String) archResult.get("id");
        String idCommit = (String) archResult.get("idCommit");
        
        String dateString = (String) archResult.get("date");
        Date date = stringToDate(dateString); 
        
        // getting files from the ArchinautReport
        ArrayList files = (ArrayList) archResult.get("files");
        
        //Create the metrics map for this report
        HashMap<Integer, String> newMetricsMap = createMetricsMap(files);
        
        
        //Create the files list for this report
        List<File> reportFiles = createFilesList(files, newMetricsMap);
        
        //Create the object that is going to be saved on the database
        AnalysisResult newAnalysisResult = new AnalysisResult();
        //Fill the object
        newAnalysisResult.setIdProject(idProject);
        newAnalysisResult.setIdCommit(idCommit);
        newAnalysisResult.setDate(date);
        newAnalysisResult.setMetricsMap(newMetricsMap);
        newAnalysisResult.setFiles(reportFiles);
        
        return newAnalysisResult;
	}
	
	/*
	 * Saves the new report into the database
	 */
	public AnalysisResult saveArchinautReport(JSONObject archResult) throws Exception{
		
		return parseArchinautObject(archResult);
	}
	
	/*
	 * Parses the archinaut String date to Date format of Java
	 */
	public Date stringToDate(String dateString) throws Exception{
		
		String dateAndTime[] = dateString.split("T");
        String dateNoTime = dateAndTime[0];
        String time[] = dateAndTime[1].split("-");
        String dateFormat = dateNoTime.concat(" ");
        dateFormat = dateFormat.concat(time[0]);
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateFormat);
        
        return date;
	}
	
	/*
	 * Creates a new metricsMap based on the ArchinautReport
	 */
	@SuppressWarnings("rawtypes")
	public HashMap<Integer, String> createMetricsMap(ArrayList files) {
		
		HashMap<Integer, String> newMetricsMap = new HashMap<Integer, String>();
		
        //First Iteration to fill the newMetricsMap using the first file(all the files has the same metrics)
        Iterator metricsIterator = ((Map) files.get(0)).entrySet().iterator();
    	
        Integer metricKey = 0;
        
        while (metricsIterator.hasNext()) {
            Map.Entry pair = (Entry) metricsIterator.next();
            if(pair.getKey() != "Filename") {
            	newMetricsMap.put(metricKey, (String) pair.getKey());
            	metricKey++;
            }
        }
        
        return newMetricsMap;
	}
	
	/*
	 * Creates the list of Files(our project class) based on the ArchinautReport
	 */
	@SuppressWarnings("rawtypes")
	public List<File> createFilesList(ArrayList files, HashMap<Integer, String> newMetricsMap) {
		
		List<File> reportFiles = new ArrayList<File>();
    	
        // iterating files
        for(int i=0; i<files.size(); i++) {
        	
        	Iterator itr1 = ((Map) files.get(i)).entrySet().iterator();
        	
        	//Creates an auxiliary file
        	File currentFile = new File();
        	//Creates an auxiliary map for file metrics result
        	HashMap<Integer, Double> currentFileMetricResult = new HashMap<Integer, Double>();
        	// iterating the results per file
            while (itr1.hasNext()) {
                Map.Entry pair = (Entry) itr1.next();
                if(pair.getKey() == "Filename") {
                	currentFile.setFileName((String) pair.getValue());
                }else {
                	for(int j = 0; j<newMetricsMap.size(); j++) {//Look for the metric key to match the metrics map and the metrics result map
            			if(pair.getKey() == newMetricsMap.get(j)) {
            				currentFileMetricResult.put(j, Double.parseDouble((String)pair.getValue()));
            			}
            		}
                } 
            }
            currentFile.setMetricResult(currentFileMetricResult);
            reportFiles.add(currentFile);
        }
        
        return reportFiles;
	}
	
}