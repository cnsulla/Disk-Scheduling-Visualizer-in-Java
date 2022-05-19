class LOOK implements SchedulingStrategy{

    @Override
    public int[] getPath(int head, int[] requests, boolean direction) {
        int[] sortedRequests = sortArray(requests);
        int[] lookArr = new int[sortedRequests.length+1];
        lookArr[0] = head;
        int counter = 1;

        if(direction){
            int key = findNextSmaller(sortedRequests, head);
            int i;
            for(i = key; i >= 0; i--){
                lookArr[counter++] = sortedRequests[i]; 
            }
            for(i = key+1; i < sortedRequests.length;i++){
                lookArr[counter++] = sortedRequests[i];
            }

        }else{
            int key = findNextLarger(sortedRequests, head);
            int i;
            for(i = key; i < sortedRequests.length; i++){
                lookArr[counter++] = sortedRequests[i]; 
            }
            for(i = key-1; i >= 0;i--){
                lookArr[counter++] = sortedRequests[i];
            } 
        }
        return lookArr;
    }

    @Override
    public int[] getSchedule(int head, int[] requests, boolean direction) {
        int[] sortedRequests = sortArray(requests);
        int[] lookArr = new int[sortedRequests.length];
        int counter = 0;

        if(direction){
            int key = findNextSmaller(sortedRequests, head);
            int i;
            for(i = key; i >= 0; i--){
                lookArr[counter++] = sortedRequests[i]; 
            }
            for(i = key+1; i < sortedRequests.length;i++){
                lookArr[counter++] = sortedRequests[i];
            }

        }else{
            int key = findNextLarger(sortedRequests, head);
            int i;
            for(i = key; i < sortedRequests.length; i++){
                lookArr[counter++] = sortedRequests[i]; 
            }
            for(i = key-1; i >= 0;i--){
                lookArr[counter++] = sortedRequests[i];
            } 
        }
        return lookArr;
    }
    
}
