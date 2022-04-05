package Zadanie1PlanowanieDostepuDoProcesow;

import java.util.List;

public abstract class Simulation {
    private int starveBarrier = 14000;

    public List<Process> processes;

    public long time = 1;

    //used to handle adding processes that are not yet added to the queue
    public int currentProcessToAddIndex = 0;

    //stats
    public double sumTimeCreationToFinish = 0;
    public double sumTimeStartToFinish = 0;
    public double sumTimeCreationToStart = 0;
    public double maxWaitTime = 0;
    public int numberOfProcessSwitches = 0;
    public int numberOfStarvedProcesses = 0;

    public int switchTime = 0;
    public int serveTime=0;
    public int emptyQueueTime=-1;


    public final Processor processor;
    public Process currentServedProcess;


    public Simulation(List<Process> processes, AddBehavior addBehavior) {
        this.processes = processes;

        processor = new Processor(addBehavior);

        //some initial processes
        addNewProcesses();

        //setting first process as currently served, there will always be one
        currentServedProcess = processor.getProcess(0);
    }

    public void addNewProcesses() {
        //System.out.println(currentProcessToAddIndex);
        //System.out.println(time);
        while ((currentProcessToAddIndex < processes.size()) && (time >= processes.get(currentProcessToAddIndex).getCreationTime())) {
            processor.addNewProcess(processes.get(currentProcessToAddIndex));
            currentProcessToAddIndex ++;
        }
    }

    public abstract void run();

    public void switchProcess(int index){
        currentServedProcess = processor.getProcess(index);

    }

    public void saveSwitch(){
        numberOfProcessSwitches++;
        switchTime++;

    }

    public void saveValues() {
        //saving max wait time
        if (currentServedProcess.getStartTime() - currentServedProcess.getCreationTime() > maxWaitTime) {
            maxWaitTime = currentServedProcess.getStartTime() - currentServedProcess.getCreationTime();
        }

        //starved Processes, they will always be done but when done check if they waited for longer than some time
        if (currentServedProcess.getStartTime() - currentServedProcess.getCreationTime() > starveBarrier) {
            numberOfStarvedProcesses++;
        }

        sumTimeCreationToFinish += time - currentServedProcess.getCreationTime();
        sumTimeStartToFinish += time - currentServedProcess.getStartTime();
        sumTimeCreationToStart += currentServedProcess.getStartTime() - currentServedProcess.getCreationTime();
    }

    public void setStarveBarreier(int s){
        starveBarrier=s;
    }
}
