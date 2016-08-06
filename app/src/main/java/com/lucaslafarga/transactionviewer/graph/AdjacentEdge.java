package com.lucaslafarga.transactionviewer.graph;

import java.math.BigDecimal;

public class AdjacentEdge {
    private String nextVertex;
    private BigDecimal rate;

    public AdjacentEdge(String v, BigDecimal c) {
        this.nextVertex = v;
        this.rate = c;
    }

    public String getNextVertex() {
        return nextVertex;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "AdjacentEdge [nextVertex=" + nextVertex + ", rate=" + rate + "]";
    }

}
