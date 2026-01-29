package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;


public class serverSearch extends Thread{
    
    private HostBlacklistsDataSourceFacade blacklist;
    private int a;
    private int b;

    public serverSearch(HostBlacklistsDataSourceFacade blacklist, int a, int b){
        this.blacklist = blacklist;
        this.a = a;
        this.b = b;
    }

    public serverSearch(HostBlacklistsDataSourceFacade blacklist){
        this.blacklist = blacklist;
    }

    @Override
    public void run(){

    }



    public void setNumeros(int a, int b){
        this.a = a;
        this.b = b;
    }
}
