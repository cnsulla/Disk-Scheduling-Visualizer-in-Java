class SSTF implements SchedulingStrategy{

    @Override
    public int[] getPath(int head, int[] requests, boolean direction) {       
        int[] sortedRequests = sortArray(requests);
        int[] sstfArr = new int[requests.length+1];
        sstfArr[0] = head;
        
        int key = findClosest(sortedRequests, head);
        sstfArr[1] = sortedRequests[key];
        int left = key-1;
        int right = key+1;
        int counter = 2;
        while(left >= 0 && right < requests.length){
            if(Math.abs(sortedRequests[left]-sortedRequests[key]) < Math.abs(sortedRequests[right]-sortedRequests[key])){
                key = left;
                left--;                
            }else{
                key = right;
                right++;
            }
            sstfArr[counter] = sortedRequests[key];
            counter++;
        }
        while(counter < sstfArr.length){
            if(left < 0){                
                sstfArr[counter] = sortedRequests[right++];
            }
            else{
                sstfArr[counter] = sortedRequests[left--];
            }
            counter++;
        }        
        return sstfArr;
    }

    @Override
    public int[] getSchedule(int head, int[] requests, boolean direction) {
        int[] sortedRequests = sortArray(requests);
        int[] sstfArr = new int[requests.length];
        int counter = 0;
        int key = findClosest(sortedRequests, head);
        sstfArr[counter++] = sortedRequests[key];
        int left = key-1;
        int right = key+1;
        while(left >= 0 && right < requests.length){
            if(Math.abs(sortedRequests[left]-sortedRequests[key]) < Math.abs(sortedRequests[right]-sortedRequests[key])){
                key = left;
                left--;                
            }else{
                key = right;
                right++;
            }
            sstfArr[counter] = sortedRequests[key];
            counter++;
        }
        while(counter < sstfArr.length){
            if(left < 0){                
                sstfArr[counter] = sortedRequests[right++];
            }
            else{
                sstfArr[counter] = sortedRequests[left--];
            }
            counter++;
        }        
        return sstfArr;
    }

    private int findClosest(int[] array, int key){
        int index = 0;
        int minTime = Math.abs(array[index] - key);
        for(int i = 1; i < array.length; i++){
            int currTime = Math.abs(array[i] - key);
            if(currTime < minTime){
                index = i;
                minTime = currTime;
            }
        }
        return index;
    }
}