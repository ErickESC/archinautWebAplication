package mx.uam.izt.archinautInterface.bussines;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import mx.uam.izt.archinautInterface.data.DynamoDBRepository;

@Service
@Slf4j
public class DynamoDBService {
	
	@Autowired
	DynamoDBRepository dbRepo;
	
	

}
