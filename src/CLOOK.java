class CLOOK implements SchedulingStrategy{

    @Override
    public int[] getPath(int head, int[] requests, boolean direction) {
        int[] clook = new int[requests.length+1];
        int[] sortedRequests = sortArray(requests);
        clook[0] = head;
        int counter = 1;

        if(direction){
            int key = findNextSmaller(sortedRequests, head);
            for(int i = key; i >= 0; i--){
                clook[counter++] = sortedRequests[i];
            }
            if(counter-1 < sortedRequests.length){
                clook = expand(clook,2);
                clook[counter++] = 0;
                clook[counter++] = 199;
            }
            for(int i = sortedRequests.length-1; i > key; i--){
                clook[counter++] = sortedRequests[i];
            }

        }else{
            int key = findNextLarger(sortedRequests, head);
            for(int i = key; i < sortedRequests.length; i++){
                clook[counter++] = sortedRequests[i];
            }
            if(counter-1 < sortedRequests.length){
                clook = expand(clook,2);
                clook[counter++] = 199;
                clook[counter++] = 0;
            }
            for(int i = 0; i < key; i++){
                clook[counter++] = sortedRequests[i];
            }
        }
         
        return clook;
    }

    @Override
    public int[] getSchedule(int head, int[] requests, boolean direction) {
        int[] clook = new int[requests.length];
        int[] sortedRequests = sortArray(requests);
        int counter = 0;

        if(direction){
            int key = findNextSmaller(sortedRequests, head);
            for(int i = key; i >= 0; i--){
                clook[counter++] = sortedRequests[i];
            }
            for(int i = sortedRequests.length-1; i > key; i--){
                clook[counter++] = sortedRequests[i];
            }

        }else{
            int key = findNextLarger(sortedRequests, head);
            for(int i = key; i < sortedRequests.length; i++){
                clook[counter++] = sortedRequests[i];
            }
            for(int i = 0; i < key; i++){
                clook[counter++] = sortedRequests[i];
            }
        }
         
        return clook;
    }
    
}
