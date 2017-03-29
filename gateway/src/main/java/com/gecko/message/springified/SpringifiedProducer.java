package com.gecko.message.springified;

import com.gecko.message.springified.repository.ProducerRepository;

/**
 * Created by hlieu on 03/3/17.
 */
public class SpringifiedProducer {

   public static void main (String[] args) throws Exception {

      for (int i = 0; i < 10; i++) {
         System.out.println ("Sending a message with transaction id");
         ProducerRepository.findProducer ("default_producer").send("A test message " + i);
      }
   }
}
