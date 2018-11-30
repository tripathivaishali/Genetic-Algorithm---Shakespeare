/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.shakespeare;

import java.util.Random;
import sun.awt.windows.WToolkit;

/**
 *
 * @author Vaishali Tripathi
 */
public class MainClass {

    private String target;
    private int popmax;
    private double mutationRate;
    private Population population;

//    private String bestPhrase;
//    private String allPhrases;

    /**
     * @param args the command line arguments
     */

    public MainClass() {
        target = "to be or not to be that is the question";
        popmax = 100000;
        mutationRate = 100;
         population = new Population(target, mutationRate, popmax);
//         System.out.println("\nPopulation instanciated.\nTarget: "+target+"\nMutationRate: "
//                 +mutationRate+"\nPopmax: "+popmax);
    }

    public static void main(String args[]) {
        MainClass m = new MainClass();
//        System.out.println("Target: " + m.target);
         //char[] genes = new char[100];
         Random r = new Random();
         while(!m.population.isFinished())
         {
             m.population.evaluate();
         
         m.population.naturalSelection();
         
         m.population.getAverageFitness();
         //m.population.generate();
         
         }
         
         System.out.println("Population size: "+m.popmax);
         System.out.println("Number of generations: "+m.population.getGenerations());
//        for (int i = 0; i < 20; i++) {
//            genes[i] = (char) (r.nextInt(26)+'a');
//            
//        }
//        System.out.print(genes);
//        System.out.print("Target length: "+m.target.length());

//        while (true) {
//            m.population.naturalSelection();
//            
//            m.population.generate();
//            m.population.calcFitness();
//            m.population.evaluate();
//
//            if (m.population.isFinished())
//                break;            
//            System.out.println(m.population.getBest());
//        }

    }

}
