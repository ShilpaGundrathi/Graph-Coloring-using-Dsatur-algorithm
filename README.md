# Graph-Coloring-using-Dsatur-algorithm
This project describes efficient new heuristic methods to color the vertices of a graph which rely upon the comparison of the degrees and structure of a graph  

Dsatur Algorithm:

1)  Arrange the vertices by decreasing order of degrees.  
2)  Color a vertex of maximal degree with color 1.  
 Choose a vertex with a maximal saturation degree. If there is an equality, choose any vertex of maximal degree in the uncolored subgraph.  
3)  Color the chosen vertex with the least possible (lowest numbered)
color  
4)  If all the vertices are colored, stop. Otherwise, return to 3. 
