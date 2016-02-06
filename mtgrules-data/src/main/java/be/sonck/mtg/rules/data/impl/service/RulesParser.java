package be.sonck.mtg.rules.data.impl.service;

import java.util.Map;

/**
 * Created by johansonck on 06/09/15.
 */
public interface RulesParser {
    String getEffectiveDate();

    Map<String, String> getRules();

    Map<String, Iterable<String>> getGlossary();
}
