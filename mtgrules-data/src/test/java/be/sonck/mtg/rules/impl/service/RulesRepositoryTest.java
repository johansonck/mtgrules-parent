package be.sonck.mtg.rules.impl.service;

import be.sonck.mtg.rules.api.model.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by johansonck on 14/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class RulesRepositoryTest {

    private static final String RULE_1 = "rule1";
    private static final String RULE_2 = "rule2";
    private static final String RULE_100 = "rule100";
    private static final String RULE_100_1 = "rule100_1";
    private static final String RULE_100_1A = "rule100_1a";
    private static final String RULE_100_1B = "rule100_1b";
    private static final String RULE_100_2 = "rule100_2";
    private static final String RULE_101 = "rule101";

    @Mock
    private RulesParser rulesParser;

    @Test
    public void getRule() {
        expectGetRules();

        validateRule(repository().getRule("1."), RULE_1);
        validateRule(repository().getRule("2."), RULE_2);
    }

    @Test
    public void getRuleWithChildren() {
        expectGetRules();

        Collection<Rule> children = repository().getRule("1.").get().getChildren();
        assertFalse(isEmpty(children));
        assertThat(children.size(), is(2));

        Iterator<Rule> ruleIterator = children.iterator();

        validateRule(ruleIterator.next(), "100.", RULE_100);
        validateRule(ruleIterator.next(), "101.", RULE_101);
    }

    @Test
    public void getRuleWithGrandChildren() {
        expectGetRules();

        Collection<Rule> grandChildren = repository().getRule("1.").get().getChildren().iterator().next().getChildren();

        Iterator<Rule> ruleIterator = grandChildren.iterator();

        validateRule(ruleIterator.next(), "100.1.", RULE_100_1);
        validateRule(ruleIterator.next(), "100.2.", RULE_100_2);
    }

    @Test
    public void getRuleWithGrandGrandChildren() {
        expectGetRules();

        Collection<Rule> grandGrandChildren = repository().getRule("1.").get().getChildren().iterator().next().getChildren().iterator().next().getChildren();

        Iterator<Rule> ruleIterator = grandGrandChildren.iterator();

        validateRule(ruleIterator.next(), "100.1a", RULE_100_1A);
        validateRule(ruleIterator.next(), "100.1b", RULE_100_1B);
    }

    @Test
    public void getDeepRule() {
        expectGetRules();

        validateRule(repository().getRule("100.1a"), RULE_100_1A);
    }

    private void expectGetRules() {
        Map<String, String> rules = new StringHashMapBuilder()
                .entry("1.", RULE_1)
                .entry("100.", RULE_100)
                .entry("100.1.", RULE_100_1)
                .entry("100.1a", RULE_100_1A)
                .entry("100.1b", RULE_100_1B)
                .entry("100.2.", RULE_100_2)
                .entry("101.", RULE_101)
                .entry("2.", RULE_2)
                .build();

        expectGetRules(rules);
    }

    private void expectGetRules(Map<String, String> map) {
        Mockito.when(rulesParser.getRules()).thenReturn(map);
    }

    private void validateRule(Optional<Rule> optionalRule, String ruleText) {
        assertTrue(optionalRule.isPresent());
        assertThat(optionalRule.get().getText(), is(ruleText));
    }

    private void validateRule(Rule rule, String ruleId, String ruleText) {
        assertNotNull(rule);
        assertThat(rule.getId(), is(ruleId));
        assertThat(rule.getText(), is(ruleText));
    }

    private RulesRepository repository() {
        return new RulesRepository(rulesParser);
    }
}