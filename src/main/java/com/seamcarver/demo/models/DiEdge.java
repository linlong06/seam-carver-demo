package com.seamcarver.demo.models;

public class DiEdge {
    private final int v;
    private final int w;

    public DiEdge(int v, int w) {
        this.v = v;
        this.w = w;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public static void main(String[] args) {
    }
}