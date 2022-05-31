package Zadanie4PrzydzialRamek.EqualAlloc;

import Zadanie3ZastepowanieStron.Memory;
import Zadanie3ZastepowanieStron.Page;
import Zadanie3ZastepowanieStron.Process;
import Zadanie4PrzydzialRamek.Simulation;

import java.util.ArrayList;

public class EqualSimulation extends Simulation {
    private final EqualAlgorithm algorithm = new EqualAlgorithm();
    private ArrayList<Memory> memories;

    public EqualSimulation(ArrayList<Process> processes, int numberOfFrames, ArrayList<Page> unitedReferences) {
        super(processes, numberOfFrames, unitedReferences);
    }

    @Override
    public int run() {
        numberOfFaults = 0;
        initializeMemories();

        Page curPage;
        Memory curMemory;

        for (int i = 0; i < unitedReferences.size(); i++) {
            curPage = unitedReferences.get(i);
            int processIndex = curPage.getProcessId();
            curMemory = memories.get(processIndex);

            if (!curMemory.pageInMemory(curPage)) {
                numberOfFaults++;
                int indexForPage = pageReplacementAlgorithm.insertPageIndex(curMemory, unitedReferences, i);
                curMemory.setFrame(indexForPage, curPage);
            }
        }
        return numberOfFaults;
    }

    private void initializeMemories() {
        memories = new ArrayList<>(processes.size());
        int numberOfFramesForEachProcess = algorithm.giveFrames(numberOfFrames, processes.size());

        for (int i = 0; i < processes.size(); i++) {
            memories.add(new Memory(numberOfFramesForEachProcess));
        }
    }
}
