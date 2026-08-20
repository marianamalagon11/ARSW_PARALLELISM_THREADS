package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT = 5;

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
    public List<Integer> checkHost(String ipaddress, int N){

        HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();

        int totalServers = skds.getRegisteredServersCount();
        int interval = totalServers / N;

        HostSearchThread[] threads = new HostSearchThread[N];
        List<Integer> ocurrencesShared = Collections.synchronizedList(new LinkedList<>());

        int start, end;

        for (int i = 0; i < N; i++) {
            start = i*interval;
            if (i == N-1) {
                end = totalServers-1;
            } else {
                end = (i+1)*interval - 1;
            }

            threads[i] = new HostSearchThread(start, end, ipaddress, skds, ocurrencesShared, BLACK_LIST_ALARM_COUNT);
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < N; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        int checkedListsCount = 0;
        for (int i = 0; i < N; i++) {
            checkedListsCount += threads[i].getCheckedListsCount();
        }

        int totalOccurrencesCount = ocurrencesShared.size();

        if (totalOccurrencesCount >= BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }

        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});

        return ocurrencesShared;
    }

    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());

}