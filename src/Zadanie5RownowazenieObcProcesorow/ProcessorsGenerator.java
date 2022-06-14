package Zadanie5RownowazenieObcProcesorow;

import java.util.ArrayList;
import java.util.Random;;

public class ProcessorsGenerator {
    private static final Random r = new Random();

    public static ArrayList<Processor> strictLoad(int numOfProcessors, int numOfProcesses, int minLoad, int maxLoad, int minTime, int maxTime, int minTimeBetween, int maxTimeBetween) {
        ArrayList<Processor> processors = new ArrayList<>(numOfProcessors);

        for (int i = 0; i < numOfProcessors; i++) {
            int curMinLoad = randomIntGenerator(minLoad, (minLoad + maxLoad) / 2);
            int curMaxLoad = randomIntGenerator((minLoad + maxLoad) / 2, maxLoad);
            int curMinTime = randomIntGenerator(minTime, (minTime + maxTime) / 2);
            int curMaxTime = randomIntGenerator((minTime + maxTime) / 2, maxTime);
            int curMinTimeBetween = randomIntGenerator(minTimeBetween, (minTimeBetween + maxTimeBetween) / 2);
            int curMaxTimeBetween = randomIntGenerator((minTimeBetween + maxTimeBetween) / 2, maxTimeBetween);
            Processor p = new Processor();

            p.generateProcesses(numOfProcesses, curMinLoad, curMaxLoad, curMinTime, curMaxTime, curMinTimeBetween, curMaxTimeBetween);
            processors.add(p);
        }
        printProcessors(processors);

        return processors;
    }

    private static void printProcessors(ArrayList<Processor> processors){
        for (int i = 0; i < processors.size(); i++) {
            System.out.println(processors.get(i));
        }
    }

    private static int randomIntGenerator(int from, int to) {
        return r.nextInt(to - from) + from;
    }
}
