package com.catsoft.springmsgtest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

@Controller
public class SpringMsgTestController {
	
	Object1 object1;
	Object2 object2;
	
	@PostConstruct
	void init() {
		object1 = new Object1();
		object2 = new Object2();
	}
	
	@RequestMapping("/sendFromObject1")
	public @ResponseBody String sendFromObject1(@RequestParam Map<String, String> msgParam) {
	String retAnswer = "SUCCESS";
	
		if(object1!=null ) {
			String msg = msgParam.get("msg");
			try {
				object1.send(msg);
			} catch(Exception ex ) {
				ex.printStackTrace();
				retAnswer = "ERROR";
			}
		}
		else {
			retAnswer = "ERROR";
		}
		return retAnswer;
	}
	@RequestMapping("/sendFromObject2")
	public @ResponseBody String sendFromObject2(@RequestParam Map<String, String> msgParam) {
	String retAnswer = "SUCCESS";
	
		if(object2!=null ) {
			String msg = msgParam.get("msg");
			try {
				object2.send(msg);
			} catch(Exception ex ) {
				ex.printStackTrace();
				retAnswer = "ERROR";
			}
		}
		else {
			retAnswer = "ERROR";
		}
		return retAnswer;
	}
	
}
