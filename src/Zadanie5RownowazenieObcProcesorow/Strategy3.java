package Zadanie5RownowazenieObcProcesorow;

import java.util.ArrayList;
import java.util.LinkedList;

public class Strategy3 extends Controller {
    protected int lowerBound;

    public Strategy3(ArrayList<Processor> processors, int upperBound, int numToAsk, int lowerBound) {
        super(processors, upperBound, numToAsk);
        this.lowerBound = lowerBound;
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

                while (cur.getLoad() <= upperBound && !toAddNow.isEmpty()) {
                    cur.addProcess(toAddNow.removeFirst());
                }


                while (!toAddNow.isEmpty()) {
                    //looking for processor that has load<=upperBound
                    Processor available = findAvailable(cur);

                    //if found add one process to it
                    if (available != null) {
                        available.addProcess(toAddNow.removeFirst());
                    }
                    //if not found add processes to cur processor
                    else {
                        cur.addProcesses(toAddNow);
                        toAddNow.clear();
                    }
                }

                //every 15 seconds ask for other processes
                if (cur.getLoad() <= lowerBound && t % 15 == 0) {
                    Processor p = findToTake(cur);
                    if(p!=null){
                        cur.addProcess(p.removeBiggest());
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

    private Processor findToTake(Processor p) {
        int i = 0;

        int index = random.nextInt(processors.size());

        Processor cur;
        while (i < numToAsk) {
            cur = processors.get(index);
            if (cur.equals(p)) {
                index = (index + 1) % processors.size();
                continue;
            }

            if (cur.getLoad() > upperBound) {
                return cur;
            }

            i++;
            index = (index + 1) % processors.size();
        }

        return null;
    }
}
