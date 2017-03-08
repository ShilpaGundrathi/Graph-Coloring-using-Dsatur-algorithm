import java.io.*;
import java.util.*;

public class TCSS543 {

    //main class
    public static void main(String[] args) throws IOException {
        try {

            //initialize variables
            int[] numberOfNodesList = new int[]{10,20,30,40,50,60,70,80,90,100};
            int numberOfGraphs = 1;//-- check for 100
            double[] densityList = new double[]{0.75,0.50,0.25};
            RandomGraphGenerator graphGenerator = new RandomGraphGenerator();
            StringBuilder string = new StringBuilder();
            string.append("DSaturAlgorithms with average time in micro sec (tab separated)\n");
            string.append("Graph Density").append("\t")
                    .append("NumberOfNodes").append("\t")
                    .append("DSatur Basic(μs)").append("\t")
                    //.append("DSatur ColorMemoized(μs)").append("\t")
                    .append("DSatur PriorityQ(μs)").append("\t")
                    .append("DSatur Basic Colors Used").append("\t")
                    //.append("DSatur ColorMem Colors Used ").append("\t")
                    .append("DSatur PriQueue Colors Used").append("\t")
                    .append("Average edges").append("\t")
                    .append("\n");
            //Generate graph for each density variation
            for (double density : densityList)
            {
                RunTimes[] runTimes = new RunTimes[numberOfNodesList.length];

                //generate graphs with Nodes list as per numberOfNodesList
                for (int k = 0; k < numberOfNodesList.length; ++k)
                {
                    int numberOfNodes = numberOfNodesList[k];
                    List<Graph> graphs = graphGenerator.create(numberOfNodes, density, numberOfGraphs);

                    runTimes[k] = new RunTimes();
                    RunTimes runTime = runTimes[k];
                    runTime.numberOfNodes = numberOfNodes;

                    //run algorithm for each graph that is created
                    for (int i = 0; i < numberOfGraphs; ++i)
                    {
                        Graph graph = graphs.get(i);
                        runTime.averageEdges =runTime.averageEdges +  graph.getTotalEdgeCount();
//                        System.out.println("\n\nGraph: " + (i+1) + "\t Nodes: " + numberOfNodes);
//                        printGraph(graph);
                        colorGraphsUsingAlgorithms(graph, runTime);
                    }
                    runTime.averageEdges = runTime.averageEdges/numberOfGraphs;

                    string.append(density).append("\t")
                            .append(runTime.numberOfNodes).append("\t")
                            .append((double)runTime.DSaturBasicTime / (numberOfGraphs*1000)).append("\t")
                            //.append((double)runTime.DSaturMemorizedColorTime / (numberOfGraphs*1000)).append("\t")
                            .append((double)runTime.DSaturPriorityQueueTime / (numberOfGraphs*1000)).append("\t")
                            .append(runTime.DSaturBasiccolorsUsed).append("\t")
                           // .append(runTime.DSaturMemorizedcolorsUsed).append("\t")
                            .append(runTime.colorsUsed).append("\t")
                            .append(runTime.averageEdges ).append("\t")
                            .append("\n");
                }
            }
            System.out.println("\nRun times:");
            System.out.println(string.toString());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    private static void colorGraphsUsingAlgorithms(Graph graph, RunTimes runTime) throws Exception {

        Graph[] graphs = new Graph[3];//each for different algorithm
        for (int i = 0; i < 3; ++i)
        {
            graphs[i] = graph.clone();
        }

        DSaturAlgorithmBasic dsatur = new DSaturAlgorithmBasic();
        dsatur.run(graphs[0], runTime);

        //System.out.println("Time: " + runTime.DSaturBasicTime + " nanoSec");


       // DSaturAlgorithmMemorizedColor lessNaive = new DSaturAlgorithmMemorizedColor();
       // lessNaive.run(graphs[1], runTime);
        //System.out.println("\nTime: " + runTime.DSaturMemorizedColorTime + " nanoSec");


        DSaturAlgorithmPriorityQueue improv = new DSaturAlgorithmPriorityQueue();
        improv.run(graphs[2], runTime);
        //System.out.println("\nTime: " +  runTime.DSaturPriorityQueueTime + "nanoSec");

    }


    public static void printGraph(Graph graph)
    {
        System.out.println("Node: Adjacent nodes");
        for (Node node : graph.getAllNodes()) {
            System.out.print(node.Id);

            System.out.print(": ");
            boolean first = true;
            for (Node adjNode : node.getAdjacentNodes()) {
                if (!first)
                    System.out.print(",");
                else
                    first = false;
                System.out.print(adjNode.Id);
            }

            System.out.println("");
        }

        System.out.println("");
    }

}
