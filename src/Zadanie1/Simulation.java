package Zadanie1;

import java.util.List;

public abstract class Simulation {
    public List<Process> processes;

    public long time = 1;

    //used to handle adding processes that are not yet added to the queue
    public int currentProcessToAddIndex = 0;

    public double sumTimeCreationToFinish = 0;
    public double sumTimeStartToFinish = 0;
    public double sumTimeCreationToStart = 0;
    public double maxWaitTime = 0;
    public double numberOfProcessSwitches = 0;
    public int numberOfStarvedProcesses = 0;

    public final Processor processor;
    public Process currentServedProcess;


    public Simulation(List<Process> processes, AddBehavior ab) {
        this.processes = processes;

        processor = new Processor(ab);

        //some initial processes
        addNewProcesses();

        //setting first process as currently served, there will always be one
        currentServedProcess = processor.getProcess(0);
    }

    public void addNewProcesses() {
        while ((currentProcessToAddIndex < processes.size()) && (time >= processes.get(currentProcessToAddIndex).getCreationTime())) {
            processor.addNewProcess(processes.get(currentProcessToAddIndex));
            currentProcessToAddIndex += 1;
        }
    }

    public abstract void run();

    public void saveValues(){
        //saving max wait time
        if (currentServedProcess.getStartTime() - currentServedProcess.getCreationTime() > maxWaitTime) {
            maxWaitTime = currentServedProcess.getStartTime() - currentServedProcess.getCreationTime();
        }
        if(currentServedProcess.getStartTime() - currentServedProcess.getCreationTime()>100000){
            numberOfStarvedProcesses++;
        }

        sumTimeCreationToFinish += time - currentServedProcess.getCreationTime();
        sumTimeStartToFinish += time - currentServedProcess.getStartTime();
        sumTimeCreationToStart += currentServedProcess.getStartTime() - currentServedProcess.getCreationTime();
    }

    public void displayData(String algorithm){
        System.out.println(algorithm);
        System.out.println("Max wait time: " + maxWaitTime);
        System.out.println("Average time start - finish: " + sumTimeStartToFinish / processes.size());
        System.out.println("Average time creation - finish: " + sumTimeCreationToFinish / processes.size());
        System.out.println("Average time creation - start: " + sumTimeCreationToStart / processes.size());
        System.out.println("Number of process switches: " + numberOfProcessSwitches);
        System.out.println("Number of starved processes: " + numberOfStarvedProcesses);
        System.out.println("Time for everything: " + time);

    }
}
