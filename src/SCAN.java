class SCAN implements SchedulingStrategy{

    @Override
    public int[] getPath(int head, int[] requests, boolean direction) { //adds 0 or 199 when needed
        int[] sortedRequests = sortArray(requests);
        int[] scanArr = new int[requests.length+1];
        
        scanArr[0] = head;
        int counter = 1;

        if(direction){
            int key = findNextSmaller(sortedRequests, head);
            int i;
            for(i = key; i >= 0; i--){
                scanArr[counter++] = sortedRequests[i];
            }
            if(counter-1 < sortedRequests.length){
                scanArr = expand(scanArr, 1);
                scanArr[counter++] = 0;
            }
            for(i = key+1; i < sortedRequests.length; i++){
                scanArr[counter++] = sortedRequests[i];
            }
        }else{
            int key = findNextLarger(sortedRequests, head);
            int i;
            for(i = key; i < sortedRequests.length; i++){
                scanArr[counter++] = sortedRequests[i];
            }
            if(counter-1 < sortedRequests.length){
                scanArr = expand(scanArr, 1);
                scanArr[counter++] = 199;
            }
            for(i = key-1; i >= 0; i--){
                scanArr[counter++] = sortedRequests[i];
            }
        }
        return scanArr;
    }

    @Override
    public int[] getSchedule(int head, int[] requests, boolean direction) { //describes order of visiting requests
        int[] scanArr = new int[requests.length];
        int[] sortedRequests = sortArray(requests);
        int counter = 0;

        if(direction){
            int key = findNextSmaller(sortedRequests, head);
            int i;
            for(i = key; i >= 0; i--){
                scanArr[counter++] = sortedRequests[i];
            }
            for(i = key+1; i < sortedRequests.length; i++){
                scanArr[counter++] = sortedRequests[i];
            }
        }else{
            int key = findNextLarger(sortedRequests, head);
            int i;
            for(i = key; i < sortedRequests.length; i++){
                scanArr[counter++] = sortedRequests[i];
            }
            for(i = key-1; i >= 0; i--){
                scanArr[counter++] = sortedRequests[i];
            }
        }
        return scanArr;
    }
    
}
