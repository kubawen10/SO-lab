package Zadanie4PrzydzialRamek;

import Zadanie3ZastepowanieStron.Algorithm.LRU;
import Zadanie3ZastepowanieStron.Algorithm.PageReplacementAlgorithm;
import Zadanie3ZastepowanieStron.Page;
import Zadanie3ZastepowanieStron.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Simulation {
    protected final PageReplacementAlgorithm pageReplacementAlgorithm = new LRU();
    protected int numberOfFaults = 0;

    protected ArrayList<Process> processes;
    protected ArrayList<Page> unitedReferences;

    protected int numberOfProcesses;
    protected int numberOfFrames;
    protected int referencesForEachProcess;

    public Simulation(List<Integer> numOfPagesOfEachProcess, int numberOfFrames, int referencesForEachProcess, int localReferencesChance) {
        this.numberOfProcesses = numOfPagesOfEachProcess.size();
        this.numberOfFrames = numberOfFrames;
        this.referencesForEachProcess = referencesForEachProcess;
        processes = new ArrayList<>();

        //create processes with own references
        for (int i = 0; i < numberOfProcesses; i++) {
            Process p = new Process(i, numOfPagesOfEachProcess.get(i));
            p.generateReferences(referencesForEachProcess, localReferencesChance);
            processes.add(p);
        }

        //unite references
        referencesUnion();
    }

    private void referencesUnion() {
        unitedReferences = new ArrayList<>();
        Random r = new Random();

        //list of each process' cur reference index
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < processes.size(); i++) {
            indexes.add(0);
        }

        //track references lists of processes that are already added
        int wholeAdded = 0;
        int chooseProcess;
        int curIndex;
        boolean added;

        //while there are references that arent fully added
        while (wholeAdded < indexes.size()) {
            added = false;
            //choose random process that will add its reference
            chooseProcess = r.nextInt(processes.size());
            curIndex = indexes.get(chooseProcess);

            while (!added) {
                //if it isnt fully added
                if (curIndex < referencesForEachProcess) {
                    unitedReferences.add(processes.get(chooseProcess).getReferences().get(curIndex));
                    curIndex++;

                    //increasing current reference index
                    indexes.set(chooseProcess, curIndex);

                    //if current process' references has been fully added
                    if (curIndex == referencesForEachProcess) {
                        wholeAdded++;
                    }
                    added = true;
                } else {
                    //go to next process in list
                    chooseProcess = (chooseProcess + 1) % processes.size();
                    curIndex = indexes.get(chooseProcess);
                }
            }
        }
    }

    public abstract int run();
}
