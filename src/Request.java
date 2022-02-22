public class Request {
    private double arrivalTime;
    private double startTime;
    private double finishTime;
    private int id;

    public Request(int id, double arrTime){
        this.id = id;
        this.arrivalTime = arrTime;
    }

    public double getArrivalTime(){
        return arrivalTime;
    }

    public double getStartTime(){
        return startTime;
    }

    public double getFinishTime(){
        return finishTime;
    }

    public double getId(){
        return id;
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

    public void setId(int id){
        this.id = id;
    }

    public void start(int serverId) {
        switch(serverId){
            case 0:
                System.out.println("R" + this.id + " START 0: " + startTime);
                break;
            case 1:
                System.out.println("R" + this.id + " START 1: " + startTime);
                break;
        }
    }
}
