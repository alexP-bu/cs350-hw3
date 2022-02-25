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

    private static int totalCompletedRequests = 0;
    private static int totalVisits = 0;

    public Sink(Timeline timeline) {
        super(timeline);
    }

    @Override
    void receiveRequest(Event evt) {
        super.receiveRequest(evt);

        /* Print the occurrence of this event */
        System.out.println(evt.getRequest() + " DONE 1: " + evt.getTimestamp());

        // add to counter
        totalCompletedRequests++;
        totalVisits += evt.getRequest().getVisitCount();
    }

    public double getVPR(){
        return ((double)totalVisits) / ((double)totalCompletedRequests);
    }

}

/* END -- Q1BSR1QgUmVuYXRvIE1hbmN1c28= */
