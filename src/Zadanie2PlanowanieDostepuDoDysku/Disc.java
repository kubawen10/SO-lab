package Zadanie2PlanowanieDostepuDoDysku;

import Zadanie2PlanowanieDostepuDoDysku.Task.RealTimeTask;
import Zadanie2PlanowanieDostepuDoDysku.Task.Task;

import java.util.ArrayList;
import java.util.LinkedList;

public class Disc {
    private final int size;
    private ArrayList<LinkedList<Task>> tasks;

    private ArrayList<RealTimeTask> realTimeTasks;

    private int currentlyAdded = 0;

    public Disc(int size) {
        this.size = size;
        tasks = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            tasks.add(new LinkedList<>());
        }
        realTimeTasks = new ArrayList<>();
    }

    public boolean isEmpty() {
        return currentlyAdded == 0;
    }

    private void addRealTimeTask(RealTimeTask task) {
        realTimeTasks.add(task);
    }

    public void addTask(Task task) {
        if(task.isRealTime()){
            addRealTimeTask((RealTimeTask) task);
        }
        tasks.get(task.getIndex()).addLast(task);
        currentlyAdded++;
    }

    public LinkedList<Task> getQueueAtIndex(int index) {
        return tasks.get(index);
    }

    public Task removeFirstAtIndex(int index) {
        Task cur = tasks.get(index).removeFirst();

        if (cur.isRealTime()) {
            realTimeTasks.remove(cur);
        }

        currentlyAdded--;
        return cur;
    }

    public boolean removeTask(Task t) {
        if (t.isRealTime()) {
            realTimeTasks.remove(t);
        }

        currentlyAdded--;
        return tasks.get(t.getIndex()).remove(t);
    }

    public Task getRealTimeAtIndex(int index){
        for (Task t: realTimeTasks) {
            if(t.getIndex() == index){
                return t;
            }
        }
        return null;
    }

    public ArrayList<RealTimeTask> getRealTimeTasks() {
        return realTimeTasks;
    }

    public int getSize(){
        return size;
    }

    public ArrayList<LinkedList<Task>> getTasks(){
        return tasks;
    }
}
