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

class Train_Reservation_System {

    // Store all train details
    static HashMap<String, Train> trainDetails;

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

            int trainNumber = Integer.parseInt(trainDetailArray[0]);

            String source = trainDetailArray[1].split("-")[0];

            String destination = trainDetailArray[2].split("-")[0];

            int distanceFromSourceToDestination = Integer.parseInt(trainDetailArray[2].split("-")[1]);

            for (int i = 1; i < coachDetailArray.length; i++) {
                coachDetailMap.put(coachDetailArray[i].split("-")[0],
                        Integer.parseInt(coachDetailArray[i].split("-")[1]));
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

        String coach = bookingRequestFromUserArray[3];

        int totalPassenger = Integer.parseInt(bookingRequestFromUserArray[4]);

        checkAndPrintIfSeatsAvailable(source + " " + destination, coach, totalPassenger);

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
    static void checkAndPrintIfSeatsAvailable(String sourceAndDestination, String coach, int totalPassengers) {

        int amount = giveAmountFromCoach(coach);

        System.out.println("Amount " + amount);

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
                    System.out.println("route match");
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

                        System.out.println(coachString.charAt(0) + " " + coach.charAt(coach.length() - 1) + " " + tempcoachString);
                        System.out.println(trainDetails.get(route).coachDetail.toString());

                        if (coachString.charAt(0) == coach.charAt(coach.length() - 1)
                                && tempcoachString.equals(coach) && trainDetails.get(route).coachDetail
                                        .get(coachString.charAt(0) + "") >= totalPassengers) {

                            System.out.println("coach match");

                            fair += trainDetails.get(route).distanceFromSourceToDestination * amount * totalPassengers;

                            trainDetails.get(route).coachDetail.put(coachString.charAt(0) + "",
                                    trainDetails.get(route).coachDetail.get(coachString.charAt(0) + "")
                                            - totalPassengers);

                            System.out.println("Amount " + amount);

                            break;

                        } else {
                            continue;
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

        takeTrainDetails();

        while (true) {

            takeTicketBookingDetails();

        }

    }
}