package mx.uam.izt.archinautInterface.data;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import mx.uam.izt.archinautInterface.mongodb.model.AnalysisResult;

@Repository
public interface MongoDBRepository extends MongoRepository<AnalysisResult,String>{
	List<AnalysisResult> findByIdProject(String idProject, Sort sort);
}
