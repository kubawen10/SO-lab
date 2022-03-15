package Zadanie1;


public class Process implements Comparable<Process> {
    private final int number;
    private int length;
    private final long creationTime;
    private long startTime = -1;

    public Process(int number, int length, long creationTime) {
        this.number = number;
        this.length = length;
        this.creationTime = creationTime;
    }

    public void serveProcess(long t) {
        if(startTime==-1){
            startTime = t;
        }
        length--;
    }

    public int getNumber() {
        return number;
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
        return "Process number " + number + ", length: " + length + ", creationTime: " + creationTime + ", startTime: " + startTime;
    }

    @Override
    public int compareTo(Process o) {
        return Integer.compare(getLength(), o.getLength());
    }
}
