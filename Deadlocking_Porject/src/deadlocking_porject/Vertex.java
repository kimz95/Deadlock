package deadlocking_porject;

import java.util.Vector;

public class Vertex{
    protected String        self;       // Name of node (Pn, Rn)
    private Vector<Vertex>  edges;      // Requests and Grants
    
    public Vertex(){
        edges = new Vector();
    }
    
    public void print(){
        System.out.print(self + " : ");
        System.out.print("{ ");
        for(Vertex v : edges)
            System.out.format("%s,", v);
        System.out.println("\b }");
    }
    
    public Vector<Vertex> getEdges(){
        return edges;
    }
    public void addEdge(Vertex v){
        if(!edges.contains(v))
            edges.add(v);
    }
    public void removeEdge(Vertex v){
        edges.remove(v);
    }
    public String toString(){
        return self;
    }
}
