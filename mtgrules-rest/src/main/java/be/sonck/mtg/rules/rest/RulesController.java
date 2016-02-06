package be.sonck.mtg.rules.rest;

import be.sonck.mtg.rules.data.api.service.RulesRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by johansonck on 09/08/15.
 */
@RestController
public class RulesController {

    RulesRepository rulesRepository;

    @RequestMapping("/rules/{id}")
    public String getRule(@PathVariable String id) {
        rulesRepository.getRule(id);

        return id;
    }
}
