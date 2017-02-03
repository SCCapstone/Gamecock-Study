package edu.sc.cse;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    //test the email before the @ sign for unit testing.
    public void myUsernameTest() throws Exception
    {
        String anEmail = "blahblahImanEmail@emailplace.com";
        String aResult = LoginScreen.createUsername( anEmail );
        String anExpectedResult = "blahblahImanEmail";

        assertEquals(  aResult , anExpectedResult );


    }



}