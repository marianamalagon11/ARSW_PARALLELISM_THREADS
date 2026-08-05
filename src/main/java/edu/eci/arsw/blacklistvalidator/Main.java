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
        int cores = Runtime.getRuntime().availableProcessors(); // this is for point 3
        System.out.println("Nucleos disponibles: " + cores); // this is for point 3

        int N = 100; // this is for point 3

        HostBlackListsValidator hblv=new HostBlackListsValidator();

        long startTime = System.currentTimeMillis(); // this is for point 3
        List<Integer> blackListOcurrences=hblv.checkHost("202.24.34.55", N);
        long endTime = System.currentTimeMillis(); // this is for point 3

        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
        System.out.println("N=" + N + " hilos con tiempo: " + (endTime - startTime) + " ms"); // this is for point 3
    }
    
}
