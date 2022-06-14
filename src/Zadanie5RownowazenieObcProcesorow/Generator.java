package Zadanie5RownowazenieObcProcesorow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;;

public class Generator {
    private static final Random r = new Random();

    public static LinkedList<Process> generateProcesses(int numberOfProcesses, int numberOfProcessors, int maxTime, int maxLoad) {
        LinkedList<Process> processes = new LinkedList<>();
        int entryTime = 1;
        for (int i = 0; i < numberOfProcesses; i++) {
            int cpu = r.nextInt(numberOfProcessors);
            int load = r.nextInt(maxLoad) + 1;
            int time = r.nextInt(maxTime) + 5;
            processes.addLast(new Process(cpu, load, time, entryTime));

            entryTime += r.nextInt(3);
        }

        return processes;
    }

    public static ArrayList<Processor> generateProcessors(int numberOfProcessors) {
        ArrayList<Processor> processors = new ArrayList<>(numberOfProcessors);

        for (int i = 0; i < numberOfProcessors; i++) {
            processors.add(new Processor(i));
        }

        return processors;
    }

    public static LinkedList<Process> copyProcesses(LinkedList<Process> processes) {
        LinkedList<Process> copied = new LinkedList<>();

        Iterator<Process> it = processes.iterator();

        Process p;
        while (it.hasNext()) {
            p = it.next();
            copied.addLast(p.copyProcess());
        }

        return copied;
    }
}
