package com.gecko.broker.security.authorization;

import org.apache.activemq.command.Message;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.CommandVisitor;

import javax.jms.JMSException;

/**
 * Created by hlieu on 03/19/17.
 */
public class MockMessage extends Message {

   @Override
   public Message copy () {
      return null;
   }

   @Override
   public void clearBody () throws JMSException {

   }

   @Override
   public void storeContent () {

   }

   @Override
   public void storeContentAndClear () {

   }

   @Override
   public Response visit (CommandVisitor visitor) throws Exception {
      return null;
   }

   @Override
   public byte getDataStructureType () {
      return 0;
   }
}
