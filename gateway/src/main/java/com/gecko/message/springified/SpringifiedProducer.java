package com.gecko.message.springified;

import com.gecko.message.springified.repository.ProducerRepository;
import org.apache.activemq.util.StopWatch;

import java.util.Timer;

/**
 *
 * A simple main that can be used to compare the performance of
 * ConnectionFactory vs PooledConnectionFactory.
 *
 * A trial run for regular connection pool: 6000 ms vs 1500 ms for PooledConnection.
 * To switch between pooled vs regular, change the connectionKey in ProducerRepository
 * Created by hlieu on 03/3/17.
 */
public class SpringifiedProducer {

   public static void average (long[] times) {
      long totalTime = 0;
      for(long time : times) {
         totalTime += time;
      }

      System.out.println ("total times: " + totalTime);
      System.out.println ("average times: " + totalTime/times.length);
   }

   public static void main (String[] args) throws Exception {
      long[] times = new long[10];

      StopWatch timer = new StopWatch ();

      for (int i = 0; i < times.length; i++) {

         timer.restart ();
         for (int j = 0; j < 10; j++) {
            System.out.println ("Sending a message with transaction id");
            ProducerRepository.findProducer ("default_producer").send ("A test message " + i);
         }
         timer.stop();
         times[i] = timer.taken ();

      }

      average(times);
   }
}
