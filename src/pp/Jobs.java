package pp;

import java.util.ArrayList;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.view.Viewer;

/**
 *
 * @author Osveron
 */
public class Jobs {

    private final DefaultGraph graph;
    private final ArrayList<Job> jobs;

    public boolean addJob(ArrayList<String> deps, long execTime, String ID) {
        if (graph.getNode(ID) != null) {
            return false;
        }
        ArrayList<Job> dependencesList = new ArrayList<>();
        Node node = graph.addNode(ID);

        if (deps != null) {
            deps.forEach((id) -> {
                dependencesList.add(graph.getNode(id).getAttribute("J"));
                graph.addEdge(id + ID, id, ID, true);
            });
        }

        Job job = new Job(dependencesList, execTime, ID);
        node.setAttribute("J", job);
        node.setAttribute("ui.label", node.getId());

        dependencesList.clear();
        if (deps != null) {
            deps.forEach((id) -> {
                for (Job j : jobs) {
                    if (j.getID().equals(id)) {
                        dependencesList.add(j);
                    }
                }

            });
        }
        job = new Job(dependencesList, execTime, ID);
        jobs.add(job);

        return true;
    }

    public void removeJob(String ID) {
        Job removed = graph.removeNode(ID).getAttribute("J");

        for (Job job : jobs) {
            if (job.getID().equals(ID)) {
                jobs.remove(job);
                break;
            }
        }

        for (Node node : graph) {
            Job job = node.getAttribute("J");
            job.getDepends().remove(removed);
        }
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
        if (jobID.equals(dependencyID)) {
            return false;
        }

        Job job = graph.getNode(jobID).getAttribute("J");
        Job dependency = graph.getNode(dependencyID).getAttribute("J");
        if (job == null || dependency == null) {
            return false;
        }
        job.addDependency(dependency);

        graph.addEdge(dependencyID + jobID, dependencyID, jobID, true);

        for (Job j : jobs) {
            if (j.getID().equals(dependencyID)) {
                dependency = j;
                break;
            }
        }

        for (Job j : jobs) {
            if (j.getID().equals(jobID)) {
                j.addDependency(dependency);
                break;
            }
        }

        return true;
    }

    public void removeDependency(String jobID, String dependencyID) {
        Job job = graph.getNode(jobID).getAttribute("J");
        Job dependency = graph.getNode(dependencyID).getAttribute("J");
        job.removeDependency(dependency);

        graph.removeEdge(dependencyID + jobID);

        for (Job j : jobs) {
            if (j.getID().equals(dependencyID)) {
                dependency = j;
                break;
            }
        }

        for (Job j : jobs) {
            if (j.getID().equals(jobID)) {
                j.removeDependency(dependency);
                break;
            }
        }

    }

    public ArrayList<Job> getJobs() {
        return jobs;
    }

}
