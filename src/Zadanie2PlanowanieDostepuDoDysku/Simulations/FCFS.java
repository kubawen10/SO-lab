package Zadanie2PlanowanieDostepuDoDysku.Simulations;

import Zadanie2PlanowanieDostepuDoDysku.Task.Task;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.Algorithm;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.FCFSAlgorithm;

import java.util.LinkedList;

public class FCFS extends Simulation {
    public FCFS(int discSize, LinkedList<Task> tasks) {
        super(discSize, tasks);
        taskChoosingAlgorithm = new FCFSAlgorithm();
    }


    public FCFS(int discSize, LinkedList<Task> tasks, Algorithm realTimeAlgorithm) {
        super(discSize, tasks);
        taskChoosingAlgorithm = new FCFSAlgorithm(realTimeAlgorithm);
    }

    @Override
    protected void serveTaskDependingOnIndex(int goToIndex, Task goToTask) {
        if (goToIndex == currentHeadIndex) {
            serveTask(goToTask);
        }
    }

    @Override
    protected boolean moveHeadTowardsDesiredDirection(int goTo) {
        if (goTo == currentHeadIndex) return false;

        if (goTo != -1) {
            if (goTo < currentHeadIndex) currentHeadIndex--;
            else currentHeadIndex++;
            distance++;
        }

        return true;
    }

}
