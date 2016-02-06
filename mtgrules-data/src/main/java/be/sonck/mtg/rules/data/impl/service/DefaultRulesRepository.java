package be.sonck.mtg.rules.data.impl.service;

import be.sonck.mtg.rules.data.api.model.Rule;
import be.sonck.mtg.rules.data.api.service.RulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by johansonck on 14/07/15.
 */
@Component("rulesRepository")
public class DefaultRulesRepository implements RulesRepository {

    private final RuleIdInterpreter ruleIdInterpreter = new RuleIdInterpreter();
    private final Map<String, Rule> rules = new HashMap<>();


    @Autowired
    public DefaultRulesRepository(RulesParser rulesParser) {
        initialize(rulesParser);
    }

    @Override public Optional<Rule> getRule(String ruleId) {
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
