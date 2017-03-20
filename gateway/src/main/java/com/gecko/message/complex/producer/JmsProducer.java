package com.gecko.message.complex.producer;

import javax.jms.JMSException;

/**
 * Created by hlieu on 03/1/17.
 */
public interface JmsProducer {
   void sendMessage(String destinationName, String message) throws JMSException;
}
