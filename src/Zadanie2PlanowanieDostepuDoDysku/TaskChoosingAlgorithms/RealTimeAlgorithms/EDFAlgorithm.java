package Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.RealTimeAlgorithms;

import Zadanie2PlanowanieDostepuDoDysku.Disc;
import Zadanie2PlanowanieDostepuDoDysku.Task.RealTimeTask;
import Zadanie2PlanowanieDostepuDoDysku.Task.Task;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.Algorithm;

import java.util.ArrayList;

public class EDFAlgorithm extends Algorithm {
    private RealTimeTask lastlyChosen = null;

    public EDFAlgorithm() {
    }

    @Override
    public Task chooseTask(int currentHeadIndex, Disc disc) {
        ArrayList<RealTimeTask> realTimeTasks = disc.getRealTimeTasks();

        if (realTimeTasks.isEmpty()) {
            lastTaskIndex = -1;
            lastlyChosen = null;
            return null;
        }

        if ((lastlyChosen != null && !realTimeTasks.contains(lastlyChosen)) || currentHeadIndex == lastTaskIndex) {
            lastTaskIndex = -1;
            lastlyChosen = null;
        }

        if (lastTaskIndex == -1) {
            lastlyChosen = realTimeTasks.get(0);

            for (int i = 0; i < realTimeTasks.size(); i++) {
                if (realTimeTasks.get(i).getDeadline() < lastlyChosen.getDeadline()) {
                    lastlyChosen = realTimeTasks.get(i);
                    lastTaskIndex = i;
                }
            }
        }

        return lastlyChosen;
    }

    @Override
    public String toString() {
        return "EDF";
    }
}
