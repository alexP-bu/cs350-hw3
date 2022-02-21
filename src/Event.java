public class Event {
    private EventType type;
    private Double timestamp;
    private Double lambda;
    private int id;

    public Event(EventType type, Double timestamp, double lambda){
        this.type = type;
        this.timestamp = timestamp;
        this.lambda = lambda;
    }

    public Event(Event e){
        this.type = e.getType();
        this.timestamp = e.getTimestamp() + Exp.getExp(e.lambda);
        this.lambda = e.lambda;
    }

    public void setTimestamp(double val){
        this.timestamp = val;
    }

    public void setType(EventType type){
        this.type = type;
    }

    public EventType getType(){
        return this.type;
    }

    public Double getTimestamp(){
        return this.timestamp;
    }

    @Override
    public String toString(){
        return this.type + ": " + this.timestamp + "\n";
    }
}
