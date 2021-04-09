package mx.uam.izt.archinautInterface.MongoDBConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

@Configuration
public class DynamodbConfiguration {

	private static final DynamoDBMapperConfig.TableNameResolver TABLE_NAME_RESOLVER = (className,config) -> "ArchAnalysis";
		ClientConfiguration getClientConfoguration() {
		
		ClientConfiguration cfg = new ClientConfiguration();
		cfg.setProtocol(Protocol.HTTP);
		cfg.setProxyPort(8099);
			
	return cfg;
	}
	
	@Bean(name="dynamoDBMapper")
	public DynamoDBMapper dynamoDBMapperLocal() {
		Regions region = Regions.US_EAST_1;
		DynamoDBMapperConfig dbMapperConfig = new DynamoDBMapperConfig.Builder().withTableNameResolver(TABLE_NAME_RESOLVER).build();
		AmazonDynamoDBClient dynamoClient = getAmazonDynamoDBLocalClient(region);
		
		return new DynamoDBMapper(dynamoClient,dbMapperConfig);
	}
	
	//This client is configured only with the local profile
	private AmazonDynamoDBClient getAmazonDynamoDBLocalClient(Regions region) {
		return (AmazonDynamoDBClient) AmazonDynamoDBClientBuilder.standard().withRegion(region).build();
	}
}
