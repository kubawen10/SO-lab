package Zadanie1AlgorytmyPlanowania;


public class Process implements Comparable<Process> {
    private final int PID;
    private int length;
    private final long creationTime;
    private long startTime = -1;

    public Process(int PID, int length, long creationTime) {
        this.PID = PID;
        this.length = length;
        this.creationTime = creationTime;
    }

    public void serveProcess(long t) {
        if(startTime==-1){
            startTime = t;
        }
        length--;
    }

    public boolean isDone(){
        return length < 1;
    }

    public int getPID() {
        return PID;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Process id " + PID + ", length: " + length + ", creationTime: " + creationTime + ", startTime: " + startTime;
    }

    @Override
    public int compareTo(Process o) {
        return Integer.compare(getLength(), o.getLength());
    }
}
