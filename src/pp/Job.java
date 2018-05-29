package pp;

import java.util.ArrayList;

/**
 *
 * @author Osveron
 */
public class Job {

    private final ArrayList<Job> depends;
    private final long executionTime;
    private final String ID;
    private int level;
    private final ArrayList<String> requirements;

    public Job(ArrayList<Job> d, long e, String id) {
        this.depends = d;
        this.executionTime = e;
        this.ID = id;
        this.requirements=new ArrayList<>();
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    
    public String getID() {
        return ID;
    }

    public ArrayList<Job> getDepends() {
        return this.depends;
    }
    
    public ArrayList<String> getDependsName(){
        ArrayList<String> dependsName = new ArrayList<>();
        for(Job j : this.depends)
        {
        dependsName.add(j.getID());
        }
        return dependsName;
    }

    public long getExecutionTime() {
        return this.executionTime;
    }

    public void addDependency(Job job) {
        depends.add(job);
    }

    public void removeDependency(Job job) {
        depends.remove(job);
    }
    
    public void addRequirement(String property){
        requirements.add(property);
    }
    
    public void removeRequirement(String property){
        requirements.remove(property);
    }
    
    public ArrayList<String> getRequirements(){
        return requirements;
    }

}
