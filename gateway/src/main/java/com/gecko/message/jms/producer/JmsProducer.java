package com.gecko.message.jms.producer;

import com.gecko.message.jms.SessionProvider;

import javax.jms.JMSException;

/**
 * Created by hlieu on 03/1/17.
 */
public interface JmsProducer {
   void sendMessage(String destinationName, String message) throws JMSException;
}
