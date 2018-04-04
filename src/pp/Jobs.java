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

  private int counter=0;
  private final DefaultGraph graph;

  public void addJob(ArrayList<String> deps, long execTime, String ID) {
    ArrayList<Job> d = new ArrayList();
    Node n=graph.addNode(ID);
    n.addAttribute("number",counter);
    counter++;
    
    if (deps != null) {
      deps.forEach((id) -> {
      d.add(graph.getNode(id).getAttribute("J"));
      graph.addEdge(id + ID, ID, id, true);
    });
    }
    
    Job job = new Job(d, execTime);
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
    //return graph.display();
  }

}
