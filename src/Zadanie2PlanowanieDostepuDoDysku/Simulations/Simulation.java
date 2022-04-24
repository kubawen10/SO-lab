package Zadanie2PlanowanieDostepuDoDysku.Simulations;

import Zadanie2PlanowanieDostepuDoDysku.Disc;
import Zadanie2PlanowanieDostepuDoDysku.Task.RealTimeTask;
import Zadanie2PlanowanieDostepuDoDysku.Task.Task;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.Algorithm;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Simulation {
    protected Disc disc;
    protected LinkedList<Task> tasksToAdd;

    protected int currentHeadIndex;

    protected int time = 0;
    protected int distance = 0;
    protected double waitTimeSum = 0;
    protected int deletedRealTime = 0;
    protected final int tasksAmmount;

    protected Algorithm taskChoosingAlgorithm;

    public Simulation(int discSize, LinkedList<Task> tasks) {
        disc = new Disc(discSize);
        tasksToAdd = tasks;
        tasksAmmount = tasks.size();
        currentHeadIndex = discSize / 2;
    }

    public void run() {
        int goToIndex;
        Task goToTask;

        while (!(disc.isEmpty() && tasksToAdd.isEmpty())) {
            //adding new tasks
            addNewTasks();

            //choosing Task we should move towards on disc
            goToTask = taskChoosingAlgorithm.chooseTask(currentHeadIndex, disc);

            if (goToTask != null) {
                goToIndex = goToTask.getIndex();
            } else {
                goToIndex = -1;
            }

            //moving towards the task weve chosen
            boolean moved = moveHeadTowardsDesiredDirection(goToIndex);

            //if we moved increase time (reading tasks at same index doesnt cost us time)
            if (moved) {
                time++;
            }

            //
            serveTaskDependingOnIndex(goToIndex, goToTask);

            //decreasing realTime deadlines if time has passed
            if (moved) {
                decreaseRealTimeDeadline();
            }
        }
        printStatistics();
    }

    //different for SCAN algorithms
    protected abstract void serveTaskDependingOnIndex(int goToIndex, Task goToTask);

    protected abstract boolean moveHeadTowardsDesiredDirection(int goTo);

    protected void printStatistics(){
        System.out.println(taskChoosingAlgorithm);
        System.out.println("Distance: " + distance);
        System.out.println("Avg wait time: " + waitTimeSum/tasksAmmount);
        System.out.println("Deleted real time task: " + deletedRealTime);
    }

    protected void addNewTasks() {
        Task currentTaskToAdd;

        do {
            //checking if there are tasks to add
            if (!tasksToAdd.isEmpty()) {
                currentTaskToAdd = tasksToAdd.getFirst();
            } else return;

            //if tasks creation time has come, remove it from waiting tasks and add it to queue
            if (currentTaskToAdd.getCreationTime() <= time) {
                tasksToAdd.removeFirst();
                disc.addTask(currentTaskToAdd);
            }
        } while (currentTaskToAdd.getCreationTime() <= time); //do it until tasks creation time hasnt come
    }

    protected void serveCurrentIndex() {
        //serve every task at current index(used for  SCAN algorithms)

        Task serveTask;

        while (!disc.getQueueAtIndex(currentHeadIndex).isEmpty()) {
            serveTask = disc.removeFirstAtIndex(currentHeadIndex);
            waitTimeSum += calculateWaitTime(serveTask);
        }
    }

    protected void serveTask(Task t) {
        //serve given task

        disc.removeTask(t);
        waitTimeSum += calculateWaitTime(t);
    }

    protected int calculateWaitTime(Task t) {
        //used for statistics
        return time - t.getCreationTime();
    }

    protected void printDisc() {
        String s = disc.getTasks().toString();
        s += "\t\tCurrentHeadIndex: " + currentHeadIndex + "\t\t";
        s += disc.getRealTimeTasks();
        System.out.println(s);
    }

    public int getDistance() {
        return distance;
    }

    protected void decreaseRealTimeDeadline() {
        //decreasing realTimeTasks deadline values by one, happens every unit of time
        ArrayList<RealTimeTask> realTimeTasks = disc.getRealTimeTasks();

        for (int i = 0; i < realTimeTasks.size(); i++) {
            boolean delete = realTimeTasks.get(i).decreaseDeadline();
            //if tasks deadline is less than 0 then delete it
            if(delete){
                disc.removeTask(realTimeTasks.get(i));
                i--;
                deletedRealTime++;
            }
        }
    }


}
