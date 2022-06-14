package Zadanie5RownowazenieObcProcesorow.Strategies;

import Zadanie5RownowazenieObcProcesorow.Process;
import Zadanie5RownowazenieObcProcesorow.Processor;

import java.util.ArrayList;
import java.util.LinkedList;

public class Strategy1 extends Controller {
    public Strategy1(ArrayList<Processor> processors, LinkedList<Process> processes, int upperBound, int numToAsk) {
        super(processors, processes, upperBound, numToAsk);
    }

    @Override
    public void run() {
        reset();

        while (!done()) {
            //System.out.println("Time: " + t);

            //adding new processes
            while (!done() && processes.getFirst().getEntryTime() <= t) {
                Process addNow = processes.removeFirst();

                //finding processor to send process to
                Processor available = findAvailable(addNow.getCpu());

                Processor curProcessor;
                if (available != null) {
                    curProcessor = available;
                } else {
                    curProcessor = processors.get(addNow.getCpu());
                }

                curProcessor.addProcess(addNow);
            }
            statCreation();

            //printCurServed();

            //serving processes
            serveProcesses();
            t++;
        }

        printStatistics(1);
    }
}
