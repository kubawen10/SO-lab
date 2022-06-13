package Zadanie5RownowazenieObcProcesorow;

import java.util.Objects;

public class Process {
    private int load;
    private int time;
    private int entryTime;

    public Process(int load, int time, int entryTime) {
        this.load = load;
        this.time = time;
        this.entryTime = entryTime;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(int entryTime) {
        this.entryTime = entryTime;
    }

    public boolean serve() {
        time--;

        return time == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return load == process.load && time == process.time && entryTime == process.entryTime;
    }

    @Override
    public String toString() {
        return "Process{" +
                " load=" + load +
                ", time=" + time +
                ", entryTime=" + entryTime +
                '}';
    }
}
