package com.gecko.gateway;

import com.gecko.gateway.repository.ProducerRepository;
import org.apache.activemq.util.StopWatch;

/**
 *
 * A simple main that can be used to compare the performance of
 * ConnectionFactory vs PooledConnectionFactory.
 *
 * A trial run for regular connection pool: 6000 ms vs 1500 ms for PooledConnection.
 * To switch between pooled vs regular, change the connectionKey in ProducerRepository
 * Created by hlieu on 03/3/17.
 */
public class JmsSender {

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

      Gateway.getInstance ().start ();

      for (int i = 0; i < times.length; i++) {

         timer.restart ();
         for (int j = 0; j < 10; j++) {
            System.out.println ("Sending a message with transaction id");
            Gateway.getInstance ().findProducer ("default_producer").send ("A test message " + i);
         }
         timer.stop();
         times[i] = timer.taken ();

      }

      average(times);
   }
}
