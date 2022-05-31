package Zadanie4PrzydzialRamek;

import Zadanie3ZastepowanieStron.Algorithm.LRU;
import Zadanie3ZastepowanieStron.Algorithm.PageReplacementAlgorithm;
import Zadanie3ZastepowanieStron.Page;
import Zadanie3ZastepowanieStron.Process;

import java.util.ArrayList;

public abstract class Simulation {
    protected final PageReplacementAlgorithm pageReplacementAlgorithm = new LRU();
    protected int numberOfFaults;

    protected ArrayList<Process> processes;
    protected ArrayList<Page> unitedReferences;

    protected int numberOfFrames;

    public Simulation(ArrayList<Process> processes, int numberOfFrames, ArrayList<Page> unitedReferences) {
        this.numberOfFrames = numberOfFrames;
        this.processes = processes;
        this.unitedReferences = unitedReferences;
        numberOfFaults = 0;
    }

    public abstract int run();

    protected void copyUnitedReferences() {
        ArrayList<Page> copy = new ArrayList<>(unitedReferences.size());
        for (int i = 0; i < unitedReferences.size(); i++) {
            int processId = unitedReferences.get(i).getProcessId();
            int pageId = unitedReferences.get(i).getPageId();
            copy.add(new Page(processId, pageId));
        }

        unitedReferences = copy;
    }
}
