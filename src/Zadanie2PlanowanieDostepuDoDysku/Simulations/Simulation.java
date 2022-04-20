package Zadanie2PlanowanieDostepuDoDysku.Simulations;

import Zadanie2PlanowanieDostepuDoDysku.Disc;
import Zadanie2PlanowanieDostepuDoDysku.RealTimeTask;
import Zadanie2PlanowanieDostepuDoDysku.Task;
import Zadanie2PlanowanieDostepuDoDysku.TaskChoosingAlgorithms.Algorithm;

import java.util.LinkedList;

public abstract class Simulation {
    protected Disc disc;
    protected LinkedList<Task> tasksToAdd;

    protected int currentHeadIndex;

    protected int time = 0;
    protected int distance = 0;
    protected double waitTimeSum = 0;

    protected Algorithm taskChoosingAlgorithm;

    public Simulation(int discSize, LinkedList<Task> tasks) {
        disc = new Disc(discSize);
        tasksToAdd = tasks;
        currentHeadIndex = discSize / 2;
    }

    public abstract void run();

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
            }
        } while (currentTaskToAdd.getCreationTime() <= time);
    }

    protected void serveCurrentIndex() {
        LinkedList<Task> queue = disc.getQueueAtIndex(currentHeadIndex);
        Task serveTask;
        while (!queue.isEmpty()) {
            serveTask = disc.removeFirstAtIndex(currentHeadIndex);
            waitTimeSum += calculateWaitTime(serveTask);
        }
    }

    protected void serveTaskAtCurrentIndex(Task t){
        disc.removeTaskAtIndex(t, currentHeadIndex);

        waitTimeSum += calculateWaitTime(t);
    }

    protected void moveHeadTowardsDesiredDirection(int goTo){
        if(goTo == -1 || goTo == currentHeadIndex) return;

        if(goTo < currentHeadIndex) currentHeadIndex--;
        else currentHeadIndex++;

        distance++;
    }

    protected int calculateWaitTime(Task t){
        return time - t.getCreationTime();
    }

    public void printDisc(){
        StringBuilder s = new StringBuilder("{");
        for (int i = 0; i < disc.getSize(); i++) {
            s.append(i + "[");
            for(Task t: disc.getQueueAtIndex(i)){
                s.append(t).append(", ");
            }

            s.append("], ");
        }



        s.delete(s.length()-2, s.length());
        s.append("} ");
        s.append(currentHeadIndex + "     ");
        s.append(disc.getRealTimeTasks());
        System.out.println(s);
    }
}
