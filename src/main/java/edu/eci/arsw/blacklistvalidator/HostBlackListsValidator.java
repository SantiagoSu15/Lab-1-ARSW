/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    

    public void dividirValores(int inter, int numHilos, List<serverSearch> listaHilos) {
        int intervalo = inter / numHilos;
        int inicio = 0;

        for(serverSearch t : listaHilos) {
            int fin = inicio + intervalo;
            System.out.println("intervalo: inicio: " + inicio  +", Final: " + fin);
            t.setNumeros(inicio,fin);
            inicio = fin + 1;
        }
    }



    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int maxHilosCorriendo){
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
        
        int ocurrencesCount=0;
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        
        int checkedListsCount=0;

        List<serverSearch> totalHilos= new ArrayList<serverSearch>();

        int numberSvs = skds.getRegisteredServersCount();


        for(int hilo = 0; hilo< maxHilosCorriendo;hilo++){
            serverSearch h = new serverSearch(skds,ipaddress);
            totalHilos.add(h);
        }

        dividirValores(numberSvs, totalHilos.size(), totalHilos);


        for(serverSearch t :totalHilos ){
            t.start();
        }

        
        for (serverSearch t : totalHilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Logger.getLogger(HostBlackListsValidator.class.getName()).log(Level.SEVERE, "Error al esperar que el hilo termine", e);
                Thread.currentThread().interrupt();
            }
        }

        for(serverSearch t : totalHilos){
            int mientras = t.ocurrenciasEncontradas();
            ocurrencesCount += mientras; 
            blackListOcurrences.addAll(t.listasEncontradas());
        }



        
        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
