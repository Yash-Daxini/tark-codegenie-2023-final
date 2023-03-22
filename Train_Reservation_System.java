import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

//Train Class
class Train {
    int trainNumber;
    String source;
    String destination;
    int distanceFromSourceToDestination;
    HashMap<String, Integer> coachDetail = new HashMap<>();

    // Constructor of an object
    Train(int trainNumber, String source, String destination, int distanceFromSourceToDestination,
            HashMap<String, Integer> coachDetail) {
        this.trainNumber = trainNumber;
        this.source = source;
        this.destination = destination;
        this.distanceFromSourceToDestination = distanceFromSourceToDestination;
        this.coachDetail = coachDetail;
    }
    Train(Train obj) {
        this.trainNumber = obj.trainNumber;
        this.source = obj.source;
        this.destination = obj.destination;
        this.distanceFromSourceToDestination = obj.distanceFromSourceToDestination;
        this.coachDetail = new HashMap<>();
        for( String key : obj.coachDetail.keySet() ){
            this.coachDetail.put( key , obj.coachDetail.get(key) );
        }
    }
}

// Bookind Details
class BookingRequests {
    String source;
    String destination;
    String date;
    String coach;
    int totalPassenger;

    BookingRequests(String source, String destination, String date, String coach, int totalPassenger) {
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.coach = coach;
        this.totalPassenger = totalPassenger;
    }

}

//Store and track the seats according to specific date
class DateWiseCoachAndSits{
    String date;
    String route;
    int seatsAvailbaleInS;
    int seatsAvailbaleInA;
    int seatsAvailbaleInB;
    int seatsAvailbaleInH;

    DateWiseCoachAndSits( String date , String route , int seatsAvailbaleInS , int seatsAvailbaleInA , int seatsAvailbaleInB , int seatsAvailbaleInH ){
        this.date = date;
        this.route = route;
        this.seatsAvailbaleInS = seatsAvailbaleInS;
        this.seatsAvailbaleInA = seatsAvailbaleInA;
        this.seatsAvailbaleInB = seatsAvailbaleInB;
        this.seatsAvailbaleInH = seatsAvailbaleInH;
    }
}

//Main Class
class Train_Reservation_System {

    // Store all train details
    static HashMap<String, Train> trainDetails;

    static HashMap<String, DateWiseCoachAndSits> dateWiseCoachAndSeats;

    static String curDate;

    // Declare PNR
    static long PNR;

    // Take train details
    static void takeTrainDetails() throws Exception {
        // Input Object
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int totalTrains = Integer.parseInt(bufferedReader.readLine());

        while (totalTrains-- > 0) {
            String trainDetail = bufferedReader.readLine();
            String[] trainDetailArray = trainDetail.split(" ");

            String coachDetail = bufferedReader.readLine();
            String[] coachDetailArray = coachDetail.split(" ");

            HashMap<String, Integer> coachDetailMap = new HashMap<>();

            HashMap<String, Integer> coachDetailMap2 = new HashMap<>();

            int trainNumber = Integer.parseInt(trainDetailArray[0]);

            String source = trainDetailArray[1].split("-")[0];

            String destination = trainDetailArray[2].split("-")[0];

            int distanceFromSourceToDestination = Integer.parseInt(trainDetailArray[2].split("-")[1]);

            for (int i = 1; i < coachDetailArray.length; i++) {
                String coach = "";
                if( coachDetailArray[i].split("-")[0].charAt(0) == 'S' ){
                    coach = "S";
                }
                else if( coachDetailArray[i].split("-")[0].charAt(0) == 'A' ){
                    coach = "A";
                }
                else if( coachDetailArray[i].split("-")[0].charAt(0) == 'B' ){
                    coach = "B";
                }
                else if( coachDetailArray[i].split("-")[0].charAt(0) == 'H' ){
                    coach = "H";
                }
                coachDetailMap.put(coach, coachDetailMap.getOrDefault( coach , 0 ) + 
                        Integer.parseInt(coachDetailArray[i].split("-")[1]));
                coachDetailMap2.put(coach,
                        Integer.parseInt(coachDetailMap2.getOrDefault( coach , 0 ) + coachDetailArray[i].split("-")[1]));
            }

            Train TrainObj = new Train(trainNumber, source, destination, distanceFromSourceToDestination,
                    coachDetailMap);

            String sourceAndDestination = source + " " + destination;

            // Store details of the train in the hashmap
            trainDetails.put(sourceAndDestination, TrainObj);

            
        }

    }

    // Take Booking Requests
    static void takeTicketBookingDetails() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String bookingRequestFromUser = bufferedReader.readLine();

        String bookingRequestFromUserArray[] = bookingRequestFromUser.split(" ");

        String source = bookingRequestFromUserArray[0];

        String destination = bookingRequestFromUserArray[1];

        String date = bookingRequestFromUserArray[2];

        String coach = bookingRequestFromUserArray[3];

        int totalPassenger = Integer.parseInt(bookingRequestFromUserArray[4]);

        checkAndPrintIfSeatsAvailable(source + " " + destination, coach, totalPassenger , date);

    }

    // Give amount per km
    static int giveAmountFromCoach(String coach) {
        if (coach.equals("SL"))
            return 1;
        if (coach.equals("3A"))
            return 2;
        if (coach.equals("2A"))
            return 3;
        return 4;
    }

    // Check that ticke is available and if availble then print PNR and fair
    static void checkAndPrintIfSeatsAvailable(String sourceAndDestination, String coach, int totalPassengers , String date) {

        int amount = giveAmountFromCoach(coach);

        if (coach.equals("SL"))
            coach = "S";
        else if (coach.equals("3A"))
            coach = "B";
        else if (coach.equals("2A"))
            coach = "A";
        else if (coach.equals("1A"))
            coach = "H";

        double fair = 0;

        if (!trainDetails.containsKey(sourceAndDestination)) {
            System.out.println("No Trains Available");
        }
        else {
            for (String route : trainDetails.keySet()) {
                if (route.equalsIgnoreCase(sourceAndDestination)) {

                    for (String coachString : trainDetails.get(route).coachDetail.keySet()) {

                        String tempcoachString = "";

                        if (coachString.charAt(0) == 'S')
                            tempcoachString = "S";
                        else if (coachString.charAt(0) == 'B')
                            tempcoachString = "B";
                        else if (coachString.charAt(0) == 'A')
                            tempcoachString = "A";
                        else if (coachString.charAt(0) == 'H')
                            tempcoachString = "H";

                        if( !dateWiseCoachAndSeats.containsKey(date + " " + route) ){

                            // System.out.println( trainDetails.toString() );

                            DateWiseCoachAndSits dateObj = new DateWiseCoachAndSits(date,route,trainDetails.get(route).coachDetail.getOrDefault("S",0),trainDetails.get(route).coachDetail.getOrDefault("A",0),trainDetails.get(route).coachDetail.getOrDefault("B",0),trainDetails.get(route).coachDetail.getOrDefault("H",0));

                            dateWiseCoachAndSeats.put( date + " " + route , dateObj );

                        }

                        // for( String key : dateWiseCoachAndSeats.keySet() ){
                        //     System.out.println( key + " " + dateWiseCoachAndSeats.get(key).toString() );
                        // }

                        if (tempcoachString.equals(coach) && coach.equals("S") && dateWiseCoachAndSeats.get(date + " " + route).seatsAvailbaleInS >= totalPassengers) {

                            fair += trainDetails.get(route).distanceFromSourceToDestination * amount * totalPassengers;

                            if( dateWiseCoachAndSeats.get(date + " " + route).seatsAvailbaleInS
                            - totalPassengers <= 0 ){
                                dateWiseCoachAndSeats.get(date + " " + route).seatsAvailbaleInS = 0;
                            }
                            else{
                                dateWiseCoachAndSeats.get(date + " " + route).seatsAvailbaleInS -= totalPassengers;
                            }

                            break;

                        }
                     
                        else if (tempcoachString.equals(coach) && coach.equals("A") && dateWiseCoachAndSeats.get(date + " " + route).
                        seatsAvailbaleInA >= totalPassengers) {

                            fair += trainDetails.get(route).distanceFromSourceToDestination * amount * totalPassengers;

                            if( dateWiseCoachAndSeats.get(date + " " + route).seatsAvailbaleInA
                            - totalPassengers <= 0 ){
                                dateWiseCoachAndSeats.get(date + " " + route).seatsAvailbaleInA = 0;
                            }
                            else{
                                dateWiseCoachAndSeats.get(date + " " + route).seatsAvailbaleInA -= totalPassengers;
                            }

                            break;

                        }

                        else if (tempcoachString.equals(coach) && coach.equals("B") && dateWiseCoachAndSeats.get(date + " " + route).seatsAvailbaleInB >= totalPassengers) {

                            fair += trainDetails.get(route).distanceFromSourceToDestination * amount * totalPassengers;

                            if( dateWiseCoachAndSeats.get(date + " " + route).seatsAvailbaleInB
                            - totalPassengers <= 0 ){
                                dateWiseCoachAndSeats.get(date + " " + route).seatsAvailbaleInB = 0;
                            }
                            else{
                                dateWiseCoachAndSeats.get(date + " " + route).seatsAvailbaleInB -= totalPassengers;
                            }

                            break;

                        }

                        else if (tempcoachString.equals(coach) && coach.equals("H") && dateWiseCoachAndSeats.get(date + " " + route).seatsAvailbaleInH >= totalPassengers) {

                            fair += trainDetails.get(route).distanceFromSourceToDestination * amount * totalPassengers;

                            if( dateWiseCoachAndSeats.get(date + " " + route).seatsAvailbaleInS
                            - totalPassengers <= 0 ){
                                dateWiseCoachAndSeats.get(date + " " + route).seatsAvailbaleInH = 0;
                            }
                            else{
                                dateWiseCoachAndSeats.get(date + " " + route).seatsAvailbaleInH -= totalPassengers;
                            }

                            break;

                        }

                    }

                }

                if (fair != 0) {
                    break;
                }
            }

            if (fair == 0) {
                System.out.println("No Seats Available");
            } else {
                System.out.println((PNR++) + " " + fair);
            }

        }

    }

    // Main function
    public static void main(String[] args) throws Exception {

        PNR = 100000001;

        trainDetails = new HashMap<>();
        
        dateWiseCoachAndSeats = new HashMap<>();
        
        takeTrainDetails();

        curDate = "";

        while (true) {

            takeTicketBookingDetails();

        }

    }
}