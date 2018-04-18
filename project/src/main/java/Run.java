import tisho.friday.challenge.Address;
import tisho.friday.challenge.utils.InvalidInputException;

import static tisho.friday.challenge.utils.Utils.*;
//import static utils.Utils.*;

public class Run {

    private static final String USER_AGENT = "Mozilla/5.0";



    public static void main(String[] args) {

        Address address = null;
        try {
            String mode = getModeFromArgs(args);
            String input_address = getAddressFromArgs(args);

            String API_key = "AIzaSyADqQxCPnLmHlT6I5-8yadnQVobfWViwiY";

            address = null;

            if(mode.equals("regex")) {
                System.out.println("Solving with regex ...");
                address = parseAddress(input_address);
            } else if (mode.equals("google")){
                    System.out.println("Solving with a request to Google Maps API ...");
                    address = getResultsFromGoogle(input_address, API_key);
                } else {
                System.err.println("Invalid mode provided.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("=====| Final result: |=======\n" + address.toString());
        System.out.println("=============================");

    }


}
