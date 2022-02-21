public class Event {
    private EventType type;
    private double timestamp;

    public void setTimestamp(double val){
        this.timestamp = val;
    }

    public void setType(EventType type){
        this.type = type;
    }

    public EventType getType(){
        return type;
    }

    public double getTimestamp(){
        return timestamp;
    }
}
