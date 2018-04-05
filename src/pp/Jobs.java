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

  public void addJob(ArrayList<String> deps, long execTime, String ID) {
    ArrayList<Job> dependencesList = new ArrayList();
    Node node = graph.addNode(ID);

    if (deps != null) {
      deps.forEach((id) -> {
        dependencesList.add(graph.getNode(id).getAttribute("J"));
        graph.addEdge(id + ID, id, ID, true);
      });
    }

    Job job = new Job(dependencesList, execTime);
    node.setAttribute("J", job);
    node.setAttribute("ui.label", node.getId());

  }

  public DefaultGraph getGraph() {
    return this.graph;
  }

  public Jobs() {
    this.graph = new DefaultGraph("Graph");
    graph.setStrict(false);
  }

  public Viewer getViewer() {
    return new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
  }

    void removeJob(String string) {
        
    }

    void addEdge(String string, String string0) {
       
    }

    void removeEdge(String string, String string0) {
       
    }

}
