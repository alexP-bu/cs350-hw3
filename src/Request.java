public class Request {
    private double arrivalTime;
    private double startTime;
    private double finishTime;

    public double getArrivalTime(){
        return arrivalTime;
    }

    public double getStartTime(){
        return startTime;
    }

    public double getFinishTime(){
        return finishTime;
    }

    public void setArrivalTime(double time){
        this.arrivalTime = time;
    }

    public void setStartTime(double time){
        this.startTime = time;
    }

    public void setFinishTime(double time){
        this.finishTime = time;
    }
}
