package mx.uam.izt.archinautInterface.data;

import java.util.ArrayList;
import java.util.List;

import mx.uam.izt.archinautInterface.model.Informe;

public class InformeRepository {
	
	private static List <Informe> repository = new ArrayList<>();
	
	public Informe save(Informe nuevoInforme) {
		repository.add(nuevoInforme);
		return nuevoInforme;
	}
	
	public List<Informe> findAll(){
		return repository;
	}
	
	public Informe findByIdCommit(String idCommit) {
		
		Informe aux;
		
		for(int i=0; i<repository.size();i++) {
			aux = repository.get(i);
			if(aux.getIdCommit() == idCommit) {
				return aux;
			}
		}
		return null;
	}
	
}
