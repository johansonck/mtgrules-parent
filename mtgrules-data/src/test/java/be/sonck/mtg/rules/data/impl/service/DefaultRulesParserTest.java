package be.sonck.mtg.rules.data.impl.service;

import com.google.common.collect.Iterators;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by johansonck on 12/07/15.
 */
public class DefaultRulesParserTest {

    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-beans.xml");

    private RulesParser rulesParser = (RulesParser) applicationContext.getBean("rulesParser");

    @Test
    public void getEffectiveDate() {
        assertThat(rulesParser.getEffectiveDate(), is("These rules are effective as of March 27, 2015."));
    }

    @Test
    public void getRules() {
        Map<String, String> map = rulesParser.getRules();

        assertNotNull(map);

        Set<String> keys = map.keySet();
        Iterator<String> keyIterator = keys.iterator();

        assertTrue(keyIterator.hasNext());
        validate(map, keyIterator.next(), "1", "Game Concepts");

        assertTrue(keyIterator.hasNext());
        validate(map, keyIterator.next(), "100", "General");

        assertThat(map.get("205.3i"), is("Lands have their own unique set of subtypes; these subtypes are called land types. The land " +
                "types are Desert, Forest, Gate, Island, Lair, Locus, Mine, Mountain, Plains, Power-Plant, Swamp, Tower, " +
                "and Urza’s. Of that list, Forest, Island, Mountain, Plains, and Swamp are the basic land types. See rule 305.6."));

        assertThat(map.get("903.5c"), is("A card can be included in a Commander deck only if every color in its color identity is also " +
                "found in the color identity of the deck’s commander. Example: Wort, the Raidmother is a legendary creature with mana " +
                "cost {4}{R/G}{R/G}. Wort’s color identity is red and green. Each card in a Wort Commander deck must be only red, only " +
                "green, both red and green, or have no color. Each mana symbol in the mana cost or rules text of a card in this deck " +
                "must be only red, only green, both red and green, or have no color."));

        validate(map, Iterators.getLast(keyIterator), "905.6", "Once the starting player has been determined, each player sets his or her life total to 20 and draws a hand of seven cards.");
    }

    @Test
    public void getGlossary() {
        Map<String, Iterable<String>> map = rulesParser.getGlossary();

        assertNotNull(map);

        Set<String> keys = map.keySet();

        String key = "Abandon";
        assertTrue(keys.contains(key));
        Iterable<String> values = map.get(key);
        assertNotNull(values);
        Iterator<String> valueIterator = values.iterator();
        assertTrue(valueIterator.hasNext());
        String value = valueIterator.next();
        assertThat(value, is("To turn a face-up ongoing scheme card face down and put it on the bottom of its owner’s scheme deck. See rule 701.23, “Abandon.”"));
        assertFalse(valueIterator.hasNext());

        key = "Ability";
        assertTrue(keys.contains(key));
        values = map.get(key);
        assertNotNull(values);
        valueIterator = values.iterator();
        assertTrue(valueIterator.hasNext());
        value = valueIterator.next();
        assertThat(value, is("1. Text on an object that explains what that object does or can do."));
        assertTrue(valueIterator.hasNext());
        value = valueIterator.next();
        assertThat(value, is("2. An activated or triggered ability on the stack. This kind of ability is an object."));
        assertTrue(valueIterator.hasNext());
        value = valueIterator.next();
        assertThat(value, is("See rule 112, “Abilities,” and section 6, “Spells, Abilities, and Effects.”"));
        assertFalse(valueIterator.hasNext());

        key = "Zone-Change Triggers";
        assertTrue(keys.contains(key));
        values = map.get(key);
        assertNotNull(values);
        valueIterator = values.iterator();
        assertTrue(valueIterator.hasNext());
        value = valueIterator.next();
        assertThat(value, is("Trigger events that involve objects changing zones. See rule 603.6."));
        assertFalse(valueIterator.hasNext());
    }

    private void validate(Map<String, String> map, String key, String expectedKey, String expectedValue) {
        assertThat(key, is(expectedKey));
        assertThat(map.get(key), is(expectedValue));
    }
}