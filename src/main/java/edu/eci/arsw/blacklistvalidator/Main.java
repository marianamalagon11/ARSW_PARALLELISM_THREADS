/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.util.List;

/**
 *
 * @author Mariana Malagón
 * @author Paula Lozano
 */
public class Main {
    
    public static void main(String a[]){
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Nucleos disponibles: " + cores);

        int N = 1; // cambia este valor entre corridas: 1, cores, cores*2, 50, 100

        HostBlackListsValidator hblv=new HostBlackListsValidator();

        long startTime = System.currentTimeMillis();
        List<Integer> blackListOcurrences=hblv.checkHost("202.24.34.55", N);
        long endTime = System.currentTimeMillis();

        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
        System.out.println("N=" + N + " hilos -> tiempo: " + (endTime - startTime) + " ms");
    }
    
}
