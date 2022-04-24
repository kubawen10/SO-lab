package Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms;

import Zadanie2PlanowanieDostepuDoDysku.Disc;
import Zadanie2PlanowanieDostepuDoDysku.Task.Task;

public class SSTFAlgorithm extends Algorithm {
    public SSTFAlgorithm() {
    }

    public SSTFAlgorithm(Algorithm realTimeAlgorithm) {
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

        int i = currentHeadIndex;
        int j = currentHeadIndex;


        if (currentHeadIndex == lastTaskIndex || (lastTaskIndex!=-1 && disc.getQueueAtIndex(lastTaskIndex).isEmpty())) {
            lastTaskIndex = -1;
        }

        if (lastTaskIndex == -1) {
            while (i >= 0 || j < disc.getSize()) {
                if (i>=0 && !disc.getQueueAtIndex(i).isEmpty()) {
                    returnTask = disc.getQueueAtIndex(i).getFirst();
                    lastTaskIndex = i;
                    return returnTask;
                }

                if (j < disc.getSize() &&!disc.getQueueAtIndex(j).isEmpty()) {
                    returnTask = disc.getQueueAtIndex(j).getFirst();
                    lastTaskIndex = j;
                    return returnTask;
                }

                i--;
                j++;
            }
        }else {
            return disc.getQueueAtIndex(lastTaskIndex).getFirst();
        }

        return null;
    }

    @Override
    public String toString() {
        return "SSTF + " + realTimeAlgorithm;
    }
}
