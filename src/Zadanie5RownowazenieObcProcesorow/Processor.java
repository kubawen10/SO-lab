package Zadanie5RownowazenieObcProcesorow;

import java.util.*;

public class Processor {
    private final int id;

    private int load = 0;
    private int loadAsks = 0;
    private int migrations = 0;
    private int overload = 0;

    private LinkedList<Process> currentlyServed;

    public Processor(int id) {
        this.id = id;
        currentlyServed = new LinkedList<>();
    }

    public void reset() {
        load = 0;
        loadAsks = 0;
        migrations = 0;
        overload = 0;

        currentlyServed = new LinkedList<>();
    }

    // set time of every process to time - 1, if its 0 remove from currently  served list
    public void serve() {
        if (load > 100) {
            overload++;
        }

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

    public void addProcess(Process p) {
        currentlyServed.addLast(p);
        load += p.getLoad();
        if (p.getCpu() != id) {
            migrations++;
        }
    }

    public Process moveLast() {
        if (currentlyServed.isEmpty()) {
            System.out.println("halo");
            return null;
        }

        Process last = currentlyServed.removeLast();

        load -= last.getLoad();
        migrations++;

        return last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Processor processor = (Processor) o;
        return id == processor.id;
    }

    @Override
    public String toString() {
        return "Id: " + id + " Load: " + load;
    }

    public int getId() {
        return id;
    }

    public int getLoad() {
        loadAsks++;
        return load;
    }

    public int getLoadAsks() {
        return loadAsks;
    }

    public void lowerLoadAsks() {
        loadAsks--;
    }


    public LinkedList<Process> getCurrentlyServed() {
        return currentlyServed;
    }

    public int getMigrations() {
        return migrations;
    }

    public int getOverload() {
        return overload;
    }
}
