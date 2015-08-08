package be.sonck.mtg.rules.impl.service;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by johansonck on 16/07/15.
 */
public class RuleIdInterpreterTest {

    @Test
    public void getParentIdLevel1() {
        Optional<String> optionalRuleId = new RuleIdInterpreter().getParentId("1.");

        assertFalse(optionalRuleId.isPresent());
    }

    @Test
    public void getParentIdLevel2() {
        Optional<String> optionalRuleId = new RuleIdInterpreter().getParentId("100.");

        assertTrue(optionalRuleId.isPresent());
        assertThat(optionalRuleId.get(), is("1."));
    }

    @Test
    public void getParentIdLevel3() {
        Optional<String> optionalRuleId = new RuleIdInterpreter().getParentId("100.1.");

        assertTrue(optionalRuleId.isPresent());
        assertThat(optionalRuleId.get(), is("100."));
    }

    @Test
    public void getParentIdLevel4() {
        Optional<String> optionalRuleId = new RuleIdInterpreter().getParentId("100.1a");

        assertTrue(optionalRuleId.isPresent());
        assertThat(optionalRuleId.get(), is("100.1."));
    }
}