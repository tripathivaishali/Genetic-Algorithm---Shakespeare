/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm.shakespeare;

import static java.lang.Float.min;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Vaishali Tripathi
 */
public class Population {

    private ArrayList<String> matingPool = new ArrayList<String>();
  //  private ArrayList<DNA> popdna = new ArrayList<DNA>();
   
    private double fitness = 0;
    private char[] genes = new char[32];
    private String target;
    private int popmax;
    private double mutationRate;
    Random ran = new Random();
    private int generations = 0;
    private String best = "";
    private double perfectScore = 30.01;
    private boolean finished = false;
    private String[] pop = new String[1000];
    public double[] fit = new double[1000];
    // Create a population with a target phrase, mutation rate, and population max
    public Population(String target, double mutationRate, int popmax) {
        this.target = target;
        this.popmax = popmax;
        this.mutationRate = mutationRate;
        CreatePopulation(target, popmax);
    }

    public void CreatePopulation(String target, int popmax) {
       // Arrays.fill(fit, 0);
        for (int i = 0; i < popmax; i++) {
            pop[i] = newgene(target);
            
            
           // popdna=(new DNA(pop[i]));
            //System.out.println("Population at " + i + ": " + pop[i]);
        }
    }

    public String newgene(String target) {
        for (int j = 0; j < target.length(); j++) {
            genes[j] = (char) (ran.nextInt(26) + 'a');
            
//                System.out.println("genes["+j+"]: "+genes[j]);
        }
        String text = String.valueOf(genes);
        return text;
    }

    public void calcFitness() {
        for (int i = 0; i < pop.length; i++) {
            int score = 0;
           // System.out.print("i = "+i +"\n");
            for (int j = 0; j < target.length(); j++) {
                if (pop[i].charAt(j) == target.charAt(j)) {
                    score++;
                }
//                System.out.print(pop[i].charAt(j));
                fitness = (score ) +0.01;
//                fitness = score;


            }
            fit[i]=fitness;
//             System.out.println("Fitness of "+pop[i]+" is: "+ fit[i]);
        //    System.out.println("\nFitness: "+ fit[i]);
//            System.out.println("Value of genes.length: "+ genes.length);

        }
        
       // return fit;
    }

    //Generate a mating pool
    public void naturalSelection() {
//         Clear the ArrayList
        matingPool.clear();

        double maxFitness = 0;
        for (int i = 0; i < fit.length; i++) {
            if (fit[i]> maxFitness) {
                maxFitness = fit[i];
            }
        }
        for (int i = 0; i < pop.length; i++) {

            double fitness = (fit[i] / maxFitness);
            double n = (fitness * 100); // Arbitrary multiplier, we can also use monte carlo method
            for (int j = 0; j < n; j++) { // and pick two random numbers
                matingPool.add(pop[i]);
            }
            
        }
//        System.out.println("\nMating pool contents: ");
        /*for(int i = 0; i <matingPool.size(); i++){
            System.out.println(matingPool.get(i));
        }*/
        generate();
    }
    
//// Create a new generation

    public void generate() {
        //Refill the population with children from the mating pool
//        System.out.println("Size of the mating pool: "+matingPool.size());
        for (int i = 0; i < pop.length; i++) {
//             System.out.println("i= "+i);
            int a = (ran.nextInt(matingPool.size()));
            int b = (ran.nextInt(matingPool.size()));
            String partnerA = matingPool.get(a);
            String partnerB = matingPool.get(b);
//            System.out.println("PartnerA: "+ partnerA);
//            System.out.println("PartnerB: "+ partnerB);
            String child = crossover(partnerA,partnerB);
            mutate(child,mutationRate);
            pop[i] = (child);
            
            

        }
        calcFitness();
        this.generations++;
    }
    
    public String crossover(String partnerA, String partnerB) {
        // A new child
        char[] child = new char[32];
        //Random rn = new Random();
        int midpoint = (ran.nextInt(this.genes.length)); // Pick a midpoint

        // Half from one, half from the other
        for (int i = 0; i < genes.length; i++) {
            if (i > midpoint) {
                child[i] = partnerA.charAt(i);
            } else {
                child[i] = partnerB.charAt(i);
            }
        }

        return String.valueOf(child);
    }
    
    
    public void mutate(String child,double mutationRate) {

        double rangeMin = 0.0f;
        double rangeMax = 1.0f;
        double createdRanNum = 0;
        char[] childChars = child.toCharArray();
        for (int i = 0; i < childChars.length; i++) {
            createdRanNum = rangeMin + (rangeMax - rangeMin) * ran.nextDouble();
            if (createdRanNum < mutationRate) {
         //       this.genes[i] = (char) (r.nextInt(26) + 'a');
         childChars[i] = (char) (ran.nextInt(26) + 'a');
//            System.out.println("Mutated Child at :"+i+" with "+ childChars[i]);
            }
        }
        child = String.valueOf(childChars);
//        System.out.println("Child: "+ child);
    }

    public String getBest() {
        return best;
    }

    // Compute the current "most fit" member of the population
    public void evaluate() {
        double worldrecord = 0.0;
        int index = 0;
        for (int i = 0; i < pop.length; i++) {
            if (fit[i] > worldrecord) {
                index = i;
                worldrecord = fit[i];
            }
        }

        
        
        if (worldrecord == perfectScore) {
            finished = true;
        }
        best = pop[index];
        System.out.println("\nBest gene: "+best+" Fitness: "+ fit[index]);
        System.out.println("Perfectscore: "+ perfectScore);
    }

    public boolean isFinished() {
        return finished;
    }

    public int getGenerations() {
        return generations;
    }

    // Compute average fitness for the population
    public double getAverageFitness() {
        double total = 0;
        for (int i = 0; i < pop.length; i++) {
            total += fit[i];
        }
        System.out.println("Average fitness of the generation: "+ total / (pop.length));
        return total / (pop.length);
    }
/*
    public String allPhrases() {
        String everything = "";

        double displayLimit = min(pop.length, 50);

        for (int i = 0; i < displayLimit; i++) {
            everything += pop[i].toString() + "<br>";
        }
        return everything;
    }
*/
}
