package Zadanie1PlanowanieDostepuDoProcesow;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Process> initialList;
        int processesNum;
        int minLength;
        int maxLength;
        int maxTimeBetweenProcesses;

//        System.out.println("Very short processes, very dense queue, K from 1 to 36");
//        processesNum = 1000;
//        minLength = 1;
//        maxLength = 40;
//        maxTimeBetweenProcesses = 20;
//        initialList = new GenerateProcesses(processesNum, minLength, maxLength, maxTimeBetweenProcesses).generate();
//        runSeries(processesNum, minLength, maxLength, maxTimeBetweenProcesses, initialList, minLength, maxLength);

//        System.out.println("Very short processes, dense queue, K from 1 to 36");
//        processesNum = 1000;
//        minLength = 1;
//        maxLength = 40;
//        maxTimeBetweenProcesses = 35;
//        initialList = new GenerateProcesses(processesNum, minLength, maxLength, maxTimeBetweenProcesses).generate();
//        runSeries(processesNum, minLength, maxLength, maxTimeBetweenProcesses, initialList, minLength, maxLength);

//        System.out.println("Very short processes, loose queue, K from 1 to 36");
//        processesNum = 1000;
//        minLength = 1;
//        maxLength = 40;
//        maxTimeBetweenProcesses = 50;
//        initialList = new GenerateProcesses(processesNum, minLength, maxLength, maxTimeBetweenProcesses).generate();
//        runSeries(processesNum, minLength, maxLength, maxTimeBetweenProcesses, initialList, minLength, maxLength);

//        System.out.println("Short processes, very dense queue, K from 10 to 40");
//        processesNum = 1000;
//        minLength = 10;
//        maxLength = 40;
//        maxTimeBetweenProcesses = 20;
//        initialList = new GenerateProcesses(processesNum, minLength, maxLength, maxTimeBetweenProcesses).generate();
//        runSeries(processesNum, minLength, maxLength, maxTimeBetweenProcesses, initialList, minLength, maxLength);


//        System.out.println("\n\nShort processes, dense queue, K from 10 to 40");
//        processesNum = 1000;
//        minLength = 10;
//        maxLength = 40;
//        maxTimeBetweenProcesses = 35;
//        initialList = new GenerateProcesses(processesNum, minLength, maxLength, maxTimeBetweenProcesses).generate();
//        runSeries(processesNum, minLength, maxLength, maxTimeBetweenProcesses, initialList, minLength, maxLength);

        System.out.println("\n\nShort processes, loose queue, K from 10 to 40");
        processesNum = 1000;
        minLength = 10;
        maxLength = 40;
        maxTimeBetweenProcesses = 60;
        initialList = new GenerateProcesses(processesNum, minLength, maxLength, maxTimeBetweenProcesses).generate();
        runSeries(processesNum, minLength, maxLength, maxTimeBetweenProcesses, initialList, minLength, maxLength);

//        //adjust starveBarrier
//        System.out.println("\n\nLong processes, very dense queue, K from 10 to 100");
//        processesNum = 1000;
//        minLength = 10;
//        maxLength = 100;
//        maxTimeBetweenProcesses = 30;
//        initialList = new GenerateProcesses(processesNum, minLength, maxLength, maxTimeBetweenProcesses).generate();
//        runSeries(processesNum, minLength, maxLength, maxTimeBetweenProcesses, initialList, minLength, maxLength);


        //adjust starveBarrier
//        System.out.println("\n\nLong processes, dense queue, K from 10 to 100");
//        processesNum = 1000;
//        minLength = 10;
//        maxLength = 100;
//        maxTimeBetweenProcesses = 80;
//        initialList = new GenerateProcesses(processesNum, minLength, maxLength, maxTimeBetweenProcesses).generate();
//        runSeries(processesNum, minLength, maxLength, maxTimeBetweenProcesses, initialList, minLength, maxLength);

//        System.out.println("\n\nLong processes, loose queue, K from 10 to 100")   ;
//        processesNum = 1000;
//        minLength = 10;
//        maxLength = 100;
//        maxTimeBetweenProcesses = 110;
//        initialList = new GenerateProcesses(processesNum, minLength, maxLength, maxTimeBetweenProcesses).generate();
//        runSeries(processesNum, minLength, maxLength, maxTimeBetweenProcesses, initialList, minLength, maxLength);
    }

    public static void runSeries(int processesNum, int minLength, int maxLength, int maxTimeBetweenProcesses, List<Process> initialList, int minK, int maxK) {
        for (int k = minK; k <= maxK; k += 5) {
            runSimulation(processesNum, minLength, maxLength, maxTimeBetweenProcesses, k, initialList);
        }
        System.out.println();
        System.out.println();
    }

    public static void runSimulation(int numberOfProcesses, int minLength, int maxLength, int maxTimeBetween, int K, List<Process> init) {
        List<Process> listForFCFS;
        List<Process> listForSJF;
        List<Process> listForRR;

        Simulation FCFSSim;
        Simulation SJFSim;
        Simulation RRSim;


        System.out.printf("Simulation for numberOfProcesses: %d \tminLength: %d \tmaxLength: %d \tmaxTimeDifference: %d \tK: %d\n", numberOfProcesses, minLength, maxLength, maxTimeBetween, K);
        //GenerateProcesses generator = new GenerateProcesses(numberOfProcesses, minLength, maxLength, maxTimeBetween);
        //init = generator.generate();

        listForFCFS = copyListOfInitialProcesses(init);
        listForSJF = copyListOfInitialProcesses(init);
        listForRR = copyListOfInitialProcesses(init);

        FCFSSim = new FCFS(listForFCFS);
        SJFSim = new SJF(listForSJF);
        RRSim = new RR(listForRR, K);
        FCFSSim.run();
        SJFSim.run();
        RRSim.run();

        displaySimulationData(FCFSSim, SJFSim, RRSim, numberOfProcesses);
    }

    public static void displaySimulationData(Simulation FCFSSim, Simulation SJFSim, Simulation RRSim, int processes) {
        int numLength = 15;
        int descLength = 35;
        String text = padRight("", descLength) + padRight("FCFS", numLength) + padRight("SJF", numLength) + padRight("RR", numLength);
        System.out.println(text);

        text = padRight("Average time start - finish: ", descLength) +
                padRight(String.valueOf(FCFSSim.sumTimeStartToFinish / processes), numLength) +
                padRight(String.valueOf(SJFSim.sumTimeStartToFinish / processes), numLength) +
                padRight(String.valueOf(RRSim.sumTimeStartToFinish / processes), numLength);
        System.out.println(text);

        text = padRight("Average time creation - finish: ", descLength) +
                padRight(String.valueOf(FCFSSim.sumTimeCreationToFinish / processes), numLength) +
                padRight(String.valueOf(SJFSim.sumTimeCreationToFinish / processes), numLength) +
                padRight(String.valueOf(RRSim.sumTimeCreationToFinish / processes), numLength);
        System.out.println(text);

        text = padRight("Average time creation - start: ", descLength) +
                padRight(String.valueOf(FCFSSim.sumTimeCreationToStart / processes), numLength) +
                padRight(String.valueOf(SJFSim.sumTimeCreationToStart / processes), numLength) +
                padRight(String.valueOf(RRSim.sumTimeCreationToStart / processes), numLength);
        System.out.println(text);

        text = padRight("Number of process switches: ", descLength) +
                padRight(String.valueOf(FCFSSim.numberOfProcessSwitches), numLength) +
                padRight(String.valueOf(SJFSim.numberOfProcessSwitches), numLength) +
                padRight(String.valueOf(RRSim.numberOfProcessSwitches), numLength);
        System.out.println(text);

        text = padRight("Number of starved processes: ", descLength) +
                padRight(String.valueOf(FCFSSim.numberOfStarvedProcesses), numLength) +
                padRight(String.valueOf(SJFSim.numberOfStarvedProcesses), numLength) +
                padRight(String.valueOf(RRSim.numberOfStarvedProcesses), numLength);
        System.out.println(text);

        text = padRight("Max wait time: ", descLength) +
                padRight(String.valueOf(FCFSSim.maxWaitTime), numLength) +
                padRight(String.valueOf(SJFSim.maxWaitTime), numLength) +
                padRight(String.valueOf(RRSim.maxWaitTime), numLength);
        System.out.println(text);

        text = padRight("Serve time: ", descLength) +
                padRight(String.valueOf(FCFSSim.serveTime), numLength) +
                padRight(String.valueOf(SJFSim.serveTime), numLength) +
                padRight(String.valueOf(RRSim.serveTime), numLength);
        System.out.println(text);

        text = padRight("Switch time: ", descLength) +
                padRight(String.valueOf(FCFSSim.switchTime), numLength) +
                padRight(String.valueOf(SJFSim.switchTime), numLength) +
                padRight(String.valueOf(RRSim.switchTime), numLength);
        System.out.println(text);

        text = padRight("Empty queue time: ", descLength) +
                padRight(String.valueOf(FCFSSim.emptyQueueTime), numLength) +
                padRight(String.valueOf(SJFSim.emptyQueueTime), numLength) +
                padRight(String.valueOf(RRSim.emptyQueueTime), numLength);
        System.out.println(text);

        text = padRight("Time for everything: ", descLength) +
                padRight(String.valueOf(FCFSSim.serveTime + FCFSSim.switchTime + FCFSSim.emptyQueueTime), numLength) +
                padRight(String.valueOf(SJFSim.serveTime + SJFSim.switchTime + SJFSim.emptyQueueTime), numLength) +
                padRight(String.valueOf(RRSim.serveTime + RRSim.switchTime + RRSim.emptyQueueTime), numLength);
        System.out.println(text);
        System.out.println();
    }

    public static String padRight(String text, int colWidth) {
        return String.format("%-" + colWidth + "s", text);
    }

    public static List<Process> copyListOfInitialProcesses(List<Process> init) {
        List<Process> listToReturn = new ArrayList<>();

        for (int i = 0; i < init.size(); i++) {
            Process p = init.get(i);
            listToReturn.add(new Process(p.getPID(), p.getLength(), p.getCreationTime()));
        }

        return listToReturn;
    }

}
