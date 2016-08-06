package com.lucaslafarga.transactionviewer.graph;

import java.math.BigDecimal;

public class Edge {
    public String targetVertex;
    public String previousVertex;
    public BigDecimal rate;

    public Edge(String targetVertex, String previousVertex, BigDecimal rate) {
        this.targetVertex = targetVertex;
        this.previousVertex = previousVertex;
        this.rate = rate;
    }
}
