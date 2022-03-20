package Zadanie1;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("high processor use, lot of changes for RR");
        runSimulation(1, 20, 5, 10000, 1);
        System.out.println("high processor use, medium changes for RR");
        runSimulation(1, 20, 5, 10000, 5);
        System.out.println("high processor use,  low changes for RR");
        runSimulation(1, 20, 5, 10000, 15);

        System.out.println("medium processor use, lot of changes for RR");
        runSimulation(1, 20, 15, 10000, 1);
        System.out.println("medium processor use, medium changes for RR");
        runSimulation(1, 20, 15, 10000, 5);
        System.out.println("medium processor use, low changes for RR");
        runSimulation(1, 20, 15, 10000, 15);

        System.out.println("low processor use, lot of changes for RR");
        runSimulation(1, 20, 30, 10000, 1);
        System.out.println("low processor use, medium changes for RR");
        runSimulation(1, 20, 30, 10000, 5);
        System.out.println("low processor use, low changes for RR");
        runSimulation(1, 20, 30, 10000, 15);

        System.out.println("longer processes, high processor use, lot of changes for RR");
        runSimulation(30, 60, 35, 10000, 30);
        System.out.println("longer processes, medium processor use, medium changes for RR");
        runSimulation(30, 60, 50, 10000, 40);
        System.out.println("longer processes, low processor use, low changes for RR");
        runSimulation(30, 60, 70, 10000, 50);
    }

    public static void runSimulation(int minLength, int maxLength, int maxCreationTimeDifference, int numberOfProcesses, int K){
        List<Process> init;
        List<Process> listForFCFS;
        List<Process> listForSJF;
        List<Process> listForRR;

        Simulation FCFS;
        Simulation SJF;
        Simulation RR;


        System.out.printf("Simulation for minLength: %d \tmaxLength: %d \tmaxTimeDifference: %d \tnumberOfProcesses: %d\n", minLength, maxLength, maxCreationTimeDifference, numberOfProcesses);
        init = generateInitalProcesses(minLength, maxLength, maxCreationTimeDifference, numberOfProcesses);
        listForFCFS = copyListOfInitialProcesses(init);
        listForSJF = copyListOfInitialProcesses(init);
        listForRR = copyListOfInitialProcesses(init);

        FCFS = new FCFSSimulation(listForFCFS);
        SJF = new SJFSimulation(listForSJF);
        RR = new RRSimulation(listForRR, K);
        FCFS.run();
        System.out.println();
        SJF.run();
        System.out.println();
        RR.run();
        System.out.println();
    }

    public static List<Process> generateInitalProcesses(int minLength, int maxLength, int maxCreationTimeDifference, int numberOfProcesses) {
        int processNumber = 4;
        long beginning = 0;
        Random random = new Random();
        List<Process> initialList = new ArrayList<>();

        int length;
        long creationTime;

        //some initial processes
        initialList.add(new Process(1, 4, 1));
        initialList.add(new Process(2, 6, 2));
        initialList.add(new Process(3, 8, 3));

        //creating processes, add gausian?
        for (int i = 0; i < numberOfProcesses - 3 ; i++) {
            length = random.nextInt(maxLength + 1 - minLength) + minLength;
            creationTime = random.nextInt(maxCreationTimeDifference);
            beginning += creationTime;

            initialList.add(new Process(processNumber, length, beginning));
            processNumber++;
        }
        return initialList;

    }

    public static List<Process> copyListOfInitialProcesses(List<Process> init) {
        List<Process> listToReturn = new ArrayList<>();

        for (int i = 0; i < init.size(); i++) {
            Process p = init.get(i);
            listToReturn.add(new Process(p.getNumber(), p.getLength(), p.getCreationTime()));
        }

        return listToReturn;
    }
}
