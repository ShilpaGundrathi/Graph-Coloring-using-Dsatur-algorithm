import java.util.*;
import java.util.Comparator;

public class DSaturAlgorithmMemorizedColor {

	public void run(Graph graph, RunTimes runtime) throws Exception
	{
		List<Node> allNodes = new LinkedList<Node>();
		allNodes.addAll(graph.getAllNodes());
		int maxColors = allNodes.size() + 1;
		
		GreaterThanComparer compare = new GreaterThanComparer();
		List<Node> visitedNodes = new ArrayList<Node>();
		
        boolean isColorAvailable[] = new boolean[maxColors + 1];
        isColorAvailable[0] = false;
        for (int color = 1; color <= allNodes.size(); ++color)
        	isColorAvailable[color] = true;
 
        long startTime = System.nanoTime();
        
		// O(v)
		while (!allNodes.isEmpty())
		{
			// O(v)
			int currentNodeIndex = findIndex(allNodes, compare);
			Node currentNode = allNodes.get(currentNodeIndex);
			
			List<Node> currentsAdjNodes = currentNode.getAdjacentNodes();

	       	// Make colors of all nodes that already have a color as un-available.
        	for (Node adjNode : currentsAdjNodes)
        	{
        		if (adjNode.Color != 0)
        			isColorAvailable[adjNode.Color] = false;
        	}
        
            // Find the first available color
            int colorIndex;
            for (colorIndex = 1; colorIndex <= allNodes.size(); colorIndex++)
                if (isColorAvailable[colorIndex] == true)
                    break;
 
            // Assign the first un-used color to current node.
            currentNode.Color = colorIndex;
 
            // O(E)
            for (Node adjNode : currentsAdjNodes)
			{
				adjNode.SaturationDegree++;
				
	            // Reset the values back to false for the next iteration
				if (adjNode.Color != 0)
                    isColorAvailable[adjNode.Color] = true;
			}
			
			allNodes.remove(currentNodeIndex);
			visitedNodes.add(currentNode);
		}
		long endTime = System.nanoTime();

		runtime.DSaturMemorizedColorTime += (endTime - startTime);
		runtime.DSaturMemorizedcolorsUsed=graph.findMaxColorsUsed();
		
//		System.out.println("\nOutput form DSatur: Using lists, memorize available colors.");
//		for (Node n : visitedNodes)
//		{
//			System.out.println(
//					"Node: " + n.Id
//					+ " Sat.Degree: " + n.SaturationDegree
//					+ " Color: " + n.Color);
//		}
//
//		System.out.print("Colors used: " + graph.findMaxColorsUsed());
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
	
	public static class GreaterThanComparer implements Comparator<Node>
	{
		public int compare(Node n1 , Node n2)
		{	
			// Note to get Greater than we swapped lhs and rhs in in-built compare fn.
			int saturationCmp = Integer.compare(n2.SaturationDegree, n1.SaturationDegree);
			if (saturationCmp != 0)
			{	
				return saturationCmp;
					
			}

			return Integer.compare(n2.getAdjacentNodes().size(), n1.getAdjacentNodes().size());
		}
		
		public boolean equals(Object o)
		{
			GreaterThanComparer c = (GreaterThanComparer)o;
			if (c == null)
				return false;
			return c == this;
		}
	}
	
}

