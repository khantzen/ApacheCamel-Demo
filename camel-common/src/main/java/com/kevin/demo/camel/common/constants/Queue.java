package com.kevin.demo.camel.common.constants;

import com.kevin.demo.camel.common.utils.ConfigManager;

import static com.kevin.demo.camel.common.utils.ConfigManager.RABBIT_MQ_PASSWORD;
import static com.kevin.demo.camel.common.utils.ConfigManager.RABBIT_MQ_SERVER;
import static com.kevin.demo.camel.common.utils.ConfigManager.RABBIT_MQ_USER;

public enum Queue {
    //Queue logic :
    // -You send to an exchange, giving a routing key.
    // -You receive on a queue.
    SimpleFetch("simple_fetch", "simple_fetch", "simple_fetch"),
    Audit("audit", "audit", "audit");

    private String exchange;
    private String routingKey;
    private String queueName;

    Queue(String exchange, String routingKey, String queueName) {
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.queueName = queueName;
    }

    public String getExchange() {
        return exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public String getQueueName() {
        return queueName;
    }

    //Our toString method return the rabbit mq endpoint for apache camel,
    //a rabbitMq endpoint configured as below
    //works as producer and listener
    @Override
    public String toString() {
        ConfigManager config = ConfigManager.getInstance();

        //More info here : http://camel.apache.org/rabbitmq.html
        String pattern = "rabbitmq://%s/%s?" + //Server and exchange we're working on
                "routingKey=%s" + //Routing key so the exchange can redirect to the correct queue
                "&queue=%s"+ //Queue to listen in case we are listening
                "&username=%s" +
                "&password=%s"+
                "&bridgeEndpoint=true" + //Camel ignore some rabbitmq headers preventing to do some weird stuff that I can't explain
                /*
                * The two next are there to prevent camel to make some "exchange/queue management"
                */
                "&autoDelete=false" + //Do not delete the exchange when terminated
                "&declare=false"; //Do not declare an exchange if it does not exist

        String server = config.getPropertyAsString(RABBIT_MQ_SERVER);
        String user = config.getPropertyAsString(RABBIT_MQ_USER);
        String password = config.getPropertyAsString(RABBIT_MQ_PASSWORD);

        return String.format(pattern, server, exchange, routingKey, queueName, user, password);
    }
}
