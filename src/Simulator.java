import java.util.PriorityQueue;

public class Simulator {

    //simulator variables
    private double ARR_RATE;
    private double PRIMARY_AVG_SERVICE_TIME;
    private double SECONDARY_AVG_SERVICE_TIME;
    
    //system variables
    private PriorityQueue<Event> timeline = new PriorityQueue<>();
    private PriorityQueue<Request> request_queue = new PriorityQueue<>();

    //init simulator
    public Simulator(double a, double b, double c){
        this.ARR_RATE = a;
        this.PRIMARY_AVG_SERVICE_TIME = b;
        this.SECONDARY_AVG_SERVICE_TIME = c;
    }

    //simulate!
    public void simulate(double time){
        double timeCounter = 0;
        while(timeCounter < time){
            //get latest event
            Event event = timeline.poll();

            //print event out
            System.out.println(event);

            //advance time
            timeCounter = event.getTimestamp();

            //execute event
            execute(event);
        }
    }

    //system event execution simluation
    public void execute(Event event){
        if(event.getType() == EventType.BIRTH){

        }

        if(event.getType() == EventType.DEATH){

        }

        if(event.getType() == EventType.MONITOR){
            
        }
    }

    /*
    * (a) length of simulation time in milliseconds. This should be passed directly as the time parameter
    * (to the simulate(...) function.
    * (b) average arrival rate of requests at the system;
    * (c) average service time at the primary server;
    * (d) average service time at the secondary server;
    */
    public static void main(String[] args) {
        double a = Double.parseDouble(args[0]);
        double b = Double.parseDouble(args[1]);
        double c = Double.parseDouble(args[2]);
        double d = Double.parseDouble(args[3]);
        Simulator simulator = new Simulator(b, c, d);

        simulator.simulate(a);
    }
}
