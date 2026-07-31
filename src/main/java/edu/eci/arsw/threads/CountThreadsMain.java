
package edu.eci.arsw.threads;

/**
 *
 * @author Paula Lozano
 * @author Mariana Malagón
 */

public class CountThreadsMain {
    
    public static void main(String args[]) {
        int start = 0;
        int end = 300;

        private divide(start, end) {
            double interval = (end - start) mod 3;
            double t1_end = start + interval;
            double t2_start = t1_end + 1;
            double t2_end = t1_end + interval;
            double t3_start = t2_end + 1; 
        }

        Thread thread1 = new Thread(new CountThread(new int[]{start, t1_end}));
        Thread thread2 = new Thread(new CountThread(new int[]{t2_start, t2_end}));
        Thread thread3 = new Thread(new CountThread(new int[]{t3_start,end}));

        thread1.run();
        thread2.run();
        thread3.run();
    }

}
