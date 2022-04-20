package Zadanie2PlanowanieDostepuDoDysku;

import java.util.ArrayList;
import java.util.LinkedList;

public class Disc {
    private final int size;
    private ArrayList<LinkedList<Task>> tasks;

    private LinkedList<RealTimeTask> realTimeTasks;

    private int currentlyAdded = 0;

    public Disc(int size) {
        this.size = size;
        tasks = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            tasks.add(new LinkedList<>());
        }
        realTimeTasks = new LinkedList<>();
    }

    public boolean isEmpty() {
        return currentlyAdded == 0;
    }

    public void addRealTimeTask(RealTimeTask task) {
        realTimeTasks.addLast(task);
    }

    public void addTask(Task task) {
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

    public boolean removeTaskAtIndex(Task t, int index) {
        currentlyAdded--;

        if (t.isRealTime()) {
            realTimeTasks.remove(t);
        }

        return tasks.get(index).remove(t);
    }

    public Task getRealTimeAtIndex(int index){
        for (Task t: realTimeTasks) {
            if(t.getIndex() == index){
                return t;
            }
        }
        return null;
    }

    public LinkedList<RealTimeTask> getRealTimeTasks() {
        return realTimeTasks;
    }

    public int getSize(){
        return size;
    }
}
