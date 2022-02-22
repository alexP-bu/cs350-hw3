import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Simulator {

    //simulator variables
    private double ARR_RATE;
    private double PRIMARY_AVG_SERVICE_RATE;
    private double SECONDARY_AVG_SERVICE_RATE;
    private boolean PRIMARY_BUSY = false;
    private boolean SECONDARY_BUSY = false;
    private double curTime;
    
    //system variables
    private static Queue<Event> timeline = 
        new PriorityQueue<Event>
        ((Event e1, Event e2) -> e1.getTimestamp().compareTo(e2.getTimestamp()));
    
    //TODO
    private Queue<Request> PRIMARY_request_queue = new LinkedList<Request>();
    private Queue<Request> SECONDARY_request_queue = new LinkedList<Request>();

    //init simulator
    public Simulator(double a, double b, double c){
        this.ARR_RATE = a;
        this.PRIMARY_AVG_SERVICE_RATE = 1/b;
        this.SECONDARY_AVG_SERVICE_RATE = 1/c;
    }

    //simulate!
    public void simulate(double time){
        curTime = 0;
        timeline.add(new Event(EventType.BIRTH, curTime, ARR_RATE));
        while(curTime < time){
            //get latest event
            Event event = timeline.poll();

            //print event out
            System.out.println(event);

            //advance time
            curTime = event.getTimestamp();

            //execute event
            execute(event);
        }
    }

    //system event execution simluation
    public void execute(Event event){
        //event arrival execution
        if(event.getType() == EventType.BIRTH){
            //generate next arrival
            timeline.add(new Event(event));
            //generate request and add it to primary queue
            PRIMARY_request_queue.add(new Request(event.getId(), curTime));
            //if primary queue isn't busy, serve request
            if(!PRIMARY_BUSY && (!PRIMARY_request_queue.isEmpty())){
                PRIMARY_BUSY = true;
                PRIMARY_request_queue.peek().setStartTime(curTime);
                //print that task has started on server 0
                PRIMARY_request_queue.peek().start(0);
                //generate NEXT redirect event for this request
                timeline.add(new Event(EventType.NEXT, curTime + Exp.getExp(PRIMARY_AVG_SERVICE_RATE), 0));
            }
        }

        //NEXT EVENT EXECUTION
        if(event.getType() == EventType.NEXT){
            //pop finished request from primary server and add it to secondary
            SECONDARY_request_queue.add(PRIMARY_request_queue.poll());
            PRIMARY_BUSY = false;
            if(!SECONDARY_BUSY && (!SECONDARY_request_queue.isEmpty())){
                SECONDARY_BUSY = true;
                SECONDARY_request_queue.peek().setStartTime(curTime);
                //print that process has started on server 1
                SECONDARY_request_queue.peek().start(1);
                timeline.add(new Event(EventType.DEATH, curTime + Exp.getExp(SECONDARY_AVG_SERVICE_RATE), 0));
            }
        }
        
        //MONITOR event execution
        if(event.getType() == EventType.MONITOR){

        }

        //DEATH EVENT EXECUTION
        if(event.getType() == EventType.DEATH){
            SECONDARY_request_queue.peek().setFinishTime(curTime);
            SECONDARY_request_queue.poll();
            SECONDARY_BUSY = false;
        }

    }

    /*
    * (a) length of simulation time in milliseconds. This should be passed directly as the time parameter
    * (to the simulate(...) function.
    * (b) average arrival rate of requests at the system
    * (c) average service time at the primary server
    * (d) average service time at the secondary server
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
