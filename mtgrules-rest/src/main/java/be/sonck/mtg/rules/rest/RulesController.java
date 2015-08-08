package be.sonck.mtg.rules.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by johansonck on 09/08/15.
 */
@Controller
@RequestMapping("/rules")
public class RulesController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getRule(@PathVariable String ruleId, ModelMap model) {

        model.addAttribute("movie", ruleId);
        return "list";
    }
}
