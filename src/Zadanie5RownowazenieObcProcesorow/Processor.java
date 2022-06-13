package Zadanie5RownowazenieObcProcesorow;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Processor {
    LinkedList<Process> processesToComeOriginal;

    LinkedList<Process> currentlyServed;
    LinkedList<Process> processesToCome;

    private int load = 0;

    public Processor() {
        processesToComeOriginal = new LinkedList<>();
        currentlyServed = new LinkedList<>();
    }

    public LinkedList<Process> generateProcesses(int numOfProcesses, int minLoad, int maxLoad, int minTime, int maxTime, int minTimeBetween, int maxTimeBetween) {
        processesToComeOriginal = new LinkedList<>();
        Random r = new Random();
        int load = r.nextInt(maxLoad - minLoad) + minLoad;
        int time = r.nextInt(maxTime - minTime) + minTime;
        int timeBetween;

        int t = 1;
        Process p = new Process(load, time, t);
        processesToComeOriginal.addLast(p);

        for (int i = 1; i < numOfProcesses; i++) {
            load = r.nextInt(maxLoad - minLoad) + minLoad;
            time = r.nextInt(maxTime - minTime) + minTime;
            timeBetween = r.nextInt(maxTimeBetween - minTimeBetween) + minTimeBetween;
            t += timeBetween;

            p = new Process(load, time, t);
            processesToComeOriginal.addLast(p);
        }

        return processesToComeOriginal;
    }

    public void cloneProcesses() {
        processesToCome = (LinkedList<Process>) processesToComeOriginal.clone();
    }

    // set time of every process to time - 1, if its 0 remove from currently  served list
    public void serve() {
        Iterator<Process> it = currentlyServed.iterator();
        Process p;

        while (it.hasNext()) {
            p = it.next();

            if (p.serve()) {
                load -= p.getLoad();
                it.remove();
            }
        }
    }

    public LinkedList<Process> processesToAddNow(int t) {
        LinkedList<Process> returnList = new LinkedList<>();
        Process p = null;

        if (!processesToCome.isEmpty()) {
            p = processesToCome.getFirst();
        } else {
            return returnList;
        }

        while (p != null && p.getEntryTime() <= t) {
            returnList.add(p);
            processesToCome.removeFirst();

            if (!processesToCome.isEmpty()) {
                p = processesToCome.getFirst();
            } else {
                p = null;
            }
        }

        return returnList;
    }

    public Process removeBiggest() {
        if(currentlyServed.isEmpty()){
            System.out.println("halo");
            return null;
        }
        Process biggest = currentlyServed.get(0);

        Iterator<Process> it = currentlyServed.iterator();
        Process p;
        while (it.hasNext()) {
            p = it.next();

            if (p.getLoad() > biggest.getLoad()) {
                biggest = p;
            }
        }

        currentlyServed.remove(biggest);
        load -= biggest.getLoad();
        return biggest;
    }

    public void addProcess(Process p) {
        currentlyServed.addLast(p);
        load += p.getLoad();
    }

    public void addProcesses(LinkedList<Process> processes) {
        Iterator<Process> it = processes.iterator();

        while (it.hasNext()) {
            addProcess(it.next());
        }
    }

    public boolean isDone() {
        return processesToCome.isEmpty() && currentlyServed.isEmpty();
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }
}
