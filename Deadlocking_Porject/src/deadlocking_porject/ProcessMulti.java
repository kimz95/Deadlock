
package deadlocking_porject;

public class ProcessMulti {
    int ID;
    int AllocatedRcs[] = new int [5];
    int ReqRcs[] = new int [5];
    int RunTime;
    
    public ProcessMulti(int ID){
        this.ID=ID;
        RunTime= (int) (Math.random() * 4 + 1);
        RandomizeRecources();
    }
    
    public void print(){
        
        System.out.println("Required Recources :");
        for(int i=0;i<5;i++)
            System.out.print(ReqRcs[i]+" ");
        
        System.out.println();
        
        System.out.println("Allocated Recources :");
        for(int i=0;i<5;i++)
            System.out.print(AllocatedRcs[i]+" ");
        
        System.out.println();
    }
    
    public void RandomizeRecources(){
        
        for(int i=0;i<5;i++){
            ReqRcs[i]=(int) (Math.random() * 4 + 0);
        }
        
    }
    
    public void AllocateRecources(){
        for(int i=0;i<5;i++){
            AllocatedRcs[i]+=ReqRcs[i];
        }
    }
    
    public void ReleaseRecources(int AvailableRecources[]){
        for(int i=0;i<5;i++){
            int rand=(int) (Math.random() * 3 + 0);
            
            if(AllocatedRcs[i]-rand>=0){
                AllocatedRcs[i]-=rand;
                AvailableRecources[i]+=rand;
            }
        }
    }
    
}
