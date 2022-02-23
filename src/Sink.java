/***************************************************/
/* CS-350 Spring 2022 - Homework 2 - Code Solution   */
/* Author: Renato Mancuso (BU)                     */
/*                                                 */
/* Description: This class implements the logic of */
/*   a traffic sink. Any request that arrives at   */
/*   the sink is effectively released from the     */
/*   system.                                       */
/*                                                 */
/***************************************************/

class Sink extends EventGenerator {

    private double completedReq = 0.0;
    private double totalVisits = 0;

    public Sink(Timeline timeline) {
	    super(timeline);
    }
    
    @Override
    void receiveRequest(Event evt) {
	    super.receiveRequest(evt);
    
        //add 1 to completed requests
        completedReq++;
        System.out.println("done");
        //add to total visited
        totalVisits += evt.getRequest().getVisits();
    }

    public double getCompleted(){
        return completedReq;
    }

    public double getTotalVisits(){
        return totalVisits;
    }
        
}

/* END -- Q1BSR1QgUmVuYXRvIE1hbmN1c28= */
