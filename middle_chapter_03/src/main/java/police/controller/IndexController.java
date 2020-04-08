package police.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import police.model.Accident;
import police.repository.AccidentRepository;

import java.util.List;

/**
 * контроллер для главной страницы
 */
@Controller
public class IndexController {

    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class.getName());

    private final AccidentRepository repository = AccidentRepository.getInstance();

    @RequestMapping(value = "/police", method = RequestMethod.GET)
    public String getAccidents(ModelMap modelMap) {
        List<Accident> accidentsList = repository.getAccidents();
        modelMap.addAttribute("accidentsList", accidentsList);
        return "index";
    }

}
