import java.util.*;

public class RandomGraphGenerator {

	public List<Graph> create(int numOfNodes, double d, int numberOfGraphs) throws Exception
	{
		Random random=new Random();
		List<Graph> graphs = new ArrayList<Graph>();
		if (d < 0 || d > 1.0)
			throw new Exception("Density should be between 0.0 and 1.0");
		
		for (int i = 0; i < numberOfGraphs; ++i)
		{
			graphs.add(createOneGraph(numOfNodes, d, random));
		}
		return graphs;
	}

	private Graph createOneGraph(int numberOfNodes, double density, Random random) throws Exception {

		Graph graph = new Graph(); 
		for (int i = 1; i <= numberOfNodes; ++i)
		{
			Node newNode = new Node(i);
			graph.addNode(newNode);
		}
		
		for (int i = 1; i <= numberOfNodes; ++i)
		{
			Node node_i = graph.getNode(i);
			
			for (int j = i+1; j <= numberOfNodes; ++j)
			{
				Node node_j = graph.getNode(j);
				if (node_i == null || node_j == null)
				{
					throw new Exception("Null in Graph creation.");
				}
			
				if (random.nextDouble() < density)
				{
					// Edges are un-directed, so connect i,j and j,i both.
					node_i.addConnection(node_j);
					node_j.addConnection(node_i);
				}				
			}
			
			// If we can't add an edge randomly, then at least add one edge (but to a random node).
			if (node_i.getAdjacentNodes().isEmpty())
			{
				int otherNodeId = i;
				while (otherNodeId == i || otherNodeId == 0)
				{
					otherNodeId = random.nextInt(numberOfNodes + 1);
				}
				Node node_j = graph.getNode(otherNodeId);
				if (node_i == null || node_j == null)
				{
					throw new Exception("Null in Graph creation.");
				}
				// Edges are un-directed, so connect i,j and j,i both.
				node_i.addConnection(node_j);
				node_j.addConnection(node_i);
			}
		}

		
		return graph;
	}
}
