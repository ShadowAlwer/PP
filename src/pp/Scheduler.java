package pp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author Alex
 */
public class Scheduler {

    private final ArrayList<Machine> machines;
    private final ArrayList<Job> jobs;

    public void Algorithm1() {
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
                if (!queue.contains(j)) {
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
                    scheduledJobs.add(new ScheduledJob(j, tmp.getEndTime()));
                }
            }
        }
    }

    public void Algorithm2() {
        machines.stream().forEach((machine) -> machine.getWorkQueue().clear());
        ArrayList<Job> queue = new ArrayList<>();
        ArrayList<ScheduledJob> scheduledJobs = new ArrayList<>();

        int level = 0;
        for (Job j : jobs) {
            if (j.getDepends().isEmpty()) {
                j.setLevel(level);
                queue.add(j);
            }
        }

        level++;
        while (queue.size() < jobs.size()) {

            for (Job j : jobs) {
                boolean toQueue = true;
                if (!queue.contains(j)) {
                    if (!j.getDepends().isEmpty()) {
                        for (Job dep : j.getDepends()) {
                            if (!queue.contains(dep)) {
                                toQueue = false;
                                break;
                            }
                        }
                        if (toQueue) {
                            j.setLevel(level);
                            queue.add(j);
                        }
                    }
                }
                level++;
            }
        }
        for (int i = 0; i <= level; i++) {
            ArrayList<Job> jobsOnLevel = new ArrayList<>();
            for (Job job : queue) {
                if (job.getLevel() == i) {
                    jobsOnLevel.add(job);
                }
            }
            Comparator<? super Job> c = (Job o1, Job o2) -> Long.valueOf(o2.getExecutionTime()).compareTo(Long.valueOf(o1.getExecutionTime()));
            jobsOnLevel.sort(c);

            Machine tmp;
            long endTime;
            for (Job j : jobsOnLevel) {

                if (j.getDepends().isEmpty()) {
                    tmp = getTheLaziestMachine();
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
                    tmp = getTheLaziestMachine();
                    if (endTime < tmp.getEndTime()) {
                        endTime = tmp.getEndTime();
                    }
                    tmp.addTask(j, endTime);
                    scheduledJobs.add(new ScheduledJob(j, tmp.getEndTime()));
                }

            }
        }
        System.out.println("2 algorytm");

    }

    public void Algorithm3() {
        // TO DO
        machines.stream().forEach((machine) -> machine.getWorkQueue().clear());
        // zerowanie leveli
        for (Job j : jobs) {
            j.setLevel(0);
        }

        ArrayList<Job> queue = new ArrayList<>();
        ArrayList<ScheduledJob> scheduledJobs = new ArrayList<>();
        // ArrayList<Job> eliminated = new ArrayList<>();
        ArrayList<Job> checkedJobs = new ArrayList<>();

        int level = 0;
        for (Job j : jobs) {
            if (j.getDepends().isEmpty()) {
                j.setLevel(level);
                queue.add(j);
                checkedJobs.add(j);
            }
        }

        //set level for each job
        for (Job j : jobs) {
            boolean flagToAdd = false;
            ArrayList<Job> depends = j.getDepends();
            ArrayList<Job> tempDepends = new ArrayList<>();
            for (Job k : depends) {
                if (checkedJobs.contains(k)) {
                    if ((j.getLevel() - 1) > k.getLevel()) {
                        //nic nie rob
                    } else {
                        // ustaw level
                        j.setLevel(k.getLevel() + 1);
                        flagToAdd = true;
                    }
                }
            }
            // czy został dany job sprawdzony, jak tak to dodaj
            if (flagToAdd) {
                checkedJobs.add(j);
                queue.add(j);
            }
            if (j.getLevel() > level) {
                level = j.getLevel();
            }
        }

        // mamy ustawione levele na poszczególnych jobach i dodane wszystko do kolejki
        //level = 3;
        for (int i = 0; i <= level; i++) {
            ArrayList<Job> jobsOnLevel = new ArrayList<>();
            for (Job job : queue) {
                if (job.getLevel() == i) {
                    jobsOnLevel.add(job);
                }
            }
            Comparator<? super Job> c = (Job o1, Job o2) -> Long.valueOf(o2.getExecutionTime()).compareTo(Long.valueOf(o1.getExecutionTime()));
            jobsOnLevel.sort(c);

            Machine tmp;
            long endTime;
            for (Job j : jobsOnLevel) {

                if (j.getDepends().isEmpty()) {
                    tmp = getTheLaziestMachine();
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
                    tmp = getTheLaziestMachine();
                    if (endTime < tmp.getEndTime()) {
                        endTime = tmp.getEndTime();
                    }
                    tmp.addTask(j, endTime);
                    scheduledJobs.add(new ScheduledJob(j, tmp.getEndTime()));
                }

            }
        }
        System.out.println("3 algorytm");
    }

    public void Algorithm4() throws Exception {
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
                if (!queue.contains(j)) {
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
        }

        int machineCount = machines.size();
        boolean foundMachine = false;
        Machine tmp = machineIter.next();
        long endTime;
        for (Job j : queue) {
            while (!foundMachine) {
                if (machineCount == 0) {
                    throw new Exception("You can't schedule this set of jobs on available machines");
                }
                if (!machineIter.hasNext()) {
                    machineIter = machines.iterator();
                }
                tmp = machineIter.next();
                machineCount--;
                if (isCapable(tmp, j)) {
                    foundMachine = true;
                }
            }

            if (j.getDepends().isEmpty()) {
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

                if (endTime < tmp.getEndTime()) {
                    endTime = tmp.getEndTime();
                }
                tmp.addTask(j, endTime);
                scheduledJobs.add(new ScheduledJob(j, tmp.getEndTime()));
            }
            foundMachine=false;
            machineCount=machines.size();
        }
    }

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

    public void setMachinesCount(int amountOfMachines) {
        machines.clear();
        for (int i = 0; i < amountOfMachines; ++i) {
            machines.add(new Machine());
        }
    }

    public ArrayList<Machine> getMachines() {
        return this.machines;
    }

    private Machine getTheLaziestMachine() {
        Comparator<? super Machine> comparator = (Machine o1, Machine o2) -> Long.valueOf(o1.getEndTime()).compareTo(Long.valueOf(o2.getEndTime()));
        return machines
                .stream()
                .min(comparator)
                .get();

    }

    private boolean isCapable(Machine machine, Job job) {

        if (job.getProperties().size() == 0) {
            return true;
        }

        if (machine.getProperties().size() == 0) {
            return false;
        }

        for (String property : job.getProperties()) {
            if (!machine.getProperties().contains(property)) {
                return false;
            }
        }

        return true;
    }

}
