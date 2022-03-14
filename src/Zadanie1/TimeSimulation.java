package Zadanie1;

import java.util.List;

public class TimeSimulation {
    private List<Process> processes;
    private AddBehavior ab;

    private long time = 0;

    private int currentProcessToAddIndex = 3;

    private double sumTimeCreationToFinish = 0;
    private double sumTimeStartToFinish = 0;
    private double maxWaitTime = 0;

    private final Processor processor;
    private Process currentServedProcess;

    public TimeSimulation(List<Process> processes, AddBehavior ab) {
        this.ab = ab;
        this.processes = processes;

        processor = new Processor(ab);

        //some initial processes
        processor.addNewProcess(processes.get(0));
        processor.addNewProcess(processes.get(1));
        processor.addNewProcess(processes.get(2));

        //setting first process as currently served
        currentServedProcess = processor.getProcess(0);
        currentServedProcess.setStartTime(time);
    }

    private void addNewProcesses() {
        while ((currentProcessToAddIndex < processes.size()) && (time >= processes.get(currentProcessToAddIndex).getCreationTime())) {
            processor.addNewProcess(processes.get(currentProcessToAddIndex));
            currentProcessToAddIndex += 1;
        }
    }


    public void FCFS() {
        //1 time unit
        while (currentProcessToAddIndex < processes.size() || processor.getQueueSize() > 0) {
            System.out.println(currentServedProcess + "\t\ttime: " + time + "\t\t");
            System.out.println(currentProcessToAddIndex);
            processor.showQueue();

            //adding new processes to queue
            addNewProcesses();

            //decreasing and saving data
            if (currentServedProcess.getLength() > 0) {
                currentServedProcess.decreaseLength();
                if (currentServedProcess.getLength() == 0) {
                    //saving max wait time
                    if (currentServedProcess.getStartTime() - currentServedProcess.getCreationTime() > maxWaitTime) {
                        maxWaitTime = currentServedProcess.getStartTime() - currentServedProcess.getCreationTime();
                    }

                    sumTimeCreationToFinish += time - currentServedProcess.getCreationTime();
                    sumTimeStartToFinish += time - currentServedProcess.getStartTime();

                    if (processor.getQueueSize() > 1) {
                        processor.removeProcess(0);
                        currentServedProcess = processor.getProcess(0);
                        currentServedProcess.setStartTime(time);
                    }
                }
            } else {
                if (processor.getQueueSize() > 1) {
                    processor.removeProcess(0);
                    currentServedProcess = processor.getProcess(0);
                    currentServedProcess.setStartTime(time);
                } else if(processor.getQueueSize() == 1) {
                    processor.removeProcess(0);
                }
            }
            time += 1;
        }
        System.out.println("FCFS:");
        System.out.println("Max wait time: " + maxWaitTime);
        System.out.println("Average time start to finish: " + sumTimeStartToFinish / processes.size());
        System.out.println("Average time Creation to finish: " + sumTimeCreationToFinish / processes.size());
    }
}
