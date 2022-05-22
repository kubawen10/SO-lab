package Zadanie4PrzydzialRamek.ProportionalAlloc;

import Zadanie3ZastepowanieStron.Process;

import java.util.List;

public class ProportionalAlgorithm {
    public ProportionalAlgorithm() {
    }

    public int giveFrames(int numberOfFrames, int curProcess, List<Process> processes) {
        int sumOfPages = 0;

        int curProcessNumOfPages = processes.get(curProcess).getNumberOfPages();

        for (int i = 0; i < processes.size(); i++) {
            sumOfPages += processes.get(i).getNumberOfPages();
        }

        return (int)(((double)curProcessNumOfPages / sumOfPages) * numberOfFrames);
    }
}
