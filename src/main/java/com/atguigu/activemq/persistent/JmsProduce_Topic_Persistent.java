package com.atguigu.activemq.persistent;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce_Topic_Persistent {

    public static final String BROKER_URL = "tcp://192.168.1.204:61616";
    // public static final String TOPIC_NAME = "topic.persistent";
    public static final String TOPIC_NAME = "topic.persistent.jdbc";


    public static void main(String[] args) throws JMSException {
        // 1.创建连接工厂，按照指定的url地址，采用默认的用户名和密码
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);

        // 2.通过连接工厂，获取连接并启动访问
        Connection connection = connectionFactory.createConnection();

        // 3.创建会话
        // 两个参数：第一个叫事务、第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4.创建目的地（具体是队列还是主题）
        Topic topic = session.createTopic(TOPIC_NAME);

        // 5.创建消息的生产者
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        connection.start();

        // 6.通过使用消息生产者，发送3条消息到MQ的队列中
        for (int i = 1; i <= 3; i++) {
            // 7.创建消息
            // TextMessage message = session.createTextMessage("topic-persistent-msg-" + i);
            TextMessage message = session.createTextMessage("topic-persistent-jdbc-msg-" + i);

            // 8.通过消息生产者，发送给MQ
            producer.send(message);
        }

        // 9.关闭资源
        producer.close();
        session.close();
        connection.close();
        System.out.println("消息发送完毕！");
    }
}
