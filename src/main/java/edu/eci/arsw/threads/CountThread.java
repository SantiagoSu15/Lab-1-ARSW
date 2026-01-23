package edu.eci.arsw.threads;


import java.util.concurrent.SynchronousQueue;

public class CountThread extends Thread{
    private int numberoA;
    private int numberoB;


    public CountThread(int a, int b){
        this.numberoA = a;
        this.numberoB = b;
    }

    public CountThread(){
    }

    @Override
    public void run(){
        incrementa();
    }

    public synchronized void incrementa(){
        for(int i = numberoA; i <= numberoB; i++){
            System.out.println(i);
        }
    }
    public void setNumberoA(int numberoA) {
        this.numberoA = numberoA;
    }

    public void setNumberoB(int numberoB) {
        this.numberoB = numberoB;
    }

    public void setNumeros(int a, int b){
        this.numberoA = a;
        this.numberoB = b;
    }
}
