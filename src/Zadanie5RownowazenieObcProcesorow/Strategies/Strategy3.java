package Zadanie5RownowazenieObcProcesorow.Strategies;

import Zadanie5RownowazenieObcProcesorow.Process;
import Zadanie5RownowazenieObcProcesorow.Processor;

import java.util.ArrayList;
import java.util.LinkedList;

public class Strategy3 extends Controller {
    protected int lowerBound;

    public Strategy3(ArrayList<Processor> processors, LinkedList<Process> processes, int upperBound, int numToAsk, int lowerBound) {
        super(processors, processes, upperBound, numToAsk);
        this.lowerBound = lowerBound;
    }

    @Override
    public void run() {
        reset();

        while (!done()) {
            //System.out.println("Time: " + t);

            //adding new processes
            while (!done() && processes.getFirst().getEntryTime() <= t) {
                Process addNow = processes.removeFirst();

                Processor curProcessor = processors.get(addNow.getCpu());
                if (curProcessor.getLoad() <= upperBound) {
                    curProcessor.lowerLoadAsks();
                } else {
                    curProcessor.lowerLoadAsks();

                    //finding processor to send process to
                    Processor available = findAvailable(addNow.getCpu());
                    if (available != null) {
                        curProcessor = available;
                    }
                }
                curProcessor.addProcess(addNow);
            }

            //asking for processes
            for (int i = 0; i < processors.size(); i++) {
                Processor curProcessor = processors.get(i);

                curProcessor.lowerLoadAsks();
                if(curProcessor.getLoad() <= lowerBound){
                    Processor p = findToTake(i);
                    if(p!=null){
                        curProcessor.addProcess(p.moveLast());
                    }
                }
            }
            statCreation();

            //serving processes
            serveProcesses();
            t++;
        }

        printStatistics(3);
    }

    private Processor findToTake(int curProcessor) {
        int i = 0;
        int index = random.nextInt(processors.size());

        Processor cur;
        while (i < numToAsk) {
            if (index == curProcessor) {
                index = (index + 1) % processors.size();
            }

            cur = processors.get(index);
            if (cur.getLoad() > upperBound) {
                return cur;
            }

            i++;
            index = (index + 1) % processors.size();
        }
        return null;
    }


}
