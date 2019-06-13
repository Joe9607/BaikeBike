package cn.joe9607.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import cn.joe9607.pojo.Bike;

@Service
public class BikeServiceImpl implements BikeService {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void save(Bike bike) {
		//		mongoTemplate.insert(bike, "bikes");
		//调用具体的业务
		mongoTemplate.insert(bike);    //Bike类中注解保存了映射关系
	}

}
