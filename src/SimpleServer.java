
import java.util.*; 

/***************************************************/
/* CS-350 Spring 2022 - Homework 2 - Code Solution   */
/* Author: Renato Mancuso (BU)                     */
/*                                                 */
/* Description: This class implements the logic of */
/*   a single-processor server with an infinite    */
/*   request queue and exponentially distributed   */
/*   service times, i.e. a x/M/1 server.           */
/*                                                 */
/***************************************************/

class SimpleServer extends EventGenerator {

	private int id;
    private LinkedList<Request> theQueue = new LinkedList<Request>();
    private Double servTime;
	private double p0;
	private double p1;

    /* Statistics of this server --- to construct rolling averages */
    private Double cumulQ = 0.0;
    private Double cumulW = 0.0;
    private Double cumulTq = 0.0;
    private Double cumulTw = 0.0;
    private Double busyTime = 0.0;
    private int snapCount = 0;
    private int servedReqs = 0;
    
    public SimpleServer (int id, Timeline timeline, Double servTime, double p0, double p1) {
	super(timeline);
	this.id = id;
	this.p0 = p0;
	this.p1 = p1;
	/* Initialize the average service time of this server */
	this.servTime = servTime;
    }

    /* Internal method to be used to simulate the beginning of service
     * for a queued/arrived request. */
    private void __startService(Event evt, Request curRequest) {
	    Event nextEvent = new Event(EventType.DEATH, curRequest,
					evt.getTimestamp() + Exp.getExp(1/this.servTime), this);

	    curRequest.recordServiceStart(evt.getTimestamp());
	    cumulTw += curRequest.getServiceStart() - curRequest.getArrival();
	    
	    /* Print the occurrence of this event */
	    System.out.println(curRequest + " START " + this.id + ": " + evt.getTimestamp());
	    
	    super.timeline.addEvent(nextEvent);	    	
    }
    
    @Override
    void receiveRequest(Event evt) {
	super.receiveRequest(evt);

	Request curRequest = evt.getRequest();

	//add visit count to request
	curRequest.addVisit();

	curRequest.recordArrival(evt.getTimestamp());
	
	/* Upon receiving the request, check the queue size and act
	 * accordingly */
	if(theQueue.isEmpty()) {
	    __startService(evt, curRequest);
	}
	    
	theQueue.add(curRequest);	
    }

    @Override
    void releaseRequest(Event evt) {
	/* What request we are talking about? */
	Request curRequest = evt.getRequest();

	/* Remove the request from the server queue */
	Request queueHead = theQueue.removeFirst();

	/* If the following is not true, something is wrong */
	assert curRequest == queueHead;

	curRequest.recordDeparture(evt.getTimestamp());
	
	/* Update busyTime */
	busyTime += curRequest.getDeparture() - curRequest.getServiceStart();

	/* Update cumulative response time at this server */
	cumulTq += curRequest.getDeparture() - curRequest.getArrival();
	
	/* Update number of served requests */
	servedReqs++;
	
	assert super.next != null;

	//this area of code is what 'sends' the request to the next server.
	//so, with pr[p0] route request from server 0 to sink
	//and with pr[p1] route request form server 1 to server 0 
	double rand = Math.random();
	if(this.id == 0){
		if(rand <= p0){
			System.out.println(evt.getRequest() + " DONE 0: " + evt.getTimestamp());
			super.next.next.receiveRequest(evt);
		}else{
			//Print the occurrence of this event
			System.out.println(evt.getRequest() + " NEXT 1: " + evt.getTimestamp());
			super.next.receiveRequest(evt);
		}
	}else if(this.id == 1){
		if(rand <= p1){
			System.out.println(evt.getRequest() + " DONE 1: " + evt.getTimestamp());
			super.next.receiveRequest(evt);
		}else{
			System.out.println(evt.getRequest() + " NEXT 0: " + evt.getTimestamp());
			super.prev.receiveRequest(evt);
		}
	}
	
	/* Any new request to put into service?  */
	if(!theQueue.isEmpty()) {
	    Request nextRequest = theQueue.peekFirst();

	    __startService(evt, nextRequest);	    
	}
	
    }

    @Override
    Double getRate() {
	return 1/this.servTime;
    }

    @Override
    void executeSnapshot() {
	snapCount++;
	cumulQ += theQueue.size();
	cumulW += Math.max(theQueue.size()-1, 0);
    }

    @Override
    void printStats(Double time) {
	System.out.println("UTIL " + this.id + ": " + busyTime/time);
	System.out.println("QLEN " + this.id + ": " + cumulQ/snapCount);
	System.out.println("TRESP " + this.id + ": " + cumulTq/servedReqs);
	System.out.println("TWAIT " + this.id + ": " + cumulTw/servedReqs);
	System.out.println("WLEN " + this.id + ": "+ cumulW/snapCount);
    }

	@Override
	public void printUtil(Double time){
		System.out.println("UTIL " + this.id + ": " + busyTime/time);
	}

	@Override
	public void printQLen(){
		System.out.println("QLEN " + this.id + ": " + cumulQ/snapCount);
	}

	public double getTWAIT(){
		return cumulTw/servedReqs;
	}

	public double getTRESP(){
		return cumulTq/servedReqs;
	}
    
	public int getServed(){
		return servedReqs;
	}
}

/* END -- Q1BSR1QgUmVuYXRvIE1hbmN1c28= */
