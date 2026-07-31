
package edu.eci.arsw.threads;

/**
 *
 * @author Paula Lozano
 * @author Mariana Malagón
 */

public class CountThreadsMain {
    
    public static void main(String args[]){
        Thread thread1 = new Thread(new CountThread(new int[]{0,99}));
        Thread thread2 = new Thread(new CountThread(new int[]{99,199}));
        Thread thread3 = new Thread(new CountThread(new int[]{200,299}));

        thread1.run();
        thread2.run();
        thread3.run();


    }

}
