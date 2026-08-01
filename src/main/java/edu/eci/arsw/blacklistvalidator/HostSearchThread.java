package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.*;
import java.util.List;
import java.util.LinkedList;

/**
 *
 * @author Paula Lozano
 * @author Mariana Malagón
 */

public class HostSearchThread extends Thread {
    private int start, end;
    private String ipAdress;
    private HostBlacklistsDataSourceFacade facade; 
    private List<Integer> ocurrences = new LinkedList<>();
    private int checkedListsCount = 0;
    
    public HostSearchThread(int start, int end, String ipAdress, HostBlacklistsDataSourceFacade facade){
        this.start = start;
        this.end = end;
        this.ipAdress = ipAdress;
        this.facade = facade;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            if (facade.isInBlackListServer(i, ipAdress)) {
                ocurrences.add(i);
                checkedListsCount++;
            }
        } 
    }

    public int getOcurrencesCount() {
        return ocurrences.size();
    }

    public List<Integer> getOcurrences() {
        return this.ocurrences;
    }

    public int getCheckedListsCount() {
        return checkedListsCount;
    }
}
