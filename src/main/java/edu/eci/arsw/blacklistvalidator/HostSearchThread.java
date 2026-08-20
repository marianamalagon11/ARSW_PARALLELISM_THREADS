package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.*;
import java.util.List;

/**
 * @author Mariana Malagón
 * @author Paula Lozano
 */

public class HostSearchThread extends Thread {
    private int start, end;
    private String ipAdress;
    private HostBlacklistsDataSourceFacade facade;
    private List<Integer> ocurrencesShared;
    private int checkedListsCount = 0;
    private int BLACK_LIST_ALARM_COUNT;

    public HostSearchThread(int start, int end, String ipAdress, HostBlacklistsDataSourceFacade facade, List<Integer> ocurrencesShared, int BLACK_LIST_ALARM_COUNT){
        this.start = start;
        this.end = end;
        this.ipAdress = ipAdress;
        this.facade = facade;
        this.ocurrencesShared = ocurrencesShared;
        this.BLACK_LIST_ALARM_COUNT = BLACK_LIST_ALARM_COUNT;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {

            // Chequeo al INICIO de cada iteración: cualquier hilo,
            // haya encontrado algo o no, verifica si ya se cerró la búsqueda.
            synchronized (ocurrencesShared) {
                if (ocurrencesShared.size() >= BLACK_LIST_ALARM_COUNT) {
                    return;
                }
            }

            checkedListsCount++;

            if (facade.isInBlackListServer(i, ipAdress)) {
                synchronized (ocurrencesShared) {
                    if (ocurrencesShared.size() >= BLACK_LIST_ALARM_COUNT) {
                        return;
                    }
                    ocurrencesShared.add(i);
                    if (ocurrencesShared.size() >= BLACK_LIST_ALARM_COUNT) {
                        return;
                    }
                }
            }
        }
    }

    public int getCheckedListsCount() {
        return checkedListsCount;
    }
}