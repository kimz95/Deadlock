package deadlocking_porject;

import java.util.*;

public class Process extends Vertex{
    private static Random gen = new Random();
    private Stack<String> required;
    private int time;               // Time to run
    
    public Process(int p, int maxResources){
        required = new Stack();
        
        self = "P" + p;
        
        int n = gen.nextInt(maxResources) + 1;             // Randomize n of required resources atleast 1
        time  = gen.nextInt(3) + 1;
        
        // Add required resources to vector
        for(int i = 0 ; i < n ; i++){
            int  index = gen.nextInt(maxResources);
            String tmp = "" + index;

            // Don't allow duplicates
            while(required.contains(tmp)){
                index = (index + 1) % maxResources;
                tmp = "" + index;
            }
            
            required.push(tmp);
        }
    }
    
    public Stack<String> getResources(){
        return required;
    }
    public void progress(){
        time--;
    }
    public boolean isRunning(){
        return required.isEmpty() && getEdges().isEmpty();
    }
    public boolean finished(){
        return time == 0;
    }
}