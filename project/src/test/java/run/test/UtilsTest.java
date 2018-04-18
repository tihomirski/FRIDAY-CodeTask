package run.test;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import tisho.friday.challenge.Address;
import tisho.friday.challenge.utils.InvalidInputException;
import tisho.friday.challenge.utils.Utils;

import java.util.ArrayList;


import static org.testng.Assert.fail;
import static tisho.friday.challenge.utils.Utils.*;
import static tisho.friday.challenge.utils.Utils.getModeFromArgs;


public class UtilsTest {

    private static String[] caseAdresses;
    private static ArrayList<Address> expectedAdresses = new ArrayList<Address>();

    @BeforeTest
    public void setup() {
        caseAdresses = new String[] {

                "Corso Vittorio Emanuele II 73/bis",
                "Corso Vittorio Emanuele II 73 bis",

                "Strasse des 17. Juni 110",
                "Strasse des 17. Juni 110x",

                "Winterallee 3",
                "Musterstrasse 45",
                "Blaufeldweg 123B",

                "Am Bächle 23",
                "Auf der Vogelwiese 23 b",

                "4, rue de la revolution",
                "200 Broadway Av",
                "Calle Aduana, 29",
                "Calle 39 No 1540"

        };

        expectedAdresses.add(new Address ("Corso Vittorio Emanuele II", "73/bis"));
        expectedAdresses.add(new Address ("Corso Vittorio Emanuele II", "73 bis"));

        expectedAdresses.add(new Address ("Strasse des 17. Juni", "110"));
        expectedAdresses.add(new Address ("Strasse des 17. Juni", "110x"));

        expectedAdresses.add(new Address ("Winterallee", "3"));
        expectedAdresses.add(new Address ("Musterstrasse", "45"));
        expectedAdresses.add(new Address ("Blaufeldweg", "123B"));

        expectedAdresses.add(new Address ("Am Bächle", "23"));
        expectedAdresses.add(new Address ("Auf der Vogelwiese", "23 b"));

        expectedAdresses.add(new Address ("rue de la revolution", "4"));
        expectedAdresses.add(new Address ("Broadway Av", "200"));
        expectedAdresses.add(new Address ("Calle Aduana", "29"));
        expectedAdresses.add(new Address ("Calle 39", "No 1540"));
    }


    //=====================|   Setup ends   |=====================================


    @Test(description = "Verifies the correctness of the returned Address by parsing with regex.")
    public void parseAddress() {
        System.out.println("Testing Utils.parseAddress() ...");
        Address parsedAddress = null;

        try {
            for (int i = 0; i< caseAdresses.length; i++) {
                Assert.assertTrue(caseAdresses[i].getClass() == String.class);

                parsedAddress = Utils.parseAddress(caseAdresses[i]);

                Assert.assertNotNull(parsedAddress);
                Assert.assertEquals(parsedAddress, expectedAdresses.get(i));
                Assert.assertTrue(parsedAddress.getClass() == Address.class);
            }

            Assert.assertThrows(InvalidInputException.class, () -> Utils.parseAddress(null));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.parseAddress("Corso Vittorio Emanuele II"));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.parseAddress("73/bis"));

        } catch (InvalidInputException e) {
            e.printStackTrace();
        }

    }

    @Test(description = "Verifies if the right mode arguments are retrieved from main(String[] args).")
    public void getModeFromArgs() {
        System.out.println("Testing Utils.getModeFromArgs() ...");

        //Initialization
        String[] args = {"-address", "Winter 3", "-mode", "regex"};

        try {
            //Input validation

            //Method calling
            String mode = Utils.getModeFromArgs(args);
            //Assert does not return null
            Assert.assertNotNull(mode);
            //Check against expected values
            Assert.assertEquals(mode, "regex");
            //Output validation
            Assert.assertTrue(mode.getClass() == String.class);

            //o o o o o o o o o o o o o o o o o o o o o o o o
            args = new String[] {"-address", "Winter 3", "-mode", "google"};

            //Method calling
            mode = Utils.getModeFromArgs(args);
            //Assert does not return null
            Assert.assertNotNull(mode);
            //Check against expected values
            Assert.assertEquals(mode, "google");
            //Output validation
            Assert.assertTrue(mode.getClass() == String.class);


            //Fault seeding
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(null));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-address", "Winter 3", "-mode"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-mode", "-address", "Winter 3"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-mode", "else", "-address", "Winter 3"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-mode"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-mode "}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"mode"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"mode "}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-mode", "-aaa", "Winter 3"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-ford"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-ford", "Focus"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-ford", "Focus", "-opel"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-ford", "Focus", "-opel", "Corsa"}));
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }

    }

    @Test(description = "This test ")
    public void getAddressFromArgs() {
        System.out.println("Testing Utils.getAddressFromArgs() ...");

        //Initialization
        String[] args = {"-address", "Winter 3", "-mode", "regex"};

        try {
            //Input validation

            //Method calling
            String address = Utils.getAddressFromArgs(args);
            //Assert does not return null
            Assert.assertNotNull(address);
            //Check against expected values
            Assert.assertEquals(address, "Winter 3");
            //Output validation
            Assert.assertTrue(address.getClass() == String.class);

            //o o o o o o o o o o o o o o o o o o o o o o o o
            args = new String[] {"-address", "4, rue de la revolution", "-mode", "google"};

            //Method calling
            address = Utils.getAddressFromArgs(args);
            //Assert does not return null
            Assert.assertNotNull(address);
            //Check against expected values
            Assert.assertEquals(address, "4, rue de la revolution");
            //Output validation
            Assert.assertTrue(address.getClass() == String.class);


            //Fault seeding
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(null));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-address", "Winter 3", "-mode"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-address", "-mode", "google"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-mode", "else", "-address", ""}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-mode", "else", "-address", "Winter"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-mode", "else", "-address", "3"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-address"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-address "}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"address"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"address "}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-mode", "-aaa", "Winter 3"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-ford"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-ford", "Focus"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-ford", "Focus", "-opel"}));
            Assert.assertThrows(InvalidInputException.class, () -> Utils.getModeFromArgs(new String[] {"-ford", "Focus", "-opel", "Corsa"}));
        } catch (InvalidInputException e) {
            e.printStackTrace();
            fail("Unexpected exception occurred.");
        }
    }

    @Test(description = "This test ")
    public void getResultsFromGoogle() {
        System.out.println("Testing ...");

        //Assert for String[] getResultsFromGoogle(String address, String API_key)

    }

    @Test(description = "This test ")
    public void getStreetAndNumber() {
        System.out.println("Testing ...");

        //Assert for String[] parseGoogleGSON(GeocodingResult result)

    }



    @AfterTest
    public void tearDown() {
        //...
    }

}