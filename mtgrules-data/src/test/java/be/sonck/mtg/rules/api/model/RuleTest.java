package be.sonck.mtg.rules.api.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by johansonck on 14/07/15.
 */
public class RuleTest {

    @Test
    public void create() {
        Rule rule = new Rule("id", "text");

        assertThat(rule.getId(), is("id"));
        assertThat(rule.getText(), is("text"));
    }

    @Test
    public void setParent() {
        Rule rule = new Rule("id", "text");

        Rule parentRule = new Rule("", "");
        rule.setParent(parentRule);

        assertTrue(parentRule.getChildren().contains(rule));
    }

    @Test(expected = IllegalStateException.class)
    public void parentExists() {
        Rule rule = new Rule("id", "text");

        rule.setParent(new Rule("", ""));
        rule.setParent(new Rule("", ""));
    }
}