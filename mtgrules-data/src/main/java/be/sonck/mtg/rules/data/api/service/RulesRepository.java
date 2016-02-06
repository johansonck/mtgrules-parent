package be.sonck.mtg.rules.data.api.service;

import be.sonck.mtg.rules.data.api.model.Rule;

import java.util.Optional;

/**
 * Created by johansonck on 05/09/15.
 */
public interface RulesRepository {
    Optional<Rule> getRule(String ruleId);
}
