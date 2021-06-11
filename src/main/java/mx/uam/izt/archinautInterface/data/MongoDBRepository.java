package mx.uam.izt.archinautInterface.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import mx.uam.izt.archinautInterface.mongodb.model.AnalysisResult;

@Repository
public interface MongoDBRepository extends MongoRepository<AnalysisResult,String>{
}
