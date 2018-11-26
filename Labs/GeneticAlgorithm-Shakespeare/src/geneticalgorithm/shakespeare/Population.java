/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.shakespeare;

import static java.lang.Float.min;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Vaishali Tripathi
 */
public class Population {

    private ArrayList<DNA> matingPool = new ArrayList<DNA>();
    private DNA[] pop = new DNA[20];
    private String target;
    private int popmax;
    private double mutationRate;
    Random ran = new Random();
    private int generations = 0;
    private String best = "";
    private double perfectScore = 0;
    private boolean finished = false;
    
    // Create a population with a target phrase, mutation rate, and population max
    public Population(String target, double mutationRate, int popmax) {
        this.target = target;
        this.popmax = popmax;
        this.mutationRate = mutationRate;
        for (int i = 0; i < popmax; i++) {
            pop[i] = new DNA(this.target.length());
           // System.out.println(pop[i].genes);
            
        }

    }

    public void calcFitness() {
        for (int i = 0; i < this.pop.length; i++) {
            this.pop[i].calcFitness(target);
        }
    }

    // Generate a mating pool
    public void naturalSelection() {
        // Clear the ArrayList
        matingPool.clear();

        double maxFitness = 0;
        for (int i = 0; i < pop.length; i++) {
            if (pop[i].fitness > maxFitness) {
               maxFitness = pop[i].fitness;
            }
        }
        for (int i = 0; i < pop.length; i++) {

            double fitness = (pop[i].fitness/maxFitness);
            double n = (fitness * 100); // Arbitrary multiplier, we can also use monte carlo method
            for (int j = 0; j < n; j++) { // and pick two random numbers
                matingPool.add(pop[i]);
                System.out.println(matingPool);
            }
        }

    }
// Create a new generation
  public void generate() {
    // Refill the population with children from the mating pool
    //System.out.println(matingPool.size());
    for (int i = 0; i < pop.length; i++) {
      int a = (ran.nextInt(matingPool.size()));
      int b = (ran.nextInt(matingPool.size()));
      DNA partnerA = matingPool.get(a);
      DNA partnerB = matingPool.get(b);
      DNA child = partnerA.crossover(partnerB);
      child.mutate(mutationRate);
      pop[i] = child;
    }
    this.generations++;
  }


  public String getBest() {
    return best;
  }

  // Compute the current "most fit" member of the population
  public void evaluate() {
    double worldrecord = 0.0;
    int index = 0;
    for (int i = 0; i < pop.length; i++) {
      if (pop[i].fitness > worldrecord) {
        index = i;
        worldrecord = pop[i].fitness;
      }
    }

    best = pop[index].toString();
    if (worldrecord == this.perfectScore) {
      this.finished = true;
    }
  }

  public boolean isFinished() {
    return finished;
  }

  public int getGenerations() {
    return this.generations;
  }

  // Compute average fitness for the population
  public double getAverageFitness() {
    double total = 0;
    for (int i = 0; i < pop.length; i++) {
      total += pop[i].fitness;
    }
    return total / (pop.length);
  }

  public String allPhrases() {
    String everything = "";

    double displayLimit = min(pop.length, 50);


    for (int i = 0; i < displayLimit; i++) {
      everything += pop[i].toString()+ "<br>";
    }
    return everything;
  }
}
