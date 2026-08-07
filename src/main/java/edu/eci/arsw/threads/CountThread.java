package edu.eci.arsw.threads;

/**
 *
 * @author Paula Lozano
 * @author Mariana Malagón
 */
public class CountThread implements Runnable{
    private int start, end;

    public CountThread(int start, int end){
        this.start = start;
        this.end = end;
    }
    
    @Override
    public void run(){
        for(int i = start; i <= end; i++){
            System.out.println(
                Thread.currentThread().getName() + ": " + i
            );
        }
    }

}
