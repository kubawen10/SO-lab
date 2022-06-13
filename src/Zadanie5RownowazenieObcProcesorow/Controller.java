package Zadanie5RownowazenieObcProcesorow;

import java.util.ArrayList;
import java.util.Random;

public abstract class Controller {
    protected int upperBound;
    protected int numToAsk;

    protected Random random = new Random();
    protected ArrayList<Processor> processors;
    protected int t = 1;

    public Controller(ArrayList<Processor> processors, int upperBound, int ask) {
        this.processors = processors;
    }

    protected void cloneProcesses(){
        for (int i = 0; i < processors.size(); i++) {
            processors.get(i).cloneProcesses();
        }
    }

    protected boolean done(){
        for (int i = 0; i < processors.size(); i++) {
            if(!processors.get(i).isDone()) return false;
        }

        return true;
    }


    protected Processor findAvailable(Processor p) {
        int i = 0;

        int index = random.nextInt(processors.size());

        Processor cur;
        while (i < numToAsk) {
            cur = processors.get(index);
            if (cur.equals(p)) {
                index = (index + 1) % processors.size();
                continue;
            }

            if (cur.getLoad() <= upperBound) {
                return cur;
            }

            i++;
            index = (index + 1) % processors.size();
        }

        return null;
    }
}
