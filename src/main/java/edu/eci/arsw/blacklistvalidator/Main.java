/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

public class Main {

    public static void main(String[] args) {

        HostBlackListsValidator hblv = new HostBlackListsValidator();
        String ip = "202.24.34.55";

        int cores = Runtime.getRuntime().availableProcessors();
        int[] pruebas = {1, cores, cores * 2, 50, 100};

        System.out.println("Núcleos disponibles: " + cores);

        for (int hilos : pruebas) {
            long inicio = System.currentTimeMillis();
            hblv.checkHost(ip, hilos);
            long fin = System.currentTimeMillis();

            System.out.println("Hilos: " + hilos +
                               " | Tiempo(ms): " + (fin - inicio));
        }
    }
}


