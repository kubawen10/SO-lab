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

        minLength = 1;
        maxLength = 20;
        maxCreationTimeDifference = 25;
        numberOfProcesses = 1000;

        System.out.printf("Simulation for minLength: %d \tmaxLength: %d \tmaxTimeDifference: %d \tnumberOfProcesses: %d\n", minLength, maxLength, maxCreationTimeDifference, numberOfProcesses);
        List<Process> init = generateInitalProcesses(minLength, maxLength, maxCreationTimeDifference, numberOfProcesses);
        List<Process> listForFCFS = copyListOfInitialProcesses(init);
        List<Process> listForSJF = copyListOfInitialProcesses(init);
        List<Process> listForRR = copyListOfInitialProcesses(init);

        TimeSimulation FCFSSim = new TimeSimulation(listForFCFS, new AddAtTheEnd());
        FCFSSim.FCFS();
    }

    public static List<Process> generateInitalProcesses(int minLength, int maxLength, int maxCreationTimeDifference, int numberOfProcesses) {
        int processNumber = 4;
        long beginning = 0;
        Random random = new Random();
        List<Process> initialList = new ArrayList<>();

        int length;
        long creationTime;

        //some initial processes
        initialList.add(new Process(1, minLength, 0));
        initialList.add(new Process(2, minLength + 1, 0));
        initialList.add(new Process(3, minLength + 2, 0));

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
