/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;



import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hcadavid
 */
public class CountThreadsMain {
    private List<CountThread> listaHilos;

    public CountThreadsMain(int numHilos) {
        this.listaHilos = new ArrayList<>();
        for(int i = 0; i < numHilos; i++ ) {
            CountThread t = new CountThread();
            listaHilos.add(t);
        }
    }

     public void agregarValoresHilos(int a, int b) {
        int inter = b-a;
        int numHilos = listaHilos.size();
        int intervalo = inter / numHilos;
        int inicio = 0;

        for(CountThread t : listaHilos) {
            int fin = inicio + intervalo;
            System.out.println("intervalo: inicio: " + inicio  +", Final: " + fin);
            t.setNumeros(inicio,fin);
            inicio = fin + 1;
        }
    }

     public void correr(){
        for(CountThread t : listaHilos) {
            t.start();
        }
    }

    public static void main(String a[]){
        int numHilos = 3;
        CountThreadsMain ct1 = new CountThreadsMain(numHilos);
        ct1.agregarValoresHilos(0,299);
        ct1.correr();
    }
    
}
