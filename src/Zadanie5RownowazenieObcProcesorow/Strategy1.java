package Zadanie5RownowazenieObcProcesorow;

import java.util.ArrayList;
import java.util.LinkedList;

public class Strategy1 extends Controller {
    public Strategy1(ArrayList<Processor> processors, int upperBound, int numToAsk) {
        super(processors, upperBound, numToAsk);
    }

    public void run() {
        cloneProcesses();

        LinkedList<Process> toAddNow;
        Processor cur;

        while (!done()) {
            //adding new processes
            for (int i = 0; i < processors.size(); i++) {
                cur = processors.get(i);

                //list of processes to add now in cur processor
                toAddNow = cur.processesToAddNow(t);

                while (!toAddNow.isEmpty()) {
                    //looking for processor that has load<=upperBound
                    Processor available = findAvailable(cur);

                    //if found add one process to it
                    if (available != null) {
                        available.addProcess(toAddNow.removeFirst());
                    }
                    //if not found add processes to cur processor
                    else{
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
    }
}
