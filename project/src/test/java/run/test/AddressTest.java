package run.test;

import org.testng.Assert;
import tisho.friday.challenge.*;

import org.testng.annotations.Test;

public class AddressTest {

    @Test
    public void testEquals() throws Exception {
        System.out.println("Testing Address.equals() ...");
        Address address1 = new Address("Drususallee", "299B");
        Address address2 = new Address("Drususallee", "299B");

        Assert.assertEquals(address1, address2);

        Address address3 = new Address("Weggensteinweg", "14");

        Assert.assertNotEquals(address1, address3);
    }
}
