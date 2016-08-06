package com.lucaslafarga.transactionviewer.graph;

import android.util.Log;

import com.lucaslafarga.transactionviewer.data.entities.ConversionRate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ConversionGraph {
    private static final String TAG = ConversionGraph.class.getName();

    // A Map is used to map each vertex to its list of adjacent vertices
    private Map<String, List<AdjacentEdge>> neighbours = new HashMap<>();

    public ConversionGraph(List<ConversionRate> rates) {
        for (ConversionRate rate : rates) {
            addVertex(rate.from, rate.to, rate.rate);
        }

        Log.d(TAG, "The n of vertices is: " + getNeighbours().keySet().size());
        Log.d(TAG, "The current graph: " + this.toString());
    }

    private Map<String, List<AdjacentEdge>> getNeighbours() {
        return neighbours;
    }

    // Add a vertex to the graph. Nothing happens if vertex is already in graph.
    private void addVertex(String vertex) {
        if (neighbours.containsKey(vertex))
            return;
        neighbours.put(vertex, new ArrayList<AdjacentEdge>());
    }

    // Add an edge to the graph; if either vertex does not exist, it's added.
    // This implementation allows the creation of multi-edges and self-loops.
    public void addVertex(String from, String to, BigDecimal cost) {
        addVertex(from);
        addVertex(to);
        neighbours.get(from).add(new AdjacentEdge(to, cost));
    }

    public BigDecimal getRate(String from, String to) {
        Log.d(TAG, "getRate from " + from + " to " + to);

        if (from.equals(to)) {
            // No need to convert
            return BigDecimal.ONE;
        }

        Queue<Edge> queue = new LinkedList<>();
        List<Edge> path = new ArrayList<>();

        List<String> visitedVertices = new ArrayList<>();

        Edge source = new Edge(from, null, BigDecimal.ONE);
        queue.add(source);
        path.add(source);

        while (!queue.isEmpty()) {
            Edge currentEdge = queue.remove();
            visitedVertices.add(currentEdge.targetVertex);

            if (currentEdge.targetVertex.equals(to)) {
                return getRateFromPath(path, currentEdge);
            } else {
                for (AdjacentEdge e : neighbours.get(currentEdge.targetVertex)) {
                    if (!visitedVertices.contains(e.getNextVertex())) {
                        queue.add(new Edge(e.getNextVertex(), currentEdge.targetVertex, e.getRate()));
                        path.add(new Edge(e.getNextVertex(), currentEdge.targetVertex, e.getRate()));
                    }
                }
            }
        }

        throw new RuntimeException("No conversion rate available from " + from + " to " + to);
    }

    private BigDecimal getRateFromPath(List<Edge> path, Edge to) {
        BigDecimal rate = BigDecimal.ONE;
        List<Edge> shortestPath = new ArrayList<>();

        Edge currentEdge = to;

        // Trace the path backwards, finding previous vertex
        while (currentEdge.previousVertex != null) {
            shortestPath.add(currentEdge);
            currentEdge = findPrevious(path, currentEdge);
        }

        // Add source vertex too (the one with null previousVertex)
        shortestPath.add(currentEdge);

        Collections.reverse(shortestPath);

        for (Edge edge : shortestPath) {
            rate = rate.multiply(edge.rate);
        }

        return rate;
    }

    private Edge findPrevious(List<Edge> path, Edge current) {
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i).targetVertex.equals(current.previousVertex))
                return path.get(i);
        }

        throw new RuntimeException("Previous vertex not found");
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        for (String string : neighbours.keySet())
            s.append("\n    " + string + " -> " + neighbours.get(string));
        return s.toString();
    }
}