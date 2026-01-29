package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;


public class serverSearch extends Thread{
    
    private HostBlacklistsDataSourceFacade blacklist;
    private int a;
    private int b;
    private int vecesEnLista = 0;
    private String ipAddr;
    LinkedList<Integer> blackListOcurrences=new LinkedList<>();



    public serverSearch(HostBlacklistsDataSourceFacade blacklist, int a, int b){
        this.blacklist = blacklist;
        this.a = a;
        this.b = b;
    }

    public serverSearch(HostBlacklistsDataSourceFacade blacklist,String ipaddress){
        this.blacklist = blacklist;
        this.ipAddr = ipaddress;
    }

    @Override
    public void run(){
        busqueda();
    }


    private void busqueda(){
        for(int i = a; i <= b; i++){
            if(blacklist.isInBlackListServer(i,this.ipAddr)){
                blackListOcurrences.add(i);

                vecesEnLista++;
            }

        }    
    }

    public int ocurrenciasEncontradas(){
        return this.vecesEnLista;
    }

    public LinkedList<Integer> listasEncontradas(){
        return this.blackListOcurrences;
    }
    
    public void setNumeros(int a, int b){
        this.a = a;
        this.b = b;
    }
}
