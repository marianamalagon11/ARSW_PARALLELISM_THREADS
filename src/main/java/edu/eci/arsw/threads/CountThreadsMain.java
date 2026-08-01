
package edu.eci.arsw.threads;

/**
 *
 * @author Paula Lozano
 * @author Mariana Malagón
 */

public class CountThreadsMain {

    public static void main(String args[]){
        int start = 0;
        int end = 299;

        // First part
        executeThreads(start, end);
    }

    private static void executeThreads(int start, int end) {
        int intervalSize = (end - start + 1) / 3;

        int currentStart = start;
        
        for (int i = 0; i < 3; i++) {

            int currentEnd;
            if (i == 2) {
                currentEnd = end;
            } else {
                currentEnd = currentStart + intervalSize - 1;
            }

            Thread thread = new Thread(
                new CountThread(currentStart, currentEnd), // Task
                "Hilo " + (i+1) // Thread name
            );
            thread.start();

            currentStart = currentEnd + 1;
        }
    }

}
