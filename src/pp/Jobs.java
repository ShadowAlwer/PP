package pp;

import java.util.ArrayList;
import java.util.HashMap;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.view.Viewer;

/**
 *
 * @author Osveron
 */
public class Jobs {

    private final DefaultGraph graph;
    private final ArrayList<Job> jobs;

    Job findJobByID(String id) {
        return jobs.stream().filter((job)
                -> job.getID().equals(id)
        ).findFirst().get();
    }
    
    public boolean addJob(ArrayList<String> deps, long execTime, String ID) {
        if (graph.getNode(ID) != null) {
            return false;
        }
        ArrayList<Job> dependencesList = new ArrayList<>();
        graph.addNode(ID).setAttribute("ui.label", ID);

        if (deps != null) {
            deps.forEach((id) -> {
                dependencesList.add(findJobByID(id));
                graph.addEdge(id + ID, id, ID, true);
            });
        }

        jobs.add(new Job(dependencesList, execTime, ID));
        return true;
    }

    public void removeJob(String ID) {
        Job removed = findJobByID(ID);
        graph.removeNode(ID);
        jobs.remove(removed);
        jobs.forEach((job) -> job.getDepends().remove(removed));
    }

    public DefaultGraph getGraph() {
        return this.graph;
    }

    public Jobs() {
        this.graph = new DefaultGraph("Graph");
        graph.setStrict(false);
        jobs = new ArrayList<>();
    }

    public Viewer getViewer() {
        return new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
    }

    public boolean addDependency(String jobID, String dependencyID) {
        if (jobID.equals(dependencyID) || graph.getEdge(jobID + dependencyID) != null) {
            return false;
        }

        Job job = findJobByID(jobID);
        Job dependency = findJobByID(dependencyID);
        if (job == null || dependency == null) {
            return false;
        }
        job.addDependency(dependency);

        graph.addEdge(dependencyID + jobID, dependencyID, jobID, true);

        return true;
    }

    public void removeDependency(String jobID, String dependencyID) {
        Job job = findJobByID(jobID);
        Job dependency = findJobByID(dependencyID);
        job.removeDependency(dependency);
        graph.removeEdge(dependencyID + jobID);
    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }
    
    private boolean isCyclicUtil(Job job, HashMap<String, Boolean> visitedJobs) {
        if (job.getDepends() == null) {
            return false;
        }
        
        if (visitedJobs.get(job.getID()) == true) {
            return true;
        }
        
        visitedJobs.replace(job.getID(), Boolean.FALSE, Boolean.TRUE);
        
        for (Job j : job.getDepends()) {
            if (isCyclicUtil(j, visitedJobs)) {
                return true;
            }
        }
        
        visitedJobs.replace(job.getID(), Boolean.TRUE, Boolean.FALSE);
        
        return false;
    }
    
    public boolean isCyclic() {
        HashMap<String, Boolean> visitedJobs = new HashMap<>();
        jobs.forEach(j -> visitedJobs.put(j.getID(), Boolean.FALSE));
        for (Job j : jobs) {
            if (isCyclicUtil(j, visitedJobs)) {
                return true;
            }
        }
        return false;
    }
    
}
