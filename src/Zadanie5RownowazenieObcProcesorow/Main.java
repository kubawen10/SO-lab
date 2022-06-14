package Zadanie5RownowazenieObcProcesorow;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int numOfProcessors = 5;
        int numOfProcesses = 10;
        int minLoad = 2;
        int maxLoad = 15;
        int minTime = 5;
        int maxTime = 20;
        int minTimeBetween = 0;
        int maxTimeBetween = 10;
        ArrayList<Processor> processors = ProcessorsGenerator.strictLoad(numOfProcessors, numOfProcesses,  minLoad, maxLoad, minTime, maxTime, minTimeBetween, maxTimeBetween);
        Controller s1 = new Strategy1(processors, 70, 3);
        Controller s2 = new Strategy2(processors, 70, 3);
        Controller s3 = new Strategy3(processors, 70, 3, 30);

        s1.run();
    }
}
