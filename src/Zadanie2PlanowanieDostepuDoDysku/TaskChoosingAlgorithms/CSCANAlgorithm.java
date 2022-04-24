package Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms;

import Zadanie2PlanowanieDostepuDoDysku.Disc;
import Zadanie2PlanowanieDostepuDoDysku.Task.Task;

public class CSCANAlgorithm extends Algorithm {
    public CSCANAlgorithm() {
    }

    public CSCANAlgorithm(Algorithm realTimeAlgorithm) {
        this.realTimeAlgorithm = realTimeAlgorithm;
    }

    @Override
    public Task chooseTask(int currentHeadIndex, Disc disc) {
        if(disc.isEmpty()) {
            lastTaskIndex = -1;
            return null;
        }

        Task returnTask;

        if (realTimeAlgorithm != null) {
            returnTask = realTimeAlgorithm.chooseTask(currentHeadIndex, disc);
            if (returnTask != null) {
                lastTaskIndex = returnTask.getIndex();
                return returnTask;
            }
        }

        if (currentHeadIndex == lastTaskIndex) {
            lastTaskIndex = -1;
        }

        return null;
    }

    @Override
    public String toString() {
        return "CSCAN + " + realTimeAlgorithm;
    }
}
