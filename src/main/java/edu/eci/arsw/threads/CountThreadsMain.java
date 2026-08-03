
package edu.eci.arsw.threads;
import java.util.Scanner;

/**
 *
 * @author Paula Lozano
 * @author Mariana Malagón
 */

public class CountThreadsMain {

    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingresa el valor de inicio: ");
        int start = scanner.nextInt();

        System.out.print("Ingresa el valor final: ");
        int end = scanner.nextInt();

        scanner.close();

        if (start > end) {
            System.out.println("El valor de inicio no puede ser mayor al valor final");
            return;
        }

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
            thread.run();

            currentStart = currentEnd + 1;
        }
    }

}
