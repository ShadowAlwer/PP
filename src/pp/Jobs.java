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

  public void addJob(ArrayList<String> deps, long execTime, String ID) {
    ArrayList<Job> dependencesList = new ArrayList();
    graph.addNode(ID);
    
    if (deps != null) {
      deps.forEach((id) -> {
      dependencesList.add(graph.getNode(id).getAttribute("J"));
      graph.addEdge(id + ID, id, ID, true);
    });
    }
    
    Job job = new Job(dependencesList, execTime);
   graph.getNode(ID).setAttribute("J", job);
    
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

}
