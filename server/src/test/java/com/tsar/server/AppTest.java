package com.tsar.server;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class AppTest {
	
	@Autowired
	private ServerMetricLoadService s;
	
	@Test
	public void testloadAndSave() {
//		 List<ServerMetric> list= s.loadAndSave("http://192.168.4.109:7001/tsar-client/getTsarDatas");
//		 List<ServerMetric> list= s.loadAndSave("http://192.168.4.108:7001/tsar-client/getTsarDatas");
		 List<ServerMetric> list= s.loadAndSave();
			System.out.println("................................size:"+String.valueOf(list.size()));
		for(ServerMetric s:list) {
			System.out.println(Json.toJson(s));
		}
		
	}
	
	@Test
	public void testgetDestIp() {
		List<String> ip=this.s.getDestIp();
		System.out.println(Json.toJson(ip));
	}
 
}
