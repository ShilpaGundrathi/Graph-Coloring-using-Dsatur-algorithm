import java.util.*;


public class DSaturAlgorithmBasic {

	public void run(Graph graph, RunTimes runtime) throws Exception
	{
		long startTime = System.nanoTime();
        List<Node> allNodes = new LinkedList<Node>();
		allNodes.addAll(graph.getAllNodes());
		int maxColors = allNodes.size() + 1;
		
		GreaterThanComparator compare = new GreaterThanComparator();
		List<Node> visitedNodes = new LinkedList<Node>();
		//O(V)
		while (!allNodes.isEmpty())
		{
			int currentNodeIndex = findIndex(allNodes, compare);
			Node currentNode = allNodes.get(currentNodeIndex);
			List<Node> currentsAdjNodes = new ArrayList<Node>();
			currentsAdjNodes.addAll(currentNode.getAdjacentNodes());
			int i;
			//O(V)
			for (i=1; i < maxColors; ++i)
			{
				// O(E)
				if (!containsValue(currentsAdjNodes, i))
				{
					break;
				}
			}
			currentNode.Color = i;
			for (Node n : currentsAdjNodes)
			{
				n.SaturationDegree++;
			}
			
			allNodes.remove(currentNodeIndex);
			visitedNodes.add(currentNode);
		}
		long endTime = System.nanoTime();
		runtime.DSaturBasicTime += (endTime - startTime);
		runtime.DSaturBasiccolorsUsed= graph.findMaxColorsUsed();
//		System.out.println("\nOutput form Brezal DSatur:");
//		for (Node n : visitedNodes)
//		{
//			System.out.println("Node: " + n.Id+ " \tSat.Degree: " + n.SaturationDegree+ "\tColor: " + n.Color);
//		}
//		System.out.print("Colors used: " + graph.findMaxColorsUsed()+ "\n");
	}
	
	public static boolean containsValue(List<Node> nodes, int color)
	{
		for (Node n : nodes)
		{
			if (n.Color == color)
			{
				return true;
			}
		}
		return false;
	}
	
	public static int findIndex(List<Node> a, Comparator<Node> comp) throws Exception{
		
		if (a.isEmpty())
			throw new Exception("");
		
		if (a.size() == 1)
			return 0;
		
		int maxIndex = 0;
		for (int i=1; i<a.size(); i++){
			if ( comp.compare(a.get(maxIndex), a.get(i)) != -1)
				maxIndex = i;
		}
		return maxIndex;
	}
	
	public static class GreaterThanComparator implements Comparator<Node>
	{
		public int compare(Node n1 , Node n2)
		{	
			int saturationCmp = Integer.compare(n2.SaturationDegree, n1.SaturationDegree);
			if (saturationCmp != 0)
			{	
				return saturationCmp;
			}
			// if both are same, then check adjacency.
			return Integer.compare(n2.getAdjacentNodes().size(), n1.getAdjacentNodes().size());
		}
		
		public boolean equals(Object o)
		{
			GreaterThanComparator c = (GreaterThanComparator)o;
			if (c == null)
				return false;
			return c == this;
		}
	}
	
}

