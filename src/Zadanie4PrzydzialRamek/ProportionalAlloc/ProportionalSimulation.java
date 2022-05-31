package Zadanie4PrzydzialRamek.ProportionalAlloc;

import Zadanie3ZastepowanieStron.Memory;
import Zadanie3ZastepowanieStron.Page;
import Zadanie3ZastepowanieStron.Process;
import Zadanie4PrzydzialRamek.Simulation;

import java.util.ArrayList;

public class ProportionalSimulation extends Simulation {
    private final ProportionalAlgorithm algorithm = new ProportionalAlgorithm();
    private ArrayList<Memory> memories;

    public ProportionalSimulation(ArrayList<Process> processes, int numberOfFrames, ArrayList<Page> unitedReferences) {
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

            if(!curMemory.pageInMemory(curPage)){
                numberOfFaults++;
                int indexForPage = pageReplacementAlgorithm.insertPageIndex(curMemory, unitedReferences, i);
                curMemory.setFrame(indexForPage, curPage);
            }
        }
        return numberOfFaults;
    }

    private void initializeMemories(){
        memories = new ArrayList<>(processes.size());
        int curProcessFramesAlloc;
        int sumOfPages = 0;

        for (int i = 0; i < processes.size(); i++) {
            sumOfPages+= processes.get(i).getNumberOfPages();
        }

        for (int i = 0; i < processes.size(); i++) {
            int numOfCurProcessPages  = processes.get(i).getNumberOfPages();
            curProcessFramesAlloc = algorithm.giveFrames(numOfCurProcessPages, sumOfPages, numberOfFrames);
            memories.add(new Memory(curProcessFramesAlloc));
        }
    }
}
