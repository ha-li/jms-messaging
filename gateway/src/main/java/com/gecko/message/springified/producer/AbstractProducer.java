package com.gecko.message.springified.producer;

import javax.jms.Destination;

/**
 * Created by hlieu on 03/28/17.
 */
public abstract class AbstractProducer implements Producer{
   private Destination destination;
   private String applicationId;

   public AbstractProducer (Destination pDestination, String app) {
      this.destination = pDestination;
      this.applicationId = app;
   }

   public Destination getDestination () {
      return destination;
   }

   public void setDestination (Destination destination) {
      this.destination = destination;
   }

   public String getApplicationId () {
      return applicationId;
   }

   public void setApplicationId (String applicationId) {
      this.applicationId = applicationId;
   }

   public abstract void send();
}
