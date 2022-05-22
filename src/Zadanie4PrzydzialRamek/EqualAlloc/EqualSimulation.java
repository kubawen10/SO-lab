package Zadanie4PrzydzialRamek.EqualAlloc;

import Zadanie3ZastepowanieStron.Memory;
import Zadanie3ZastepowanieStron.Page;
import Zadanie4PrzydzialRamek.Simulation;

import java.util.ArrayList;
import java.util.List;

public class EqualSimulation extends Simulation {
    private final EqualAlgorithm algorithm = new EqualAlgorithm();
    private ArrayList<Memory> memories;

    public EqualSimulation(List<Integer> numOfPagesOfEachProcess, int numberOfFrames, int referencesForEachProcess, int localReferencesChance) {
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
        int numberOfFramesForEachProcess = algorithm.giveFrames(numberOfFrames, processes.size());

        for (int i = 0; i < processes.size(); i++) {
            memories.add(new Memory(numberOfFramesForEachProcess));
        }
    }
}
