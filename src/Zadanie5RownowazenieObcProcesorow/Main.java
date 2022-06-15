package Zadanie5RownowazenieObcProcesorow;

import Zadanie5RownowazenieObcProcesorow.Strategies.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        int numOfProcessors = 50;
        int numOfProcesses = 100000;
        int maxLoad = 20;
        int maxTime = 300;
        int lowerBound = 30;
        int upperBound = 80;
        int numToAsk = 30;
        //runSim(numOfProcessors, numOfProcesses, maxLoad, maxTime, lowerBound, upperBound, numToAsk);

        numOfProcessors = 50;
        numOfProcesses = 100000;
        maxLoad = 35;
        maxTime = 300;
        lowerBound = 30;
        upperBound = 80;
        numToAsk = 30;
        runSim(numOfProcessors, numOfProcesses, maxLoad, maxTime, lowerBound, upperBound, numToAsk);

        numOfProcessors = 50;
        numOfProcesses = 100000;
        maxLoad = 20;
        maxTime = 200;
        lowerBound = 30;
        upperBound = 80;
        numToAsk = 30;
        //runSim(numOfProcessors, numOfProcesses, maxLoad, maxTime, lowerBound, upperBound, numToAsk);

        numOfProcessors = 50;
        numOfProcesses = 100000;
        maxLoad = 20;
        maxTime = 500;
        lowerBound = 30;
        upperBound = 80;
        numToAsk = 30;
        //runSim(numOfProcessors, numOfProcesses, maxLoad, maxTime, lowerBound, upperBound, numToAsk);
    }

    public static void runSim(int numOfProcessors, int numOfProcesses, int maxLoad, int maxTime, int lowerBound, int upperBound, int numToAsk) {
        System.out.println("Processors: " + numOfProcessors + " Processes: " + numOfProcesses + " maxLoad: " + maxLoad + " maxtime: " + maxTime + " l: " + lowerBound + " u: " + upperBound + " ask: " + numToAsk);
        ArrayList<Processor> processors = Generator.generateProcessors(numOfProcessors);
        LinkedList<Process> processes = Generator.generateProcesses(numOfProcesses, numOfProcessors, maxTime, maxLoad);

        Controller s1 = new Strategy1(processors, processes, upperBound, numToAsk);
        Controller s2 = new Strategy2(processors, processes, upperBound, numToAsk);
        Controller s3 = new Strategy3(processors, processes, upperBound, numToAsk, lowerBound);

        s1.run(maxTime);
        s2.run(maxTime);
        s3.run(maxTime);
        System.out.println();
    }
}
