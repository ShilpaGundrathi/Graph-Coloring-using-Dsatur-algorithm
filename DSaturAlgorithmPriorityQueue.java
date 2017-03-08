import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class DSaturAlgorithmPriorityQueue {
	private List<Node> m_visitedNodes;
	private GreaterThanComparer m_cmp;
	private PriorityQueue<Node> m_allNodes;

	public DSaturAlgorithmPriorityQueue() {
		// Initialize here so that heap calls don't count towards run-time.
		m_visitedNodes = new ArrayList<Node>();
		m_cmp = new GreaterThanComparer();
		m_allNodes = new PriorityQueue<Node>(m_cmp);
	}

	public void run(Graph graph, RunTimes runtime) throws Exception
	{
		int maxColors = graph.getAllNodes().size() + 1;
		
		boolean isColorAvailable[] = new boolean[maxColors+1];
        isColorAvailable[0] = false;
        for (int color = 1; color <= maxColors; ++color)
        	isColorAvailable[color] = true;
 
        m_allNodes.addAll(graph.getAllNodes());
        
        long startTime = System.nanoTime();
		// O(V)
		while (!m_allNodes.isEmpty())
		{
			// O(1)
			Node currentNode = m_allNodes.poll();
			
			List<Node> currentsAdjNodes = currentNode.getAdjacentNodes();

			// Make colors of all nodes that already have a color as un-available.
			// O(E)
        	for (Node adjNode : currentsAdjNodes)
        	{
        			isColorAvailable[adjNode.Color] = false;
        	}

        	// Find the first available color
            int colorIndex;
            for (colorIndex = 1; colorIndex <= m_allNodes.size(); colorIndex++)
            {
            	if (isColorAvailable[colorIndex])
                    break;
            }
            // Assign the first un-used color to current node.
            currentNode.Color = colorIndex;
 			
			m_visitedNodes.add(currentNode);
			
			// Increment SaturationDegree of adjacent nodes also re-sort the priority-queue.
			// O(E)
			for (Node adjNode : currentsAdjNodes)
            {
	            // Reset the values back to false for the next iteration				
                if (adjNode.Color != 0) // Same as checking !visitedNodes.contains(adjNode)
                {
                	isColorAvailable[adjNode.Color] = true;                    
    				adjNode.SaturationDegree++;

                }
                else // It is un-visited node, so order it in the priority que-ue.
                {
                	// O(log(V))
                	m_allNodes.remove(adjNode);
    				adjNode.SaturationDegree++;

                	m_allNodes.add(adjNode);
                }
                
            }
		}
		
		long endTime = System.nanoTime();

		runtime.DSaturPriorityQueueTime += (endTime - startTime);
		runtime.colorsUsed = graph.findMaxColorsUsed();
		runtime.DSaturPriorityQueuecolorsUsed= graph.findMaxColorsUsed();
//		System.out.println("\nOutput form DSaturImproved: (Using PriorityQueue, Uniform cost search over SatDegree.");
//		for (Node n : m_visitedNodes)
//		{
//			System.out.println(
//					"Node: " + n.Id
//					+ " Sat.Degree: " + n.SaturationDegree
//					+ " Color: " + n.Color);
//		}
//		System.out.print("Colors used: " + runtime.colorsUsed);
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
