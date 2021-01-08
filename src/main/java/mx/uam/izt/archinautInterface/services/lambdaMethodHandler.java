/**
 * 
 */
package mx.uam.izt.archinautInterface.services;

import com.amazonaws.Request;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import mx.uam.izt.archinautInterface.model.LambdaRequest;
import mx.uam.izt.archinautInterface.model.LambdaResponse;

public class lambdaMethodHandler implements RequestHandler<LambdaRequest, LambdaResponse> {
    
    private DynamoDB dynamoDb;
    private String DYNAMODB_TABLE_NAME = "DataAnalysis";
    private Regions REGION = Regions.US_EAST_1;

    public LambdaResponse handleRequest(
    		Request personRequest, Context context) {
 
        this.initDynamoDbClient();

        persistData(personRequest);

        LambdaResponse personResponse = new LambdaResponse();
        personResponse.setMessage("Saved Successfully!!!");
        return personResponse;
    }

    private PutItemOutcome persistData(LambdaRequest personRequest) 
      throws ConditionalCheckFailedException {
        return this.dynamoDb.getTable(DYNAMODB_TABLE_NAME)
          .putItem(
            new PutItemSpec().withItem(new Item()
              .withString("firstName", personRequest.getFirstName())
              .withString("lastName", personRequest.getLastName())));
    }

    private void initDynamoDbClient() {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(REGION));
        this.dynamoDb = new DynamoDB(client);
    }

	@Override
	public LambdaResponse handleRequest(LambdaRequest input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}
}

