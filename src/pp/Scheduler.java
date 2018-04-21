package pp;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Alex
 */
public class Scheduler {
    
    private final ArrayList<Machine> machines;
    private final ArrayList<Job> jobs;
    
    class ScheduledJob {
        
        public Job job;
        public long endTime;
        
        public ScheduledJob(Job j, long end) {
            this.job = j;
            endTime = end;
        }
        
    };
    
    public Scheduler(ArrayList<Job> jobs, int amountOfMachines) {
        this.jobs = jobs;
        machines = new ArrayList<>();
        for (int i = 0; i < amountOfMachines; ++i) {
            machines.add(new Machine());
        }
    }
    
    public void simpleAlgorithm() {
        machines.stream().forEach((machine) -> machine.getWorkQueue().clear());
        ArrayList<Job> queue = new ArrayList<>();
        ArrayList<ScheduledJob> scheduledJobs = new ArrayList<>();
        Iterator<Machine> machineIter = machines.iterator();
        
        for (Job j : jobs) {
            if (j.getDepends().isEmpty()) {
                queue.add(j);
            }
        }
        
        while (queue.size() < jobs.size()) {
            
            for (Job j : jobs) {
                boolean toQueue = true;
                if (!j.getDepends().isEmpty()) {
                    for (Job dep : j.getDepends()) {
                        if (!queue.contains(dep)) {
                            toQueue = false;
                            break;
                        }
                    }
                    if (toQueue) {
                        queue.add(j);
                    }
                }
            }
        }
        
        Machine tmp;
        long endTime;
        for (Job j : queue) {
            if (!machineIter.hasNext()) {
                machineIter = machines.iterator();
            }
            if (machineIter.hasNext()) {
                if (j.getDepends().isEmpty()) {
                    tmp = machineIter.next();
                    tmp.addTask(j, tmp.getEndTime());
                    scheduledJobs.add(new ScheduledJob(j, tmp.getEndTime()));
                } else {
                    endTime = 0;
                    for (Job dep : j.getDepends()) {
                        for (ScheduledJob sj : scheduledJobs) {
                            if (sj.job.equals(dep) && endTime < sj.endTime) {
                                endTime = sj.endTime;
                            }
                        }
                    }
                    tmp = machineIter.next();
                    if (endTime < tmp.getEndTime()) {
                        endTime = tmp.getEndTime();
                    }
                    tmp.addTask(j, endTime);
                    scheduledJobs.add(new ScheduledJob(j, endTime));
                }
            }
        }
    }
    
    public ArrayList<Machine> getMachines() {
        return this.machines;
    }
}
