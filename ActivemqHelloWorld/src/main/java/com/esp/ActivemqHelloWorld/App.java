package com.esp.ActivemqHelloWorld;




import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
	 private static final Logger logger = LoggerFactory.getLogger(App.class);
    public static void main( String[] args ) throws Exception
    {
    	 
    	(new Thread(new ActiveMQHelloWorldProducer())).start();
		(new Thread(new ActiveMQHelloWorldProducer())).start();
		(new Thread(new HelloWorldConsumer())).start();
		Thread.sleep(10000);
		(new Thread(new HelloWorldConsumer())).start();
		(new Thread(new ActiveMQHelloWorldProducer())).start();
		(new Thread(new ActiveMQHelloWorldProducer())).start();
		(new Thread(new HelloWorldConsumer())).start();
    }
    public static class ActiveMQHelloWorldProducer implements Runnable {
		public void run() {
			try {
				// Create ConnectionFactory
				ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://Ashwin-PC:61616");

				// Create Connection
				Connection connection = activeMQConnectionFactory.createConnection();
				connection.start();

				// Create Session
				Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

				// Create the destination (Topic or Queue)
				Destination destination = session.createQueue("MyTest");

				// Create MessageProducer from the Session to the Topic or
				// Queue
				MessageProducer producer = session.createProducer(destination);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

				// Create messages
				String text = " ActiveMQ Hello world! From: "+ Thread.currentThread().getName() + " : "+ this.hashCode();
				TextMessage message = session.createTextMessage(text);

				// Tell the producer to send the message
				System.out.println("Sent message: " + message.hashCode()+ " : " + Thread.currentThread().getName());
				 logger.info("Sent message: " + message.hashCode()+ " : " + Thread.currentThread().getName());
				 logger.debug("Sent message: " + message.hashCode()+ " : " + Thread.currentThread().getName());
					
				 producer.send(message);

				// Clean up
				session.close();
				connection.close();
			} catch (Exception e) {
				System.out.println("Caught Exception: " + e);
				 logger.info("Caught Exception: " + e);
				e.printStackTrace();
			}
		}
	}

	public static class HelloWorldConsumer implements Runnable,
			ExceptionListener {
		public void run() {
			try {

				// Create a ConnectionFactory
				ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://Ashwin-PC:61616");

				// Create a Connection
				Connection connection = activeMQConnectionFactory.createConnection();
				connection.start();

				connection.setExceptionListener(this);

				// Create a Session
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

				// Create the destination (Topic or Queue)
				Destination destination = session.createQueue("MyTest");

				// Create a MessageConsumer from the Session to the Topic or
				// Queue
				MessageConsumer consumer = session.createConsumer(destination);

				// Wait for a message
				Message message = consumer.receive(1000);

				if (message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					String text = textMessage.getText();
					System.out.println("Received: " + text);
					 logger.info("Received: " + text);
					 logger.debug("Received: " + text);
				} else {
					System.out.println("Received: " + message);
					 logger.info("Received: " + message);
					 logger.debug("Received: " + message);
				}

				consumer.close();
				session.close();
				connection.close();
			} catch (Exception e) {
				System.out.println("Caught exception: " + e);
				logger.info("Caught Exception: " + e);
				logger.debug("Caught Exception: " + e);
				e.printStackTrace();
			}
		}

		public synchronized void onException(JMSException ex) {
			System.out.println("ActiveMQ JMS Exception occured.  Shutting down client.");
			logger.info("Caught Exception: " + ex);
			logger.debug("Caught Exception: " + ex);
		}
	}
}
