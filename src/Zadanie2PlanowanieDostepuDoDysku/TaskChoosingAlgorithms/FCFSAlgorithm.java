package Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms;

import Zadanie2PlanowanieDostepuDoDysku.Disc;

public class FCFSAlgorithm extends Algorithm {
    public FCFSAlgorithm() {
    }

    public FCFSAlgorithm(Algorithm realTimeAlgorithm) {
        this.realTimeAlgorithm = realTimeAlgorithm;
    }

    @Override
    public int chooseTask(int currentHeadIndex, Disc disc) {
        if (realTimeAlgorithm != null) {
            if (lastTaskIndex == -1) {
                System.out.println("Picking new index");
                lastTaskIndex = realTimeAlgorithm.chooseTask(currentHeadIndex, disc);
            }

            if (currentHeadIndex == lastTaskIndex) {
                System.out.println("Currently on desired index");
                lastTaskIndex = -1;
                return currentHeadIndex;
            }

            return lastTaskIndex;
        }

        return -1;
    }


}
