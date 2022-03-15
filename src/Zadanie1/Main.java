package Zadanie1;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //creating same processes for different algorithms
        int minLength;
        int maxLength;
        int maxCreationTimeDifference;
        int numberOfProcesses;

        minLength = 10;
        maxLength = 50;
        maxCreationTimeDifference = 60; //processor load
        numberOfProcesses = 10;

        System.out.printf("Simulation for minLength: %d \tmaxLength: %d \tmaxTimeDifference: %d \tnumberOfProcesses: %d\n", minLength, maxLength, maxCreationTimeDifference, numberOfProcesses);
        List<Process> init = generateInitalProcesses(minLength, maxLength, maxCreationTimeDifference, numberOfProcesses);
        List<Process> listForFCFS = copyListOfInitialProcesses(init);
        List<Process> listForSJF = copyListOfInitialProcesses(init);
        List<Process> listForRR = copyListOfInitialProcesses(init);

        Simulation FCFS = new FCFSSimulation(listForFCFS);
        Simulation SJF = new SJFSimulation(listForSJF);
        Simulation RR = new RRSimulation(listForRR, 15);
//        FCFS.run();
//        SJF.run();
        RR.run();
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
        for (int i = 0; i < numberOfProcesses; i++) {
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
