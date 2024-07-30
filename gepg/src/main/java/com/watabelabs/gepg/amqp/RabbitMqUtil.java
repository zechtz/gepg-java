package com.watabelabs.gepg.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.AMQP;
import com.watabelabs.gepg.utils.DotEnvUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RabbitMqUtil {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqUtil.class);

    private static final String RABBITMQ_HOST = DotEnvUtil.getEnvVariable("RABBITMQ_HOST");
    private static final int RABBITMQ_PORT = Integer.valueOf(DotEnvUtil.getEnvVariable("RABBITMQ_PORT"));
    private static final String RABBITMQ_USERNAME = DotEnvUtil.getEnvVariable("RABBITMQ_USERNAME");
    private static final String RABBITMQ_PASSWORD = DotEnvUtil.getEnvVariable("RABBITMQ_PASSWORD");

    private static Connection connection;
    private static Channel channel;

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

    public static void close() {
        try {
            if (channel != null)
                channel.close();
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            logger.error("FAILED_TO_CLOSE_RABBITMQ_CONNECTION", e);
        }
    }
}
