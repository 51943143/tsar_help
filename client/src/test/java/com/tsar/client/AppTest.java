package com.tsar.client;

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
	private ReadFileNLine r;
	
	@Test
	public void testRead() {
		 List<ServerMetric> list= r.getLastNLine();
		for(ServerMetric s:list) {
			System.out.println(Json.toJson(s));
		}
		
	}
	
 
}
