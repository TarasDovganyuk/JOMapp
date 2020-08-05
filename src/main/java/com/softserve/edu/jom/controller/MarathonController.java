package com.softserve.edu.jom.controller;

import com.softserve.edu.jom.model.Marathon;
import com.softserve.edu.jom.service.MarathonService;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Data
public class MarathonController {
    private static final Logger logger = LoggerFactory.getLogger(MarathonController.class);
    private MarathonService marathonService;

    @Autowired
    public void setMarathonService(MarathonService marathonService) {
        this.marathonService = marathonService;
    }

    @GetMapping("/marathons")
    public String getAllMarathons(Model model) {
        logger.info("Get all marathons");
        List<Marathon> marathons = marathonService.getAll();
        model.addAttribute("marathons", marathons);
        return "marathons";
    }

    @GetMapping("/delete/{marathonId}")
    public String deleteMarathon(@PathVariable(name = "marathonId") Long id) {
        logger.info(String.format("delete marathon with id %d", id));
        if (marathonService.getMarathonById(id) == null) {
            throw new EntityNotFoundException(String.format("Marathon with id=%d not found!", id));
        }
        marathonService.deleteMarathonById(id);
        return "redirect:/marathons";
    }

    @GetMapping("/edit/{marathonId}")
    public String editMarathon(Model model, @PathVariable(name = "marathonId") Long id) {
        logger.info(String.format("Edit marathon with id %d", id));
        if (marathonService.getMarathonById(id) == null) {
            throw new EntityNotFoundException(String.format("Marathon with id=%d not found!", id));
        }
        Marathon marathon = marathonService.getMarathonById(id);
        model.addAttribute("marathon", marathon);
        model.addAttribute("mode", "Edit");
        return "changeMarathon";
    }

    @PostMapping("/edit/{marathonId}")
    public String editMarathon(Model model, @PathVariable(name = "marathonId") Long id,
                               @ModelAttribute(name = "marathon") @Valid Marathon marathon,
                               BindingResult bindingResult) {
        logger.info("Edit marathon with id=%d", id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("marathon", marathon);
            model.addAttribute("mode", "Edit");
            model.addAttribute("errorMessage", getErrorMessage(bindingResult));
            return "changeMarathon";
        }
        marathon.setId(id);
        marathonService.createOrUpdate(marathon);
        logger.info(String.format("Marathon with id %d title updated to %s", id, marathon.getTitle()));
        return "redirect:/marathons";
    }

    @GetMapping("/addMarathon")
    public String addMarathon(Model model) {
        logger.info("Add marathon");
        Marathon marathon = new Marathon();
        model.addAttribute("marathon", marathon);
        model.addAttribute("mode", "Add");
        return "changeMarathon";
    }

    @PostMapping("/addMarathon")
    public String addMarathon(Model model, @ModelAttribute(name = "marathon") @Valid Marathon marathon,
                              BindingResult bindingResult) {
        logger.info(String.format("Add %s marathon", marathon.getTitle()));
        if (bindingResult.hasErrors()) {
            model.addAttribute("marathon", marathon);
            model.addAttribute("mode", "Add");
            model.addAttribute("errorMessage", getErrorMessage(bindingResult));
            return "changeMarathon";
        }
        marathonService.createOrUpdate(marathon);
        return "redirect:/marathons";
    }

    private String getErrorMessage(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(";"));
    }
}
