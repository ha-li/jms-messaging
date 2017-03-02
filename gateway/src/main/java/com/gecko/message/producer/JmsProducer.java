package com.gecko.message.producer;

import javax.jms.JMSException;

/**
 * Created by hlieu on 03/1/17.
 */
public interface JmsProducer extends Producer {
   public void sendMessage(String destinationName, String message) throws JMSException;
}
