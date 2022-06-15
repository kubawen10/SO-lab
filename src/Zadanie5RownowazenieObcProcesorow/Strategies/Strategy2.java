package Zadanie5RownowazenieObcProcesorow.Strategies;

import Zadanie5RownowazenieObcProcesorow.Process;
import Zadanie5RownowazenieObcProcesorow.Processor;

import java.util.ArrayList;
import java.util.LinkedList;

public class Strategy2 extends Controller {
    public Strategy2(ArrayList<Processor> processors, LinkedList<Process> processes, int upperBound, int numToAsk) {
        super(processors, processes, upperBound, numToAsk);
    }

    @Override
    public void run(int maxT) {
        reset();

        while (!done()) {
            //System.out.println("Time: " + t);

            //adding new processes
            while (!done() && processes.getFirst().getEntryTime() <= t) {
                Process addNow = processes.removeFirst();

                Processor curProcessor = processors.get(addNow.getCpu());
                curProcessor.lowerLoadAsks();
                if (curProcessor.getLoad() > upperBound) {
                    //finding processor to send process to
                    Processor available = findAvailable(addNow.getCpu());
                    if (available != null) {
                        curProcessor = available;
                    }
                }
                curProcessor.addProcess(addNow);
            }
            statCreation(maxT);

            //printCurServed();

            //serving processes
            serveProcesses();
            t++;
        }

        printStatistics(2);
    }
}
