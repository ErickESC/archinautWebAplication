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
	@SuppressWarnings("rawtypes")
	public List <List> retriveDpnds(String id){
		log.info("Regresando arreglo con datos");
		String[] classe, analysisPC;
		
		List <List> crono = new ArrayList<List>();
		
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
		
		/*for(int i=0; i<report.size();i++) {
			

		}*/
		return crono;
	}
	
	/**
	 * 
	 * @return Lista de los Informes para Scc
	 */
	@SuppressWarnings("rawtypes")
	public List <List> retriveScc(String id){
		log.info("Regresando arreglo con datos");
		String[] classe, analysisPC;
		
		List <List> crono = new ArrayList<List>();
		
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
		
		/*for(int i=0; i<report.size();i++) {
			

		}*/
		return crono;
	}
	
	/*
	 * Remueve el archivo cuyo reporte tenga los 3 depends planos 
	 */
	@SuppressWarnings("rawtypes")
	public List<List> removeFlatDpnds(){
		List <List> crono = new ArrayList<List>();
		return crono;
	}
	
	/*
	 * Remueve el archivo cuyo reporte tenga los 3 Css planos
	 */
	@SuppressWarnings("rawtypes")
	public List<List> removeFlatSCC(){
		List <List> crono = new ArrayList<List>();
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