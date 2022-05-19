class FCFS implements SchedulingStrategy{
    @Override
    public int[] getPath(int head, int[] requests, boolean direction) {
        int[] schedule = new int[requests.length+1];
        schedule[0] = head;
        for(int i = 1; i < schedule.length; i++){
            schedule[i] = requests[i-1];
        }
        return schedule;
    }

    @Override
    public int[] getSchedule(int head, int[] requests, boolean direction) {
        return requests;
    }

}
