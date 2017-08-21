package utils;

import com.kevin.demo.camel.common.utils.Regex;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.fail;

public class RegexTest {

    @Test
    public void matchTest() {
        Assertions.assertThat(Regex.doesMatch("42", "^[^\\DO:105-93+30]+$")).isTrue();
    }

    @Test
    public void captureTestFail() {
        try {
            List<String> captureList = Regex.capture("You are {obviously not} true master of regex now", "(\\{[^}]+\\}(*SKIP)(*FAIL)|[A-z]+)");
            fail("If it works be happy :D");
        } catch (Exception e) {
            System.out.println("Actually Java regex lib does not have (*SKIP) and (*FAIL) implemented yet");
        }
    }

    @Test
    public void captureTest() {
        List<String> captureList = Regex.capture("You are {obviously not} true master of regex now", "(.+?)\\{.+}(.+)");


        Assertions.assertThat(captureList.size()).isEqualTo(2);
        Assertions.assertThat(captureList.get(0)).isEqualTo("You are ");
        Assertions.assertThat(captureList.get(1)).isEqualTo(" true master of regex now");
    }
}
