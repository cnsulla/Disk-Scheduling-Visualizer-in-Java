class CSCAN implements SchedulingStrategy {

    @Override
    public int[] getPath(int head, int[] requests, boolean direction) {
        int[] cscanArr = new int[requests.length+1];
        int[] sortedRequests = sortArray(requests);
        cscanArr[0] = head;
        int counter = 1;

        if(direction){
            int key = findNextSmaller(sortedRequests, head);
            int i;
            for(i = key; i >= 0; i--){
                cscanArr[counter++] = sortedRequests[i];
            }
            if(counter-1 < sortedRequests.length){
                cscanArr = expand(cscanArr, 2);
                cscanArr[counter++] = 0;
                cscanArr[counter++] = 199;
            }
            for(i = sortedRequests.length-1; i > key; i--){
                cscanArr[counter++] = sortedRequests[i];
            }
        }else{
            int key = findNextLarger(sortedRequests, head);
            int i;
            for(i = key; i < sortedRequests.length; i++){
                cscanArr[counter++] = sortedRequests[i];
            }
            if(counter-1 < sortedRequests.length){
                cscanArr = expand(cscanArr, 2);
                cscanArr[counter++] = 199;
                cscanArr[counter++] = 0 ;
            }
            for(i = 0; i < key; i++){
                cscanArr[counter++] = sortedRequests[i];
            }
        }
        return cscanArr;
    }

    @Override
    public int[] getSchedule(int head, int[] requests, boolean direction) {
        int[] cscanArr = new int[requests.length+1];
        int[] sortedRequests = sortArray(requests);
        cscanArr[0] = head;
        int counter = 1;

        if(direction){
            int key = findNextSmaller(sortedRequests, head);
            int i;
            for(i = key; i >= 0; i--){
                cscanArr[counter++] = sortedRequests[i];
            }
            for(i = sortedRequests.length-1; i > key; i--){
                cscanArr[counter++] = sortedRequests[i];
            }
        }else{
            int key = findNextLarger(sortedRequests, head);
            int i;
            for(i = key; i < sortedRequests.length; i++){
                cscanArr[counter++] = sortedRequests[i];
            }
            for(i = 0; i < key; i++){
                cscanArr[counter++] = sortedRequests[i];
            }
        }
        return cscanArr;
    }
    
}
