package Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms;

import Zadanie2PlanowanieDostepuDoDysku.Disc;
import Zadanie2PlanowanieDostepuDoDysku.Task.Task;

import java.util.LinkedList;

public class FCFSAlgorithm extends Algorithm {
    public FCFSAlgorithm() {
    }

    public FCFSAlgorithm(Algorithm realTimeAlgorithm) {
        this.realTimeAlgorithm = realTimeAlgorithm;
    }

    @Override
    public Task chooseTask(int currentHeadIndex, Disc disc) {
        if (disc.isEmpty()) {
            lastTaskIndex = -1;
            return null;
        }

        Task returnTask = null;

        if (realTimeAlgorithm != null) {
            returnTask = realTimeAlgorithm.chooseTask(currentHeadIndex, disc);

            if (returnTask != null) {
                //lastTaskIndex = returnTask.getIndex();
                return returnTask;
            }
        }

        if (currentHeadIndex == lastTaskIndex || (lastTaskIndex!=-1 && disc.getQueueAtIndex(lastTaskIndex).isEmpty())) {
            lastTaskIndex = -1;
        }

        if (lastTaskIndex == -1) {
            for (LinkedList<Task> l : disc.getTasks()) {
                if (!l.isEmpty()) {
                    Task curEarliest = l.getFirst();

                    if (returnTask == null) {
                        returnTask = curEarliest;
                    } else {
                        returnTask = curEarliest.getCreationTime() < returnTask.getCreationTime() ? curEarliest : returnTask;
                    }
                }
            }

            lastTaskIndex = returnTask.getIndex();
        } else {
            return disc.getQueueAtIndex(lastTaskIndex).getFirst();
        }

        return returnTask;
    }

    @Override
    public String toString() {
        return "FCFS + " + realTimeAlgorithm;
    }
}
