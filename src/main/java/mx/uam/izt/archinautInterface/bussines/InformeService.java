package mx.uam.izt.archinautInterface.bussines;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.izt.archinautInterface.data.InformeRepository;
import mx.uam.izt.archinautInterface.model.Data;
import mx.uam.izt.archinautInterface.model.Informe;

@Service
@Slf4j
public class InformeService {
	
	@Autowired
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
	public Iterable <Data> retriveDpnds(){
		log.info("Regresando arreglo con datos");
		Data dato;
		Iterable <Data> lista = null;
		return lista;
	}
	
	/**
	 * 
	 * @return Lista de los Informes para Scc
	 */
	public Iterable <Data> retriveScc(){
		Data dato;
		Iterable <Data> lista = null;
		return lista;
	}
}
