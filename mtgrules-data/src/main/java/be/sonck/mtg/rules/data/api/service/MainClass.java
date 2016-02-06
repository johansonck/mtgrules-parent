package be.sonck.mtg.rules.data.api.service;

import be.sonck.mtg.rules.data.api.model.Rule;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Optional;

/**
 * Created by johansonck on 05/09/15.
 */
public class MainClass {

    private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-beans.xml");

    public static void main(String[] args) {
        RulesRepository rulesRepository = (RulesRepository) applicationContext.getBean("rulesRepository");

        String ruleId = "1";

        Optional<Rule> rule = rulesRepository.getRule(ruleId);
        if (rule.isPresent()) {
            System.out.println(rule.get().getText());
        } else {
            System.out.println("no rule " + ruleId);
        }
    }
}
