package be.sonck.mtg.rules.data.impl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Created by johansonck on 12/07/15.
 */
@Component("rulesParser")
public class DefaultRulesParser implements RulesParser {

    private static final String GLOSSARY = "Glossary";
    public static final String CREDITS = "Credits";

    private String effectiveDate;
    private Map<String, String> rules = new LinkedHashMap<>();
    private Map<String, Iterable<String>> glossary = new TreeMap<>();


    @Autowired
    public DefaultRulesParser(Reader rulesReader) {
        initialize(rulesReader);
    }

    @Override
    public String getEffectiveDate() {
        return effectiveDate;
    }

    @Override
    public Map<String, String> getRules() {
        return rules;
    }

    @Override
    public Map<String, Iterable<String>> getGlossary() {
        return glossary;
    }

    private void initialize(Reader reader) {
        try (LineReader lineReader = new LineReader(reader)) {
            parseLines(lineReader);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseLines(Iterable<String> lines) throws IOException {
        Iterator<String> iterator = lines.iterator();

        readEffectiveDate(iterator);
        readRules(iterator);
        readGlosssary(iterator);
    }

    private void readGlosssary(Iterator<String> iterator) {
        while (iterator.hasNext()) {
            String line = iterator.next();

            if (isEmpty(line)) continue;
            if (CREDITS.equals(line)) break;

            String key = line;
            List<String> values = readGlossaryValues(iterator);

            glossary.put(key, values);
        }
    }

    private List<String> readGlossaryValues(Iterator<String> iterator) {
        List<String> values = new ArrayList<>();

        while (iterator.hasNext()) {
            String line = iterator.next();

            if (isEmpty(line)) break;

            values.add(line);
        }

        return values;
    }

    private void readRules(Iterator<String> iterator) {
        skipUntil(iterator, CREDITS);

        String lastKnownKey = null;

        while (iterator.hasNext()) {
            String line = iterator.next();

            if (isEmpty(line)) continue;
            if (GLOSSARY.equals(line)) break;

            if (isContinuationOfPreviousLine(line)) {
                rules.put(lastKnownKey, determineNewValue(lastKnownKey, line));
                continue;
            }

            lastKnownKey = determineKey(line);

            rules.put(lastKnownKey, determineValue(line));
        }
    }

    private String determineNewValue(String key, String line) {
        String previousText = rules.get(key);
        String additionalText = line.trim();

        return previousText + " " + additionalText;
    }

    private boolean isContinuationOfPreviousLine(String line) {
        return !line.matches("^[0-9].+");
    }

    private String determineValue(String line) {
        return line.substring(line.indexOf(' ') + 1);
    }

    private String determineKey(String line) {
        return line.substring(0, line.indexOf(' ')).replaceFirst("\\.$", "");
    }

    private void readEffectiveDate(Iterator<String> iterator) {
        while (iterator.hasNext()) {
            String line = iterator.next();

            if (isEffectiveDate(line)) {
                effectiveDate = line;
                break;
            }
        }
    }

    private void skipUntil(Iterator<String> iterator, String expectedLine) {
        while (iterator.hasNext()) {
            String line = iterator.next();

            if (line.equals(expectedLine)) return;
        }
    }

    private boolean isEffectiveDate(String line) {
        return line.startsWith("These rules are effective");
    }
}
