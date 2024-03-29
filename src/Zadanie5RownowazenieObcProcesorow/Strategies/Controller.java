package Zadanie5RownowazenieObcProcesorow.Strategies;

import Zadanie5RownowazenieObcProcesorow.Generator;
import Zadanie5RownowazenieObcProcesorow.Process;
import Zadanie5RownowazenieObcProcesorow.Processor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class Controller {
    protected int upperBound;
    protected int numToAsk;

    protected Random random = new Random();

    protected ArrayList<Double> avgLoads;
    protected ArrayList<Double> stdDevs;

    protected ArrayList<Processor> processors;

    protected LinkedList<Process> processesOriginal;
    protected LinkedList<Process> processes;

    protected int t = 1;

    public Controller(ArrayList<Processor> processors, LinkedList<Process> processes, int upperBound, int numToAsk) {
        this.processors = processors;
        this.processesOriginal = processes;
        this.upperBound = upperBound;
        this.numToAsk = numToAsk;

        reset();
    }

    protected Processor findAvailable(int curProcessor) {
        int i = 0;

        int index = random.nextInt(processors.size());

        Processor cur;
        while (i < numToAsk) {
            if (index == curProcessor) {
                index = (index + 1) % processors.size();
            }

            cur = processors.get(index);

            if (cur.getLoad() <= upperBound) {
                //System.out.println("found");
                return cur;
            }

            i++;
            index = (index + 1) % processors.size();
        }

        return null;
    }

    //saving avg load every x time units
    protected void statCreation(int maxT) {
        if (t % (maxT / 2) != 0) return;

        double sum = 0;
        for (int i = 0; i < processors.size(); i++) {
            processors.get(i).lowerLoadAsks();
            sum += processors.get(i).getLoad();
        }
        double avg = sum / processors.size();
        avgLoads.add(avg);

        for (int i = 0; i < processors.size(); i++) {
            processors.get(i).lowerLoadAsks();
            int x = processors.get(i).getLoad();
            sum += x * x;
        }

        stdDevs.add(Math.sqrt(sum / processors.size()));
    }


    protected void printStatistics(int strategy) {
        System.out.println("Strategy" + strategy + ": ");
        System.out.println(avgLoads);
        System.out.println(stdDevs);
        System.out.println("Load Asks : " + calculateLoadAsks());
        System.out.println("Migrations: " + calculateMigrations());
        System.out.println("Avg overload time: " + calculateAvgOverload());
        System.out.println("Time: " + t);
    }

    private int calculateMigrations() {
        int sum = 0;
        for (int i = 0; i < processors.size(); i++) {
            sum += processors.get(i).getMigrations();
        }

        return sum;
    }

    private int calculateLoadAsks() {
        int sum = 0;
        for (int i = 0; i < processors.size(); i++) {
            sum += processors.get(i).getLoadAsks();
        }

        return sum;
    }

    private double calculateAvgOverload() {
        double sum = 0;
        for (int i = 0; i < processors.size(); i++) {
            sum += processors.get(i).getOverload();
        }

        return sum / processors.size();
    }

    protected void serveProcesses() {
        for (int i = 0; i < processors.size(); i++) {
            processors.get(i).serve();
        }
    }

    protected void printCurServed() {
        for (int i = 0; i < processors.size(); i++) {
            System.out.println(processors.get(i) + ": " + processors.get(i).getCurrentlyServed());
        }
    }

    protected void reset() {
        for (int i = 0; i < processors.size(); i++) {
            processors.get(i).reset();
        }

        processes = Generator.copyProcesses(processesOriginal);
        avgLoads = new ArrayList<>();
        stdDevs = new ArrayList<>();
        t = 1;
    }

    protected boolean done() {
        return processes.isEmpty();
    }

    public abstract void run(int maxT);
}
