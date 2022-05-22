package Zadanie4PrzydzialRamek.ProportionalAlloc;

import Zadanie3ZastepowanieStron.Memory;
import Zadanie3ZastepowanieStron.Page;
import Zadanie4PrzydzialRamek.Simulation;

import java.util.ArrayList;
import java.util.List;

public class ProportionalSimulation extends Simulation {
    private final ProportionalAlgorithm algorithm = new ProportionalAlgorithm();
    private ArrayList<Memory> memories;

    public ProportionalSimulation(List<Integer> numOfPagesOfEachProcess, int numberOfFrames, int referencesForEachProcess, int localReferencesChance) {
        super(numOfPagesOfEachProcess, numberOfFrames, referencesForEachProcess, localReferencesChance);
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

            if(!curMemory.pageInMemory(curPage)){
                numberOfFaults++;
                int indexForPage = pageReplacementAlgorithm.insertPageIndex(curMemory, unitedReferences, i);
                curMemory.setFrame(indexForPage, curPage);
            }
        }
        return numberOfFaults;
    }

    private void initializeMemories(){
        memories = new ArrayList<>();

        int curProcessFramesAlloc;
        for (int i = 0; i < processes.size(); i++) {
            curProcessFramesAlloc = algorithm.giveFrames(numberOfFrames, i, processes);
            memories.add(new Memory(curProcessFramesAlloc));
        }
    }
}
