import java.util.*;

public class Graph implements Cloneable {
	public Graph()
	{
		nodes = new LinkedList<Node>();
	}
	
	private List<Node> nodes;
	
	public Node getNode(int id)
	{
		for (Node n : nodes)
		{
			if (n.Id == id)
				return n;
		}
		return null;
	}
	
	public List<Node> getAllNodes()
	{
		return nodes;
	}
	
	public void addNode(Node node)
	{
		for (Node n : nodes)
		{
			if (n.Id == node.Id)
				return;
		}
		nodes.add(node);
	}
	
	public int getTotalEdgeCount()
	{
		int total = 0;
		for (Node n : nodes)
		{
			total = total + n.getAdjacentNodes().size();
		}
		
		return total/2;//as the graph is bidirectional
	}
	
	public Graph clone()
	{
		Graph clone = new Graph();
		for (Node n : nodes)
		{
			Node newNode = new Node(n.Id);
			newNode.SaturationDegree = n.SaturationDegree;
			newNode.Color = n.Color;
					
			clone.addNode(newNode);
		}
		
		for (Node n : nodes)
		{
			Node newNode = clone.getNode(n.Id);
			for (Node adjNode : n.getAdjacentNodes())
			{
				Node newAdjNode = clone.getNode(adjNode.Id);
				newNode.addConnection(newAdjNode);
			}
		}
		
		return clone;
	}
	
	public int findMaxColorsUsed()
	{
		int color = 0;
		for (Node n : nodes)
		{
			for (Node adj : n.getAdjacentNodes())
			{
				if (adj.Color > color)
				{
					color = adj.Color;
				}
			}
		}
		
		return color;
	}
	
}
