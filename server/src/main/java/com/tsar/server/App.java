package com.tsar.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Hello world!
 *
 */

@Controller
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = { "com.tsar.server" })
@EnableWebSocket
public class App extends SpringBootServletInitializer implements WebSocketConfigurer {
	
	@Autowired
	private ServerMetricLoadService s;
	

	@RequestMapping("/")
	String home(ModelMap model) {
		model.addAttribute("iplist",s.getDestIp());
		return "tsar_metric";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(myWebSocketHandler(), "/metric").setAllowedOrigins("*").withSockJS();
	}
	@Bean
	public WebSocketHandler myWebSocketHandler() {
		return new MyWebSocketHandler();
	}
	
}