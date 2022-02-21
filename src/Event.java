public class Event {
    private EventType type;
    private Double timestamp;
    private Double lambda;
    private int id;

    public Event(EventType type, double timestamp, double lambda){
        this.type = type;
        this.timestamp = timestamp;
        this.lambda = lambda;
        this.id = 0;
    }

    public Event(Event e){
        this.type = e.getType();
        this.timestamp = (e.getTimestamp() + Exp.getExp(e.getLambda()));
        this.lambda = e.lambda;
        this.id = e.getId() + 1;
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

    public Double getLambda(){
        return this.lambda;
    }

    public int getId(){
        return this.id;
    }

    @Override
    public String toString(){
        return "R" + this.id + " " + this.type + ": " + this.timestamp;
    }
}
