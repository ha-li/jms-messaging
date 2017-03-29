package com.gecko.message.springified.producer;

import javax.jms.Destination;

/**
 * Created by hlieu on 03/28/17.
 */
public class DefaultProducer extends AbstractProducer {

   public DefaultProducer (Destination pDestination, String id) {
      super(pDestination, id);
   }

   @Override
   public void send () {
      System.out.println ("Sending message");
   }

}
