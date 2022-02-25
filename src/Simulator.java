import java.util.LinkedList;
/***************************************************/
/* CS-350 Spring 2022 - Homework 2 - Code Solution */
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
	SimpleServer server0 = (SimpleServer) resources.get(0);
	SimpleServer server1 = (SimpleServer) resources.get(1);
	Sink theSink = (Sink) resources.get(2);
	server0.printUtil(now);
	server1.printUtil(now);
	server0.printQLen();
	server1.printQLen();
	System.out.println("TRESP: " + (server0.getTRESP() + server1.getTRESP()));
	System.out.println("TWAIT: " + (server0.getTWAIT() + server1.getTWAIT()));
	System.out.println("RUNS: "+ theSink.getVPR());
	
	
    }
    
    /* Entry point for the entire simulation  */
    public static void main (String[] args) {
	/* Create a new simulator instance */
	Simulator sim = new Simulator();
	
	/* Create the traffic source */
	Source trafficSource = new Source(sim.timeline, Double.parseDouble(args[1]));
	    
	/* Create a new traffic sink */
	Sink trafficSink = new Sink(sim.timeline);

	/* Create new single-cpu processing server */
	SimpleServer server0 = new SimpleServer(0, sim.timeline, Double.parseDouble(args[2]), Double.parseDouble(args[4]));
	SimpleServer server1 = new SimpleServer(1, sim.timeline, Double.parseDouble(args[3]), Double.parseDouble(args[5]));

	/* Establish routing */
	trafficSource.routeTo(server0);
	server0.routeTo(server1);
	server1.routeTo(trafficSink);

	/* Add resources to be monitored */
	sim.addMonitoredResource(server0);
	sim.addMonitoredResource(server1);
	sim.addMonitoredResource(trafficSink);
	
	/* Kick off simulation */
	sim.simulate(Double.parseDouble(args[0]));	
    }
    
}
