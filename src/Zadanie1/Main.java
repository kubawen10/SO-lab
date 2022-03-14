package Zadanie1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //creating same processes for different algorithms
        List<Process> init = generateInitalValues();
        //System.out.println(init);

        List <Process> listForFCFS = new ArrayList<>();
        List<Process> listForSJF = new ArrayList<>();
        List<Process> listForRR = new ArrayList<>();

        for (int i = 0; i < init.size(); i++) {
            Process p = init.get(i);
            int number = p.getNumber();
            int length = p.getLength();
            long creationTime = p.getCreationTime();

            listForFCFS.add(new Process(number, length, creationTime));
            listForSJF.add(new Process(number, length, creationTime));
            listForRR.add(new Process(number, length, creationTime));
        }

        TimeSimulation FCFSSim = new TimeSimulation(listForFCFS, new AddAtTheEnd());
        FCFSSim.FCFS();
    }

    public static List<Process> generateInitalValues() {
        int maxLength = 20;
        int maxCreationTimeDifference = 30;
        int numberOfProcesses = 1000;

        int processNumber = 4;
        long beginning = 0;
        Random random = new Random();
        List<Process> list = new ArrayList<>();

        int length;
        long creationTime;

        //some initial processes
        list.add(new Process(1, 7, 0));
        list.add(new Process(2, 8, 0));
        list.add(new Process(3, 9, 0));

        for (int i = 0; i < numberOfProcesses; i++) {

            length = random.nextInt(maxLength) + 1;
            creationTime = random.nextInt(maxCreationTimeDifference);
            beginning += creationTime;

            list.add(new Process(processNumber, length, beginning));
            processNumber++;
        }
        return list;
    }
}
