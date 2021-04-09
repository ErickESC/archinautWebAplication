package mx.uam.izt.archinautInterface.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpServerErrorException;

import lombok.extern.slf4j.Slf4j;
import mx.uam.izt.archinautInterface.mongodb.model.Messages;

@Repository
public interface MongoDBRepository extends MongoRepository<Messages,Integer>{
	
	@Resource(name="dynamoDBMapper")
	DynamoDBMapper dbMapper;
	
	public List<Messages> getDetails(String idReports){
		log.info("Buscando en BD reportes asociados a "+idReports);
		List<Messages> listing = new ArrayList<>();
		QueryResultPage<Messages> itemList;
		
		// #Expresion Attributes
		Map<String,String> expressionAttributeNames = new HashMap<>();
		expressionAttributeNames.put("#id", "id");
		Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
		expressionAttributeValues.put(":id", new AttributeValue().withS(idReports));
		
		DynamoDBQueryExpression<Messages> retrieveQuery = new DynamoDBQueryExpression<Messages>()
		.withKeyConditionExpression("#id=:id")
		.withExpressionAttributeNames(expressionAttributeNames)
		.withExpressionAttributeValues(expressionAttributeValues)
		.withScanIndexForward(false);
		
		try {
			//DynamoDB Query
			itemList = dbMapper.queryPage(Messages.class, retrieveQuery);
			listing = itemList.getResults();
			log.info("Encontrados en BD reportes asociados a "+idReports);
		}catch(ResourceNotFoundException e) {
			throw e;
		}catch(HttpServerErrorException e) {
			throw e;
		}catch(Exception e) {
			throw e;
		}
		
		return listing;
	}
}
