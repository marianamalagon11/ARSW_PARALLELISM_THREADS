package edu.eci.arsw.threads;

/**
 *
 * @author Paula Lozano
 * @author Mariana Malagón
 */
public class CountThread implements Runnable{
    private int start, end;

    public CountThread(int[] interval){
        setInterval(interval);
    }
    

    @Override
    public void run(){
        for(int i = start; i <= end; i++){
            System.out.println(i);
        }
    }

    public void setInterval(int[] interval){
        start = interval[0];
        end = interval[1];
    }
}
