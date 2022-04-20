package Zadanie2PlanowanieDostepuDoDysku;

import java.util.Objects;

public abstract class Task {
    protected final int taskId;
    protected final int creationTime;
    protected final int index;
    protected final boolean isRealTime;

    public Task(int taskId, int creationTime, int index, boolean isRealTime) {
        this.taskId = taskId;
        this.creationTime = creationTime;
        this.index = index;
        this.isRealTime = isRealTime;
    }

    public int travelTime(int fromIndex) {
        return Math.abs(fromIndex - index);
    }

    public int getCreationTime() {
        return creationTime;
    }

    public boolean isRealTime() {
        return isRealTime;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId;
    }

    @Override
    public String toString() {
        return "("+creationTime + " " + isRealTime +")";
    }
}
