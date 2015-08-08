package be.sonck.mtg.rules.impl.service;

import be.sonck.mtg.rules.api.model.Rule;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by johansonck on 14/07/15.
 */
public class RulesRepository {

    private final RuleIdInterpreter ruleIdInterpreter = new RuleIdInterpreter();
    private final Map<String, Rule> rules = new HashMap<>();


    public RulesRepository(RulesParser rulesParser) {
        initialize(rulesParser);
    }

    public Optional<Rule> getRule(String ruleId) {
        return Optional.ofNullable(rules.get(ruleId));
    }

    private void initialize(RulesParser rulesParser) {
        Map<String, String> simpleRules = rulesParser.getRules();

        for (Map.Entry<String, String> entry : simpleRules.entrySet()) {
            Rule rule = new Rule(entry.getKey(), entry.getValue());

            rule.setParent(getParentRule(rule));

            rules.put(rule.getId(), rule);
        }
    }

    private Rule getParentRule(Rule rule) {
        Optional<String> optionalParentId = ruleIdInterpreter.getParentId(rule.getId());
        if (optionalParentId.isPresent() && rules.containsKey(optionalParentId.get())) {
            return rules.get(optionalParentId.get());
        }

        return null;
    }
}
