package Zadanie1AlgorytmyPlanowania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateProcesses {
    private int numberOfProcesses;
    private int minLengthOfProcesses;
    private int maxLengthOfProcesses;
    private int maxTimeBetweenProcesses;

    private Random random;
    private List<Process> processList = new ArrayList<>();
    private int beginning = 0;

    public GenerateProcesses(int numberOfProcesses, int minLengthOfProcesses, int maxLengthOfProcesses, int maxTimeBetweenProcesses) {
        this.numberOfProcesses = numberOfProcesses;
        this.minLengthOfProcesses = minLengthOfProcesses;
        this.maxLengthOfProcesses = maxLengthOfProcesses;
        this.maxTimeBetweenProcesses = maxTimeBetweenProcesses;
        random = new Random();
    }

    public List<Process> generate() {
        for (int i = 1; i <= numberOfProcesses; i++) {
            processList.add(newProcess(i));
        }

        return processList;
    }

    public Process newProcess(int PID) {
        int length = generateLength();

        //lastProcess start + nextProcess start
        beginning += random.nextInt(maxTimeBetweenProcesses) + 1;

        if (PID <= 3) {
            beginning = 0;
        }

        return new Process(PID, length, beginning);
    }

    public int generateLength() {
        double mean = (minLengthOfProcesses + maxLengthOfProcesses) / 2;
        double stddev = (maxLengthOfProcesses - minLengthOfProcesses) / 4;

        double length = random.nextGaussian() * stddev + mean;

        if (length < minLengthOfProcesses) {
            length=minLengthOfProcesses;
        } else if (length > maxLengthOfProcesses) {
           length=maxLengthOfProcesses;
        }

        return (int) length;
    }
}
