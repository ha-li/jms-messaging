package com.gecko.gateway.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by hlieu on 03/27/17.
 */
public class MessageService {

   public static Message createMessage (Session session, String transactionId, String message)
           throws JMSException {

      TextMessage txt = session.createTextMessage(message);

      txt.setStringProperty("TransactionId", transactionId);
      return txt;
   }
}
