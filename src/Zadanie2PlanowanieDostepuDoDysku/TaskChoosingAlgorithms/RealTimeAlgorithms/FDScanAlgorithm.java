package Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.RealTimeAlgorithms;

import Zadanie2PlanowanieDostepuDoDysku.Disc;
import Zadanie2PlanowanieDostepuDoDysku.Task.RealTimeTask;
import Zadanie2PlanowanieDostepuDoDysku.Task.Task;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.Algorithm;

import java.util.ArrayList;
import java.util.LinkedList;

public class FDScanAlgorithm extends Algorithm {
    private RealTimeTask lastlyChosen = null;

    public FDScanAlgorithm() {

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
            for (int i = 0; i < realTimeTasks.size(); i++) {
                if (canReach(currentHeadIndex, realTimeTasks.get(i))) {
                    lastlyChosen = choose(lastlyChosen, realTimeTasks.get(i), currentHeadIndex);
                    lastTaskIndex = lastlyChosen.getIndex();
                }
            }

            if (lastlyChosen == null) {
                return null;
            }
        }
        return findTask(currentHeadIndex, lastlyChosen.getIndex(), disc);
    }

    @Override
    public String toString() {
        return "FDScan";
    }

    private RealTimeTask choose(RealTimeTask currentMin, RealTimeTask current, int currentIndex) {
        if (currentMin == null) {
            return current;
        }

        int d1 = Math.abs(currentIndex - currentMin.getIndex());
        int d2 = Math.abs(currentIndex - current.getIndex());

        return d1 < d2 ? current : currentMin;
    }

    private boolean canReach(int currentIndex, RealTimeTask t) {
        int dist = Math.abs(currentIndex - t.getIndex());
        return t.getDeadline() - dist >= 0;
    }

    private Task findTask(int currentIndex, int goTowardsIndex, Disc disc) {
        ArrayList<LinkedList<Task>> tasks = disc.getTasks();

        if (currentIndex <= goTowardsIndex) {
            for (int i = currentIndex; i <= goTowardsIndex; i++) {
                if (!tasks.get(i).isEmpty()) {
                    return tasks.get(i).getFirst();
                }
            }
        } else {
            for (int i = currentIndex; i >= goTowardsIndex; i--) {
                if (!tasks.get(i).isEmpty()) {
                    return tasks.get(i).getFirst();
                }
            }
        }
        return null;
    }
}
