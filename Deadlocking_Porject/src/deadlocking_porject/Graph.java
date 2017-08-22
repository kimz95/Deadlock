package deadlocking_porject;

import java.util.*;

public class Graph{
    private Vector<Vertex> vertex;
    private int processes,              // Number of Processes
                resources;              // Number of Resources
    
    public Graph(int pro, int res){
        vertex = new Vector();
        processes = pro;
        resources = res;
        
        for(int i = 0; i < pro ; i++)
            vertex.add(new Process(i, res));
        
        for(int i = 0; i < res ; i++)
            vertex.add(new Resource(i));
    }
    
    public boolean hasCycle(){
        Stack<Vertex> dfs = new Stack();
        Vector<Vertex> checked = new Vector();
        
        for(int i = 0 ; i  < processes ; i++){
            Vertex v = vertex.get(i);
            if(!checked.contains(v)){
                // Cycle is found
                if(checkEdges(v, dfs, checked)){
                    Vertex begin = dfs.pop();
                    System.out.print("Deadlocked Processes :\n\t");
                    // Print Deadlocked Processes
                    while(true){
                        Vertex end = dfs.pop();

                        if(end.toString().startsWith("P"))
                            System.out.format("%s,", end);
                        
                        if(begin == end){
                            System.out.println("\b");
                            break;
                        }
                    }
                    return true;
                }
            }
        }
        
        
        return false;
    }
    
    private boolean checkEdges(Vertex v, Stack<Vertex> s, Vector<Vertex> c){
        if(s.contains(v)){
            s.push(v);
            return true;
        }
        s.push(v);
        c.add(v);
        Vector<Vertex> edges = v.getEdges();
        for(Vertex itr : edges){
            if(checkEdges(itr, s, c))
                return true;
        }
        s.pop();
        return false;
    }
    public void print(){
        System.out.println("Allocation Graph");
        for(Vertex v : vertex){
            v.print();
        }
    }
    
    
    public void connectPR(int pn, int rn){
        if((pn >= 0 && pn < processes)
         &&(rn >= 0 && rn < resources)){
            Vertex v1 = getProcess(pn);
            Vertex v2 = getResource(rn);
            v1.addEdge(v2);
        }
    }
    public void connectRP(int rn, int pn){
        if((pn >= 0 && pn < processes)
         &&(rn >= 0 && rn < resources)){
            Vertex v1 = getProcess(pn);
            Vertex v2 = getResource(rn);
            v2.addEdge(v1);
        }
    }
    private Process getProcess(int i){
        return (Process)vertex.get(i);
    }
    private Resource getResource(int i){
        return (Resource)vertex.get(processes+i);
    }
    
    public void run(){
        boolean done = false;
        
        // Allocate one resource for each process before begining
        for(int j = 0 ; j < processes ; j++){
            Process p = getProcess(j);
            Stack<String> s = p.getResources();
            
            String str = s.pop();
            int n = Integer.parseInt(str);
            
            if(getResource(n).ready())
                connectRP(Integer.parseInt(str), Integer.parseInt(p.toString().substring(1)));
            else
                s.push(str);
        }
        
        
        
        for(int i = 0; !done ; i = (i+1) % processes){
            Process p = getProcess(i);
            if(p.finished()){
                for(int j = 0 ; j < resources ; j++)
                    getResource(j).removeEdge(p);
                continue;
            }
            if(hasCycle())
                break;
            if(p.isRunning())
                p.progress();
            else{
                if(tryAllocate(p))
                    p.progress();
            }
            
            done = true;
            for(int j = 0 ; j < processes ; j++){
                done = done && getProcess(j).finished();
            }
            
            print();
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){}
        }
        
    }
    
    private boolean tryAllocate(Process p){
        Stack<String> s = p.getResources();
        Vector<Vertex> edges = p.getEdges();
        
        // Pending Request 
        if(!edges.isEmpty()){
            Vertex v   = edges.get(0);        // Only on Request Can be pending
            Resource r = (Resource)v;
            if(r.ready()){
                p.removeEdge(v);
                connectRP(Integer.parseInt(v.toString().substring(1)), Integer.parseInt(p.toString().substring(1)));    // Acquire resource
            }else
                return false;
        }
        
        while(!s.isEmpty()){
            String str = s.pop();
            int n = Integer.parseInt(str);
            if(getResource(n).ready()){
                connectRP(Integer.parseInt(str), Integer.parseInt(p.toString().substring(1)));  // Acquire resource
            }else{
                connectPR(Integer.parseInt(p.toString().substring(1)), Integer.parseInt(str));  // Request resource
                return false;
            }
        }
        return true;        
    }
}