package Zadanie1;

import java.util.List;

public class TimeSimulation {
    private List<Process> processes;
    private AddBehavior ab;

    private long time = 0;

    private int currentProcessToAddIndex;

    private double sumTimeCreationToFinish = 0;
    private double sumTimeStartToFinish = 0;
    private double maxWaitTime = 0;

    private final Processor processor;
    private Process currentServedProcess;

    public TimeSimulation(List<Process> processes, AddBehavior ab) {
        this.processes = processes;
        this.ab = ab;

        processor = new Processor(ab);

        //some initial processes
        processor.addNewProcess(processes.get(0));
        processor.addNewProcess(processes.get(1));
        processor.addNewProcess(processes.get(2));
        currentProcessToAddIndex = 3;

        //setting first process as currently served
        currentServedProcess = processor.getProcess(0);
        currentServedProcess.setStartTime(time);
    }

    public void FCFS() {
        //1 time unit
        while (currentProcessToAddIndex < processes.size() || processor.getQueueSize() > 0) {
            //adding new processes to queue
            addNewProcesses();


            //decreasing currentServedProcesses length and saving data if necessary
            if (currentServedProcess.getLength() >= 1) {
                currentServedProcess.serveProcess(time);
            } else if (currentServedProcess.getLength() == 0) {
                //saving max wait time
                currentServedProcess.serveProcess(time);
                if (currentServedProcess.getStartTime() - currentServedProcess.getCreationTime() > maxWaitTime) {
                    maxWaitTime = currentServedProcess.getStartTime() - currentServedProcess.getCreationTime();
                }

                //System.out.println(currentServedProcess + " time from start to finish " + (time - currentServedProcess.getStartTime()));

                sumTimeCreationToFinish += time - currentServedProcess.getCreationTime();
                sumTimeStartToFinish += time - currentServedProcess.getStartTime();
                if (processor.getQueueSize() > 1) {
                    processor.removeProcess(0);
                    currentServedProcess = processor.getProcess(0);
                    time += 1;
                }else {
                    processor.removeProcess(0);
                }
            }
            else{
                if (processor.getQueueSize() >= 1) {
                    currentServedProcess = processor.getProcess(0);
                    time += 1;
                }
            }
            time+=1;
        }
        System.out.println("FCFS:");
        System.out.println("Max wait time: " + maxWaitTime);
        System.out.println("Average time start to finish: " + sumTimeStartToFinish / processes.size());
        System.out.println("Average time Creation to finish: " + sumTimeCreationToFinish / processes.size());
        System.out.println("Time for everything: " + time);
    }

    private void addNewProcesses() {
        while ((currentProcessToAddIndex < processes.size()) && (time >= processes.get(currentProcessToAddIndex).getCreationTime())) {
            processor.addNewProcess(processes.get(currentProcessToAddIndex));
            currentProcessToAddIndex += 1;
        }
    }
}
