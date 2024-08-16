package com.watabelabs.gepg.amqp.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.AMQP;
import com.watabelabs.gepg.utils.DotEnvUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Utility class for interacting with RabbitMQ, providing methods for
 * publishing messages to a queue and managing connections.
 */
public class GepgAMqPublisher {
    private static final Logger logger = LoggerFactory.getLogger(GepgAMqPublisher.class);

    private static final String RABBITMQ_HOST = DotEnvUtil.getEnvVariable("RABBITMQ_HOST");
    private static final int RABBITMQ_PORT = Integer.valueOf(DotEnvUtil.getEnvVariable("RABBITMQ_PORT"));
    private static final String RABBITMQ_USERNAME = DotEnvUtil.getEnvVariable("RABBITMQ_USERNAME");
    private static final String RABBITMQ_PASSWORD = DotEnvUtil.getEnvVariable("RABBITMQ_PASSWORD");

    private static Connection connection;
    private static Channel channel;

    // Static block to initialize the RabbitMQ connection and channel
    static {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(RABBITMQ_HOST);
            factory.setPort(RABBITMQ_PORT);
            factory.setUsername(RABBITMQ_USERNAME);
            factory.setPassword(RABBITMQ_PASSWORD);

            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (Exception e) {
            logger.error("FAILED_TO_ESTABLISH_RABBITMQ_CONNECTION", e);
        }
    }

    /**
     * Publishes a message to a specified RabbitMQ queue with headers.
     *
     * @param queueName the name of the queue to publish to
     * @param message   the message to publish
     * @param headers   the headers to include with the message
     */
    public static void publishToQueue(String queueName, String message, Map<String, Object> headers) {
        try {
            channel.queueDeclare(queueName, true, false, false, null);
            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                    .headers(headers)
                    .build();
            channel.basicPublish("", queueName, props, message.getBytes("UTF-8"));
            logger.info("MESSAGE_PUBLISHED_TO_QUEUE {}: {}", queueName, message);
        } catch (Exception e) {
            logger.error("FAILED_TO_PUBLISH_MESSAGE_TO_QUEUE", e);
        }
    }

    /**
     * Publishes a message to a specified RabbitMQ exchange with headers.
     *
     * @param exchangeName the name of the exchange to publish to
     * @param routingKey   the routing key to use
     * @param message      the message to publish
     * @param headers      the headers to include with the message
     */
    public static void publishToExchange(String exchangeName, String routingKey, String message,
            Map<String, Object> headers) {
        try {
            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                    .headers(headers)
                    .build();
            channel.basicPublish(exchangeName, routingKey, props, message.getBytes("UTF-8"));
            logger.info("MESSAGE_PUBLISHED_TO_EXCHANGE {}: {}", exchangeName, message);
        } catch (Exception e) {
            logger.error("FAILED_TO_PUBLISH_MESSAGE_TO_EXCHANGE", e);
        }
    }

    /**
     * Closes the RabbitMQ channel and connection.
     */
    public static void close() {
        try {
            if (channel != null) {
                channel.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            logger.error("FAILED_TO_CLOSE_RABBITMQ_CONNECTION", e);
        }
    }

}
