package Zadanie3ZastepowanieStron;

import Zadanie3ZastepowanieStron.Algorithm.*;

import java.util.List;

public class Simulation {
    private PageReplacementAlgorithm algorithm;
    private Memory memory;
    private Process process;

    public Simulation(Process process, Memory memory) {
        this.process = process;
        this.memory = memory;
    }

    public Simulation(int processId, int numberOfPages, int numberOfFrames, int numberOfReferences, int localReferencesChance) {
        process = new Process(processId, numberOfPages);
        process.setReferences(process.generateReferences(numberOfReferences, localReferencesChance));

        memory = new Memory(numberOfFrames);
    }

    private int run() {
        memory.resetMemory();
        int countPageFaults = 0;

        List<Page> references = process.getReferences();
        Page curPage;

        for (int i = 0; i < references.size(); i++) {
            curPage = references.get(i);

            if (!memory.pageInMemory(curPage)) {
                countPageFaults++;

                int indexForPage = algorithm.insertPageIndex(memory, references, i);
                memory.setFrame(indexForPage, curPage);
            }
        }

        return countPageFaults;
    }

    public int runFIFO() {
        this.algorithm = new FIFO();
        return start("FIFO");
    }

    public int runOPT() {
        this.algorithm = new OPT();
        return start("OPT ");
    }

    public int runLRU() {
        this.algorithm = new LRU();
        return start("LRU ");
    }

    public int runRAND(){
        this.algorithm = new RAND();
        return start("RAND");
    }

    public int runALRU(){
        this.algorithm = new ALRU();
        return start("ALRU");
    }

    public void runAll(){
        System.out.println(this);
        runFIFO();
        runOPT();
        runLRU();
        runALRU();
        runRAND();
    }

    private int start(String algorithmName){
        int numberOfFaults = run();
        printResults(algorithmName, numberOfFaults);
        return numberOfFaults;
    }

    public void setReferences(List<Page> references) {
        process.setReferences(references);
    }

    private void printResults(String algorithmName, int numberOfFaults) {
        System.out.println(algorithmName + "\t\t faults: " + numberOfFaults);
    }

    @Override
    public String toString() {
        return "Simulation: {NumPages: " + process.getNumberOfPages() + "\t\tNumFrames: " + memory.getNumberOfFrames() +
                "\t\tNumReferences: " + process.getReferences().size() + "}";
    }
}
