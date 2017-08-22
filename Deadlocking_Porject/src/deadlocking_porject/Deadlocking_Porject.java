/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deadlocking_porject;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author OSAMA
 */
public class Deadlocking_Porject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        boolean correct = false;
        System.out.println("Type 1 for Single Resource, 2 for Multi Resource");
        while(!correct){
            int x = sc.nextInt();
            if(x == 1){
                int tmp,pro,res;
        
                System.out.print("Enter number of Processes: ");
                tmp = sc.nextInt();
                pro = (tmp > 1) ? tmp : 1;
                System.out.print("Enter number of Resources: ");
                tmp = sc.nextInt();
                res = (tmp > 1) ? tmp : 1;
        
                Graph g = new Graph(pro, res);
        
                g.run();
                break;
                 }
            else if (x== 2){
                int AvailableRecources[]= {13,15,18,21,14};
                ArrayList <ProcessMulti> ProcessArr= new ArrayList();
                int NoOfProcesses=ReadInt();
        
                System.out.println("no. of processes = "+NoOfProcesses);
        
        
                for(int i=0;i<NoOfProcesses;i++)
                    ProcessArr.add(new ProcessMulti(i+1));
        
        
                while(!ProcessArr.isEmpty()){
                    boolean UselessLoop = true;
                    for(int i=0;i<ProcessArr.size();i++){
                    boolean SufficientRecources = true;
                
                    System.out.println("Process "+ ProcessArr.get(i).ID+" attempting to excute.");
                    PrintAvailableRecources(AvailableRecources);
                    ProcessArr.get(i).print();
            
                
                    for(int j=0;j<5;j++){
                        if(ProcessArr.get(i).ReqRcs[j]>AvailableRecources[j])
                            SufficientRecources = false;
                    }
                
                
                    if((ProcessArr.get(i)).RunTime==0){
                        System.out.println("Process "+ (i+1) +" is done");
                    
                    for(int j=0;j<5;j++){
                        AvailableRecources[j]+=ProcessArr.get(i).AllocatedRcs[j];
                        ProcessArr.get(i).AllocatedRcs[j]=0;
                        ProcessArr.get(i).ReqRcs[j]=0;
                    }
                    
                    ProcessArr.remove(i--);
                    UselessLoop=false;
                    }
                
                else if(SufficientRecources){
                    System.out.println("Recources successfully allocated.");
                    ProcessArr.get(i).AllocateRecources();
                    ProcessArr.get(i).ReleaseRecources(AvailableRecources);
                    
                    for(int j=0;j<5;j++){
                        AvailableRecources[j]-=ProcessArr.get(i).ReqRcs[j];
                    }
                    
                    ProcessArr.get(i).RandomizeRecources();
                    ProcessArr.get(i).RunTime--;
                    UselessLoop= false;
                }
                else{
                    System.out.println("Allocantion denied. Insufficient recources.");
                }
                
                }
            
                
            
            if(UselessLoop){
                System.out.println("DeadLock!!!!!");
                break;
             }
           
             }
             break;
        
            } 
            else{
                System.out.println("Please enter a valid number.");
            }}}
        public static void PrintAvailableRecources(int arr[]){
        
        System.out.println("Available recources :");
        
        for(int i=0;i<5;i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
    
    
    
    public static int ReadInt(){
        boolean NaN=true;
        
        System.out.println("Please enter number of processes");
        
        int NoOfProcesses=0;
        while(NaN){
                try{
                    Scanner sc= new Scanner(System.in);
                    NoOfProcesses=sc.nextInt();
                    NaN= false;
                }
                catch(java.util.InputMismatchException ex){
                    System.out.println("Please enter a NUMBER!!!!!");
                }
            }
        return NoOfProcesses;
    }
    
}
class Resource extends Vertex{
    public Resource(int i){
        self = "R" + i;
    }
    
    public boolean ready(){
        return getEdges().isEmpty();
    }
}

