interface SchedulingStrategy {
    int[] getPath(int head, int[] requests, boolean direction);
    int[] getSchedule(int head, int[] requests, boolean direction); 
    //utility methods
    default int[] sortArray(int[] array){
        int[] sortedArray = clone(array);
        for (int i = 1; i < sortedArray.length; i++) {
            int key = sortedArray[i];
            int j = i - 1;

            while (j >= 0 && key < sortedArray[j]) {
                sortedArray[j + 1] = sortedArray[j];
                j--;
            }
            sortedArray[j + 1] = key;
        }
        return sortedArray;
    }

    private int[] clone(int[] array){
        int[] clone = new int[array.length];
        for(int i = 0; i < array.length; i++){
            clone[i] = array[i];
        }
        return clone;
    }
    default int findNextSmaller(int[] array, int key){
        for(int i = array.length-1; i >=0; i--){
            if(key > array[i]){
                return i;
            }
        }
        return -1;
    }   
    default int findNextLarger(int[] array, int key){
        for(int i = 0; i < array.length; i++){
            if(key < array[i]){
                return i;
            }
        }
        return array.length; 
    }
    default int[] expand(int[] array, int expandAmount){
        int[] biggerArray = new int[array.length+expandAmount];
        for(int i = 0; i < array.length; i++){
            biggerArray[i] = array[i];
        }
        return biggerArray;
    }
}
