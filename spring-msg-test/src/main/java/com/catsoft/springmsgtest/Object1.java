package com.catsoft.springmsgtest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


/**
 * 
 * @author HellCat
 * Created 03/05/2019
 *
 */

public class Object1 {

	private static final String QUEUE_NAME1 = GlobalConstants.MSG_QUEUE1;
	private static final String QUEUE_NAME2 = GlobalConstants.MSG_QUEUE2;
	private static final String HOSTNAME = GlobalConstants.HOSTNAME;
	
	ConnectionFactory factory;
	Connection connection;
	Channel channel;
	DeliverCallback deliverCallback;
	
	public Object1() {
		try {
			factory = new ConnectionFactory();
			factory.setHost(HOSTNAME);
			factory.setPort(5672);
			factory.setUsername("guest");
			factory.setPassword("guest");
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME1, false, false, false, null);
	        deliverCallback = (consumerTag, delivery) -> {
	            String message = new String(delivery.getBody(), "UTF-8");
	            System.out.println("Object1: [x] Received '" + message + "'");
	        };
	        channel.basicConsume(QUEUE_NAME1, true, deliverCallback, consumerTag -> { });
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	public void send(String msg) {
		try {
			channel.basicPublish("", QUEUE_NAME2, null, msg.getBytes("UTF-8"));
	        System.out.println("Object1: [x] Sent '" + msg + "'");		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
	        System.out.println("Object1: " + e.getMessage());		
		} catch (IOException e) {
			// TODO Auto-generated catch block
	        System.out.println("Object1: " + e.getMessage());		
		}
	}
	
}
