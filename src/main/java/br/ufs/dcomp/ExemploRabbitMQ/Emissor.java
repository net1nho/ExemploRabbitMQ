package br.ufs.dcomp.ExemploRabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Emissor {

  private final static String QUEUE_NAME = "A";
  private final static String QUEUE_NAME2 = "B";
  private final static String QUEUE_NAME3 = "C";
  private final static String EXCHANGE_NAME = "logs";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("54.90.228.141"); // Alterar
    factory.setUsername("admin"); // Alterar
    factory.setPassword("password"); // Alterar
    factory.setVirtualHost("/");    
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

                      //(queue-name, durable, exclusive, auto-delete, params); 
    channel.queueDeclare(QUEUE_NAME, false,   false,     false,       null);
    channel.queueDeclare(QUEUE_NAME2, false,   false,     false,       null);
    channel.queueDeclare(QUEUE_NAME3, false,   false,     false,       null);
    
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
    channel.queueBind(QUEUE_NAME2, EXCHANGE_NAME, "");
    channel.queueBind(QUEUE_NAME3, EXCHANGE_NAME, "");

    String message = "Vasco";
    
                    //  (exchange, routingKey, props, message-body             ); 
    channel.basicPublish("",       QUEUE_NAME, null,  message.getBytes("UTF-8")); //Em vez de vazio no exchange, Artur colocou EXCHANGE_NAME
    System.out.println(" [x] Mensagem enviada: '" + message + "'");

    channel.close();
    connection.close();
  }
}