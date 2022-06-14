package Zadanie5RownowazenieObcProcesorow;

import java.util.ArrayList;
import java.util.LinkedList;

public class Strategy2 extends Controller {
    public Strategy2(ArrayList<Processor> processors, int upperBound, int numToAsk) {
        super(processors, upperBound, numToAsk);
    }

    @Override
    public void run() {
        clear();

        LinkedList<Process> toAddNow;
        Processor cur;

        while (!done()) {
            //adding new processes
            for (int i = 0; i < processors.size(); i++) {
                cur = processors.get(i);

                //list of processes to add now in cur processor
                toAddNow = cur.processesToAddNow(t);

                while (cur.getLoad() <= upperBound && !toAddNow.isEmpty()) {
                    cur.addProcess(toAddNow.removeFirst());
                }

                while (!toAddNow.isEmpty()) {
                    //looking for processor that has load<=upperBound
                    Processor available = findAvailable(cur);

                    //if found add one process to it
                    if (available != null) {
                        numberOfMigrations++;
                        available.addProcess(toAddNow.removeFirst());
                    }
                    //if not found add processes to cur processor
                    else {
                        cur.addProcesses(toAddNow);
                        toAddNow.clear();
                    }
                }
            }

            //serving processes
            for (int i = 0; i < processors.size(); i++) {
                cur = processors.get(i);
                cur.serve();
            }

            t++;
        }

        printStatistics(2);
    }
}
