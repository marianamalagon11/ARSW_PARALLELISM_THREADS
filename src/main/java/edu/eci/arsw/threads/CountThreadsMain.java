
package edu.eci.arsw.threads;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Paula Lozano
 * @author Mariana Malagón
 */

public class CountThreadsMain {

    private static List<Integer> divide(int start, int end) {
        int n = (end - start) / 3;
        int t1_end = start + n;
        int t2_start = t1_end + 1;
        int t2_end = t1_end + n;
        int t3_start = t2_end + 1;

        List<Integer> interval = new ArrayList<>();
        interval.add(t1_end);
        interval.add(t2_start);
        interval.add(t2_end);
        interval.add(t3_start);
        return interval;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingresa el valor de inicio: ");
        int start = scanner.nextInt();

        System.out.print("Ingresa el valor final: ");
        int end = scanner.nextInt();

        scanner.close();

        if (start > end) {
            System.out.println("El valor de inicio no puede ser mayor al valor final.");
            return;
        }

        List<Integer> interval = divide(start, end);
        int t1_end = interval.get(0);
        int t2_start = interval.get(1);
        int t2_end = interval.get(2);
        int t3_start = interval.get(3);

        Thread thread1 = new Thread(new CountThread(new int[]{start, t1_end}));
        Thread thread2 = new Thread(new CountThread(new int[]{t2_start, t2_end}));
        Thread thread3 = new Thread(new CountThread(new int[]{t3_start, end}));

        thread1.run();
        thread2.run();
        thread3.run();
    }

}
