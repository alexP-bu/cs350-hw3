import java.util.LinkedList;
/***************************************************/
/* CS-350 Spring 2022 - Homework 2 - Code Solution   */
/* Author: Renato Mancuso (BU)                     */
/*                                                 */
/* Description: This class implements the logic of */
/*   a simulator where a single source of events   */
/*   is connected to a single exit point, with a   */
/*   single-processor server in the middle.        */
/*                                                 */
/***************************************************/

public class Simulator {

    /* These are the resources that we intend to monitor */
    private LinkedList<EventGenerator> resources = new LinkedList<EventGenerator>();

    /* Timeline of events */
    private Timeline timeline = new Timeline();

    /* Simulation time */    
    private Double now;
    
    public void addMonitoredResource (EventGenerator resource) {
	this.resources.add(resource);
    }

    public static Sink trafficSink;

    /* This method creates a new monitor in the simulator. To collect
     * all the necessary statistics, we need at least one monitor. */
    private void addMonitor() {
	/* Scan the list of resources to understand the granularity of
	 * time scale to use */
	Double monRate = Double.POSITIVE_INFINITY;

	for (int i = 0; i < resources.size(); ++i) {
	    Double rate = resources.get(i).getRate();
	    if (monRate > rate) {
		monRate = rate;
	    }
	}

	/* If this fails, something is wrong with the way the
	 * resources have been instantiated */
	assert !monRate.equals(Double.POSITIVE_INFINITY);

	/* Create a new monitor for this simulation */
	Monitor monitor = new Monitor(timeline, monRate, resources);

    }
    
    public void simulate (Double simTime) {

	/* Rewind time */
	now = 0.0;

	/* Add monitor to the system */
	addMonitor();
	
	/* Main simulation loop */
	while(now < simTime) {
	    /* Fetch event from timeline */
	    Event evt = timeline.popEvent();
	    
	    /* Fast-forward time */
	    now = evt.getTimestamp();
	    
	    /* Extract block responsible for this event */
	    EventGenerator block = evt.getSource();

	    /* Handle event */
	    block.processEvent(evt);
	    
	}

	/* Print all the statistics */
	resources.get(0).printUtil(now);
	resources.get(1).printUtil(now);
	resources.get(0).printQLen();
	resources.get(1).printQLen();
	SimpleServer server0 = (SimpleServer) resources.get(0);
	SimpleServer server1 = (SimpleServer) resources.get(1);
	double avgTRESP = (server0.getTRESP() + server1.getTRESP());
	double avgWait = (server0.getTWAIT() + server1.getTWAIT());
    double runs = trafficSink.getTotalVisits() / trafficSink.getCompleted();
	System.out.println("TRESP: " + avgTRESP);
	System.out.println("TWAIT: " + avgWait);
    System.out.println("RUNS: " + runs);
	
	
    }
    
    /* Entry point for the entire simulation  */
    public static void main (String[] args) {

	/* Parse the input parameters */
	double simTime = Double.valueOf(args[0]);
	double lambda = Double.valueOf(args[1]);
	double servTime_0 = Double.valueOf(args[2]);
	double servTime_1 =Double.valueOf(args[3]);
    double p0 = Double.valueOf(args[4]);
    double p1 = Double.valueOf(args[5]);

	/* Create a new simulator instance */
	Simulator sim = new Simulator();
	
	/* Create the traffic source */
	Source trafficSource = new Source(sim.timeline, lambda);
	    
	/* Create a new traffic sink */
	trafficSink = new Sink(sim.timeline);

	/* Create new single-cpu processing server */
	SimpleServer server_0 = new SimpleServer(0, sim.timeline, servTime_0, p0, p1);
	SimpleServer server_1 = new SimpleServer(1, sim.timeline, servTime_1, p0, p1);

	/* Establish routing */
	trafficSource.routeTo(server_0);
	server_0.routeTo(server_1);
	server_1.routeTo(trafficSink);

	/* Add resources to be monitored */
	sim.addMonitoredResource(server_0);
	sim.addMonitoredResource(server_1);
	
	/* Kick off simulation */
	sim.simulate(simTime);	
    }
    
}

/* END -- Q1BSR1QgUmVuYXRvIE1hbmN1c28= */
