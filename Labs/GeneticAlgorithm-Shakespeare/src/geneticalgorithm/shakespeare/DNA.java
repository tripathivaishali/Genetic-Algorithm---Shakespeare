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
public class DNA {

    public char[] genes = new char[39];
    public int fitness = 0;
    Random r = new Random();

    public DNA(int length) {
        //Random r = new Random();
        for (int i = 0; i < length; i++) {
            genes[i] = (char) (r.nextInt(26) + 'a');
           // System.out.println(genes[i]);
        }

    }

    public int calcFitness(String target) {
        int score = 0;
        for (int i = 0; i < this.genes.length; i++) {
            if (this.genes[i] == target.charAt(i)) {
                score++;
            }
        }
        fitness = score / target.length();
        return fitness;
    }

    // Crossover
    public DNA crossover(DNA partner) {
        // A new child
        DNA child = new DNA(this.genes.length);
        //Random rn = new Random();
        int midpoint = (r.nextInt(this.genes.length)); // Pick a midpoint

        // Half from one, half from the other
        for (int i = 0; i < this.genes.length; i++) {
            if (i > midpoint) {
                child.genes[i] = this.genes[i];
            } else {
                child.genes[i] = partner.genes[i];
            }
        }
        return child;
    }

    // Based on a mutation probability, picks a new random character
    public void mutate(double mutationRate) {

        double rangeMin = 0.0f;
        double rangeMax = 1.0f;
        double createdRanNum = 0;

        for (int i = 0; i < this.genes.length; i++) {
            createdRanNum = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            if (createdRanNum < mutationRate) {
                this.genes[i] = (char) (r.nextInt(26) + 'a');
            }
        }
    }
}
