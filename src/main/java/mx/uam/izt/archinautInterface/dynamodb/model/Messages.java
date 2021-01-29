package mx.uam.izt.archinautInterface.dynamodb.model;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

import lombok.Data;

@Data
public class Messages implements Serializable{
	public static final long serialVersionUID = 165165486816897L;
	
	@DynamoDBHashKey(attributeName = "id")
	private String id;
	
	@DynamoDBAttribute(attributeName = "idCommit")
	private String idCommit;
	
	@DynamoDBAttribute(attributeName = "analisys")
	private String analysis;
	
	@DynamoDBAttribute(attributeName = "date")
	private String date;
}
