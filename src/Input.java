import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class Input {
    private int head;
    private int[] requests;
    private boolean goingLeft;

    private SchedulingStrategy strat;

    //constructors
    Input(){        
        Random rand = new Random();
        
        head = rand.nextInt(200);

        requests = new int[rand.nextInt(40)+1];
        for(int i=0; i<requests.length; i++){
            requests[i] = rand.nextInt(200);
        }
                
        goingLeft = rand.nextInt(2) == 0;
    }
    Input(File file) throws IllegalArgumentException{
        try(BufferedReader br = new BufferedReader(new FileReader(file));){
            //head
            head = Integer.parseInt(br.readLine());
            if(head < 0 || head > 199){
                throw new IllegalArgumentException("Initial position must be an integer between 0 and 199!");
            }
            
            //request queue
            String requestString = br.readLine();
            String[] tokenizedRequests = requestString.split(",");
            requests = new int[tokenizedRequests.length];
            for(int i=0; i<tokenizedRequests.length;i++){
                requests[i] = Integer.parseInt(tokenizedRequests[i]);
                if(requests[i] < 0 || requests[i] > 199){
                    throw new IllegalArgumentException("Requested cylinders must be an integer between 0 and 199");
                }
            }

            //direction
            String directionString = br.readLine();
            if(directionString.equalsIgnoreCase("TRUE")){
                goingLeft = true;
            }else if(directionString.equalsIgnoreCase("FALSE")){
                goingLeft = false;
            }
            else{
                throw new IllegalArgumentException("Initial direction must be either 'TRUE' or 'FALSE'");
            }

        }catch (IOException e) {
            throw new IllegalArgumentException("Text file is not a valid input!");
        }
    }
    Input(int head, int[] requests, boolean goingLeft){
        this.head = head;
        this.requests = requests;
        this.goingLeft = goingLeft;
    }

    //getters
    int getHead(){
        return head;
    }    
    int[] getRequests(){
        return requests;
    }
    boolean isGoingLeft(){
        return goingLeft;
    }
    
    void setStrategy(SchedulingStrategy strat){
        this.strat = strat;
    }
    int[] getPath(int head, boolean direction){
        int[] output = strat.getPath(head, requests, direction);
        // System.out.println("PATH");
        // printArray(output);
        return output;
    }
    int[] getSchedule(int head, boolean direction){
        int[] output = strat.getSchedule(head, requests, direction);
        // System.out.println("SCHED");
        // printArray(output);
        return output;
    }

    String getRequestsAsString(int[] requestArray){
        if(requestArray.length == 0)
            return null;

        String requestString = requestArray[0]+"";
        for(int i = 1; i < requestArray.length; i++){
            requestString += ", "+requestArray[i]; 
        }
        return requestString;
    }

}
