package Zadanie2PlanowanieDostepuDoDysku.Simulations;

import Zadanie2PlanowanieDostepuDoDysku.RealTimeTask;
import Zadanie2PlanowanieDostepuDoDysku.Task;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.Algorithm;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.FCFSAlgorithm;

import java.util.LinkedList;

public class FCFS extends Simulation {
    private LinkedList<Task> queue = new LinkedList<>();

    public FCFS(int discSize, LinkedList<Task> tasks) {
        super(discSize, tasks);
        taskChoosingAlgorithm = new FCFSAlgorithm();
    }

    public FCFS(int discSize, LinkedList<Task> tasks, Algorithm realTimeAlgorithm) {
        super(discSize, tasks);
        taskChoosingAlgorithm = new FCFSAlgorithm(realTimeAlgorithm);
    }

    @Override
    public void run() {
        int realTimeIndex;
        int goToIndex;

        while (!disc.isEmpty() || !tasksToAdd.isEmpty()) {
            //adding new tasks
            addNewTasks();
            printDisc();

            //checking if there is a realTime task
            realTimeIndex = taskChoosingAlgorithm.chooseTask(currentHeadIndex, disc);

            System.out.print("Real time index: " + realTimeIndex);

            if(realTimeIndex!=-1){
                goToIndex = realTimeIndex;
            }
            else{
                if(!queue.isEmpty()){
                    goToIndex = queue.getFirst().getIndex();
                }else{
                    goToIndex=-1;
                }
            }
            System.out.print(" Go to index: " + goToIndex);

            moveHeadTowardsDesiredDirection(goToIndex);

            System.out.println(" Moved to: " + currentHeadIndex);

            //reached desired index
            if (goToIndex == currentHeadIndex) {
                Task currentlyServedTask;

                taskChoosingAlgorithm.setLastTaskIndex(-1);

                //if there are realTime tasks, pick one at current index
                if(realTimeIndex != -1){
                    currentlyServedTask = disc.getRealTimeAtIndex(currentHeadIndex);
                    queue.remove(currentlyServedTask);
                }
                //there are no realTime tasks, get first from the queue
                else{
                    currentlyServedTask = queue.removeFirst();
                }

                System.out.println(" Currently served task: " + currentlyServedTask);
                //save wait time, remove from tasks array
                serveTaskAtCurrentIndex(currentlyServedTask);
            }

            time++;
        }
    }

    @Override
    protected void addNewTasks() {
        Task currentTaskToAdd;

        do {
            if (!tasksToAdd.isEmpty()) {
                currentTaskToAdd = tasksToAdd.getFirst();
            } else return;

            if (currentTaskToAdd.getCreationTime() <= time) {
                tasksToAdd.removeFirst();

                if (currentTaskToAdd.isRealTime()) {
                    disc.addRealTimeTask((RealTimeTask) currentTaskToAdd);
                }

                disc.addTask(currentTaskToAdd);

                //easier to find this way, but not for other algorithms
                queue.addLast(currentTaskToAdd);
            }
        } while (currentTaskToAdd.getCreationTime() <= time);
    }
}
