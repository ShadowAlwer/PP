package pp;

import java.util.ArrayList;
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

}
