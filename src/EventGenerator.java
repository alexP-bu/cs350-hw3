/***************************************************/
/* CS-350 Spring 2022 - Homework 2 - Code Solution   */
/* Author: Renato Mancuso (BU)                     */
/*                                                 */
/* Description: This class implements the logic of */
/*   a generic simulator that produces events.     */
/*   depending on the object extendind this class  */
/*   one might define a source, sink, or server.   */
/*                                                 */
/***************************************************/

class EventGenerator {

    protected Timeline timeline;
    protected EventGenerator next;
    protected EventGenerator prev;
    protected int id;

    public EventGenerator(Timeline timeline) {
        this.timeline = timeline;
        this.id = -1;
    }

    /*
     * Function that is used to signify the arrival of a new request
     * to the event generator
     */
    void receiveRequest(Event evt) {
        Request req = evt.getRequest();
        req.moveTo(this);
    }

    /*
     * Function that is used to signify the departure of a new request
     * from the event generator
     */
    void releaseRequest(Event evt) {
    }

    /*
     * Function to set the next hop for requests departing from this
     * event generetor block
     */
    void routeTo(EventGenerator next) {
        this.next = next;
        this.next.prev = this;
    }

    /*
     * Connect EventGenerator to corresponding timeline if not done by
     * the constructur
     */
    void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    /*
     * Generic event processing logic for a typical event generator
     * block --- a monitor will have to override this method.
     */
    void processEvent(Event evt) {
        if (evt.getType() == EventType.BIRTH) {
            receiveRequest(evt);
        } else if (evt.getType() == EventType.DEATH) {
            releaseRequest(evt);
        }
    }

    /* When no rate is set, default to some value */
    Double getRate() {
        return Double.POSITIVE_INFINITY;
    }

    /* Sub-class will have to implement this if needed */
    void executeSnapshot() {
        /* Do nothing */
    }

    /* Sub-class will have to implement this if needed */
    void printStats(Double time) {
        /* Do nothing */
    }

    void printUtil(Double time) {

    }

    void printQLen() {

    }

    public int getID(){
        return id;
    }

    public void setID(int x){
        this.id = x;
    }
}

/* END -- Q1BSR1QgUmVuYXRvIE1hbmN1c28= */
