package pp;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Alex
 */
public class Scheduler {

    class ScheduledJob {

        public Job job;
        public long endTime;

        public ScheduledJob(Job j, long end) {
            this.job = j;
            endTime = end;
        }

    };

    public void simpleAlgorithm(Jobs jobs, ArrayList<Machine> machines) {

        ArrayList<Job> queue = new ArrayList<>();
        //jobs.getJobs().toArray(queue);
        ArrayList<ScheduledJob> scheduledJobs = new ArrayList<>();
        Iterator<Machine> machineIter = machines.iterator();

        for (Job j : jobs.getJobs()) {
            if (j.getDepends().isEmpty()) {
                queue.add(j);
            }
        }

        while (queue.size() < jobs.getJobs().size()) {

            for (Job j : jobs.getJobs()) {
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
        System.out.println("Hello");

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
}
