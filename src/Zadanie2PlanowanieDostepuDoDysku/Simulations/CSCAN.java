package Zadanie2PlanowanieDostepuDoDysku.Simulations;

import Zadanie2PlanowanieDostepuDoDysku.Task.Task;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.Algorithm;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.CSCANAlgorithm;

import java.util.LinkedList;

public class CSCAN extends Simulation {
    private int headReturns = 0;

    public CSCAN(int discSize, LinkedList<Task> tasks) {
        super(discSize, tasks);
        taskChoosingAlgorithm = new CSCANAlgorithm();
    }

    public CSCAN(int discSize, LinkedList<Task> tasks, Algorithm realTimeAlgorithm) {
        super(discSize, tasks);
        taskChoosingAlgorithm = new CSCANAlgorithm(realTimeAlgorithm);
    }

    @Override
    protected void serveTaskDependingOnIndex(int goToIndex, Task goToTask) {
        if (goToIndex == -1) {
            serveCurrentIndex();
        } else {
            if (goToIndex == currentHeadIndex) {
                serveTask(goToTask);
            }
        }
    }

    @Override
    protected boolean moveHeadTowardsDesiredDirection(int goTo) {
        if (goTo == currentHeadIndex) return false;

        if (goTo == -1) {
            currentHeadIndex += 1;
            if (currentHeadIndex >= disc.getSize()) {
                currentHeadIndex = 0;
                headReturns++;
            }
        } else {
            if (goTo < currentHeadIndex) currentHeadIndex--;
            else currentHeadIndex++;
        }

        distance++;
        return true;
    }

    @Override
    protected void printStatistics() {
        super.printStatistics();
        System.out.println("Returns: " + headReturns);
    }
}
