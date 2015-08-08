package be.sonck.mtg.rules.impl.service;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by johansonck on 16/07/15.
 */
public class RuleIdInterpreter {

    // ^\d\.$
    // ^\d{3}\.$
    // ^\d{3}\.\d\.$
    // ^\d{3}\.\d[a-z]$
    private static String LEVEL2_PATTERN = "^\\d{3}\\.$";
    private static String LEVEL3_PATTERN = "^\\d{3}\\.\\d\\.$";
    private static String LEVEL4_PATTERN = "^\\d{3}\\.\\d[a-z]$";

    // 1.
    // 100.
    // 100.1.
    // 100.1a

    public Optional<String> getParentId(String ruleId) {
        checkNotNull(ruleId);

        return Optional.ofNullable(calculateParentId(ruleId));
    }

    private String calculateParentId(String ruleId) {
        if (isLevel2(ruleId)) return toLevel1(ruleId);
        if (isLevel3(ruleId)) return toLevel2(ruleId);
        if (isLevel4(ruleId)) return toLevel3(ruleId);

        return null;
    }

    private String toLevel1(String ruleId) {
        return ruleId.substring(0, 1) + ".";
    }

    private String toLevel2(String ruleId) {
        return ruleId.substring(0, 4);
    }

    private String toLevel3(String ruleId) {
        return ruleId.substring(0, ruleId.length() - 1) + ".";
    }

    private boolean isLevel2(String ruleId) {
        return ruleId.matches(LEVEL2_PATTERN);
    }

    private boolean isLevel3(String ruleId) {
        return ruleId.matches(LEVEL3_PATTERN);
    }

    private boolean isLevel4(String ruleId) {
        return ruleId.matches(LEVEL4_PATTERN);
    }
}
