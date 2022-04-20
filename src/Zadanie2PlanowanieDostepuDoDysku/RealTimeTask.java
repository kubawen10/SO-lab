package Zadanie2PlanowanieDostepuDoDysku;

public class RealTimeTask extends Task {
    private int deadline;

    public RealTimeTask(int taskId, int creationTime, int index, int deadline) {
        super(taskId, creationTime, index, true);
        this.deadline = deadline;
    }

    public boolean decreaseDeadline() {
        if (deadline >= 0) {
            deadline--;
        }

        return deadline < 0;
    }

    public int getDeadline(){
        return deadline;
    }

    @Override
    public String toString() {
        return "("+creationTime + " " + isRealTime + " "+ deadline + ")";
    }
}
