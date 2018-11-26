/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.shakespeare;

import java.util.Random;

/**
 *
 * @author Vaishali Tripathi
 */
public class MainClass {

    private String target;
    private int popmax;
    private double mutationRate;
    private Population population;

    private String bestPhrase;
    private String allPhrases;

    /**
     * @param args the command line arguments
     */

    public MainClass() {
        target = "To be or not to be.";
        popmax = 20;
        mutationRate = 0.01;
         population = new Population(target, mutationRate, popmax);
    }

    public static void main(String args[]) {
        MainClass m = new MainClass();
        System.out.println("Target: " + m.target);

        while (true) {
            m.population.naturalSelection();
            
            m.population.generate();
            m.population.calcFitness();
            m.population.evaluate();

            if (m.population.isFinished())
                break;            
            System.out.println(m.population.getBest());
        }

    }

}
