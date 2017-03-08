import java.util.*;

public class Node {
	public Node(int id)
	{
		Id = id;
		adjacentNodes = new ArrayList<Node>();
	}
	
	public int Id;
	public int Color = 0;
	public int SaturationDegree = 0;
	
	private List<Node> adjacentNodes;
	
	public List<Node> getAdjacentNodes() {
		return adjacentNodes;
	}
	
	public void addConnection(Node node) {
		if (node.Id != Id)
		{
			adjacentNodes.add(node);
		}
	}
	
	public boolean equals(Object o)
	{
		Node other = (Node)o;
		return other != null ? other.Id == Id : false;
	}
}
