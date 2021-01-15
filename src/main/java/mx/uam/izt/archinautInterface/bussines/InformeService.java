package mx.uam.izt.archinautInterface.bussines;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.izt.archinautInterface.data.InformeRepository;
import mx.uam.izt.archinautInterface.model.Datas;
import mx.uam.izt.archinautInterface.model.Informe;

@Service
@Slf4j
public class InformeService {
	
	private InformeRepository informeRepository;
	
	/**
	 * 
	 * @param nuevoInforme
	 * @return El Informe recien creado, null de lo contrario
	 */
	public Informe create(Informe nuevoInforme) {
		log.info("Creando Informe"+nuevoInforme.getId());
		
		log.info("Creado el Infrome"+nuevoInforme.getId());
		return InformeRepository.save(nuevoInforme);

	}
	
	/**
	 * 
	 * @return Lista de los Informes
	 */
	public Iterable <Informe> retriveAll(){
		log.info("Regresando arreglo con informes");
		return informeRepository.findAll();
	}
	
	/**
	 * 
	 * @param Id
	 * @return Pokemon al que le pertenece la matricula
	 */
	public Informe retrive(String IdCommit){
		log.info("Llamado a regresar el informe "+IdCommit);
		
		Informe informeOpt = informeRepository.findByIdCommit(IdCommit);
		return informeOpt;
	}
	
	
	/**
	 * 
	 * @return Lista de los Informes para Depends
	 */
	public Iterable <Datas> retriveDpnds(){
		log.info("Regresando arreglo con datos");
		String report, aux="";
		Datas dato = new Datas();
		
		List <Datas> lista = new ArrayList<>();
		List <Datas> listaAux = new ArrayList<>();
		
		Iterable <Informe> informes = retriveAll();
		
		Iterator<Informe> it = informes.iterator();
		
		//Se guardan los datos por cada archivo del reporte en una lista
		while(it.hasNext()) {
			report = it.next().getData().toString();
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
	public Iterable <Datas> retriveScc(){
		log.info("Regresando arreglo con datos");
		String report, aux="";
		Datas dato = new Datas();
		
		List <Datas> lista = new ArrayList<>();
		List <Datas> listaAux = new ArrayList<>();
		
		Iterable <Informe> informes = retriveAll();
		
		Iterator<Informe> it = informes.iterator();
		
		//Se guardan los datos por cada archivo del reporte en una lista
		while(it.hasNext()) {
			report = it.next().getData().toString();
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
}
