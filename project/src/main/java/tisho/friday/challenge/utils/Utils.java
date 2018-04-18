package tisho.friday.challenge.utils;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import tisho.friday.challenge.Address;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static Pattern[] numberPatterns = new Pattern[] {
            Pattern.compile("[Nn][OoRr][\\. ]*[0-9]+[a-zA-Z]?\\b"),
            Pattern.compile("([0-9]+(\\s*[a-zA-Z]?[ /]bis){1})\\b"),
            Pattern.compile("([0-9]+(\\s*[a-zA-Z]))\\b"),
            Pattern.compile("[0-9]+(?!\\.)\\b")
    };

    public static Address parseAddress(String address) throws InvalidInputException {
        if (address == null) {
            throw new InvalidInputException("The input String is null");
        }
        String number = null;
        String street = null;
        int ruleNumber = 0;
        for (Pattern pattern : numberPatterns) {
            Matcher matcher = pattern.matcher(address);
            if (matcher.find()) {
//                System.out.println("[rule=" + ruleNumber + "] Input address: " + address);
                number = matcher.group();
                street = address.substring(0, matcher.start());
                if (matcher.end() < address.length())
                    street += address.substring(matcher.end(), address.length());
                street = street.replaceAll(",", "");
                street = street.trim();
                number = number.replaceAll("[,\\.]", "");
                number = number.trim();
                break;
            }
            ruleNumber++;
        }

        if (street == null || street.equals("") ||
            number == null || number.equals("")) {

            throw new InvalidInputException("Members street or number could not be retrieved");
        }

        return new Address(street, number);
    }

    public static String getModeFromArgs(String[] args) throws InvalidInputException {
        if (args == null || args.length < 4) {
            throw new InvalidInputException("Insufficient arguments passed. \n" +
                                            "   Example: -address \"<insert_address>\" -mode <mode_option> \n\n" +
                                            "   Mode options:\n" +
                                            "       google\n" +
                                            "       regex");
        }
        for (int i=0; i<args.length; i++) {
            if (args[i].equals("-mode") && (i+1) < args.length && (args[i+1].equals("regex") || args[i+1].equals("google")))
                return args[i+1];
        }

        throw new InvalidInputException("No mode provided. Modes can be either google or regex.\n" +
                "   Example: -mode google");

    }

    public static String getAddressFromArgs(String[] args) throws InvalidInputException {
        if (args == null || args.length < 4) {
            throw new InvalidInputException("Insufficient arguments passed. \n" +
                    "   Example: -address \"<insert_address>\" -mode <mode_option> \n\n" +
                    "   Mode options:\n" +
                    "       google\n" +
                    "       regex");
        }
        for (int i=0; i<args.length; i++) {
            if (args[i].equals("-address") && (i+1) < args.length && args[i+1].split(" ").length >= 2)
                return args[i+1];
        }


        throw new InvalidInputException("No address provided. Address must be enclosed in \" \".\n" +
                "   Example: -address \"Calle Aduana, 29\"");

    }

    public static Address getResultsFromGoogle(String addressString, String API_key) throws InvalidInputException,AddressNotFoundException{

        GeocodingResult[] results = null;

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_key)
                .build();

        try {
            String inputGoogle = addressString.replaceAll(" ", "+");
            results = GeocodingApi.geocode(context, inputGoogle).await();
        } catch (Exception e) { e.printStackTrace(); }

        if (results.length == 0)
            throw new AddressNotFoundException("The specified address is not existing according to Google");
        return parseGoogleJSON(results[0]);

    }

    public static Address parseGoogleJSON(GeocodingResult result) throws InvalidInputException {
        String street = null;
        String number = null;
        for (AddressComponent currentComponent : result.addressComponents) {
            for (AddressComponentType currentType : currentComponent.types) {
                if (currentType.toString().equals("route"))
                    street = currentComponent.longName;
                if (currentType.toString().equals("street_number"))
                    number = currentComponent.longName;
            }
        }

        if (street == null || street.equals("") ||
            number == null || number.equals("")) {

            throw new InvalidInputException("Members street or number could not be retrieved");
        }

        return new Address(street, number);
    }

}
