package com.softserve.edu.jom.controller;

import com.softserve.edu.jom.model.Marathon;
import com.softserve.edu.jom.service.MarathonService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        marathonService.deleteMarathonById(id);
        return "redirect:/marathons";
    }

    @GetMapping("/edit/{marathonId}")
    public String editMarathon(Model model, @PathVariable(name = "marathonId") Long id) {
        logger.info(String.format("Edit marathon with id %d", id));
        Marathon marathon = marathonService.getMarathonById(id);
        model.addAttribute("marathon", marathon);
        model.addAttribute("mode", "Edit");
        return "changeMarathon";
    }

    @PostMapping("/edit/{marathonId}")
    public String editMarathon(Model model, @PathVariable(name = "marathonId") Long id,
                               @ModelAttribute(name = "marathon") Marathon marathon) {
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
    public String addMarathon(@ModelAttribute(name = "marathon") Marathon marathon) {
        marathonService.createOrUpdate(marathon);
        logger.info(String.format("Marathon %s added", marathon.getTitle()));
        return "redirect:/marathons";
    }
}
