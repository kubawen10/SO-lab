package Zadanie2PlanowanieDostepuDoDysku.Simulations;

import Zadanie2PlanowanieDostepuDoDysku.Task.Task;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.Algorithm;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.SCANAlgorithm;

import java.util.LinkedList;

public class SCAN extends Simulation {
    private int direction = 1;

    public SCAN(int discSize, LinkedList<Task> tasks) {
        super(discSize, tasks);
        taskChoosingAlgorithm = new SCANAlgorithm();
    }



    public SCAN(int discSize, LinkedList<Task> tasks, Algorithm realTimeAlgorithm) {
        super(discSize, tasks);
        taskChoosingAlgorithm = new SCANAlgorithm(realTimeAlgorithm);
    }

    @Override
    protected void serveTaskDependingOnIndex(int goToIndex, Task goToTask) {
        if (goToIndex == -1) {
            serveCurrentIndex();
        }else{
            if(goToIndex==currentHeadIndex){
                serveTask(goToTask);
            }
        }
    }

    @Override
    protected boolean moveHeadTowardsDesiredDirection(int goTo) {
        if (goTo==currentHeadIndex) return false;

        if (goTo == -1) {
            if (currentHeadIndex == disc.getSize() - 1){
                direction = -1;
            }else if(currentHeadIndex==0){
                direction = 1;
            }
            currentHeadIndex += direction;
        } else {
            if (goTo < currentHeadIndex) currentHeadIndex--;
            else currentHeadIndex++;
        }

        distance++;
        return true;
    }
}
