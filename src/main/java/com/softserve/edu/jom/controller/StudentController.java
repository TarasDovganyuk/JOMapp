package com.softserve.edu.jom.controller;

import com.softserve.edu.jom.model.Marathon;
import com.softserve.edu.jom.model.User;
import com.softserve.edu.jom.service.MarathonService;
import com.softserve.edu.jom.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Controller
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    private UserService userService;
    private MarathonService marathonService;

    @Autowired
    public StudentController(UserService userService, MarathonService marathonService) {
        this.userService = userService;
        this.marathonService = marathonService;
    }

    @GetMapping("/students")
    public String getUsers(Model model) {
        logger.info("Get all students");
        model.addAttribute("students", userService.getAllByRole("Trainee"));
        return "students";
    }

    @PostMapping("/addStudent")
    public String addStudent(Model model, @ModelAttribute(name = "student") User user) {
        logger.info("Add new student %s %s", user.getFirstName(), user.getLastName());
        user.setRole(User.Role.TRAINEE);
        userService.createOrUpdateUser(user);
        return "redirect:/students";

    }

    @GetMapping("/addStudent")
    public String showAddStudentPage(Model model) {
        logger.info("Show add new student page");
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("mode", "Add");
        return "changeStudent";
    }

    @GetMapping("/students/{marathon_id}")
    public String getStudentsByMarathonId(@PathVariable(name = "marathon_id") Long marathon_id, Model model) {
        logger.info("Get all students for marathon with id=%d", marathon_id);
        checkMarathonExists(marathon_id);
        List<User> userList = userService.getAllByRoleAndMarathonId(User.Role.TRAINEE.name(), marathon_id);
        model.addAttribute("students", userList);
        model.addAttribute("marathonId", marathon_id);
        return "students";
    }

    @GetMapping("/students/{marathon_id}/delete/{student_id}")
    public String deleteStudentFromMarathon(@PathVariable(name = "marathon_id") Long marathon_id, @PathVariable(name = "student_id") Long student_id) {
        logger.info("Remove student with id=%d from marathon with id=%d", student_id, marathon_id);
        checkMarathonExists(marathon_id);
        checkStudentExists(student_id);
        if (userService.getStudentById(student_id) == null) {
            throw new EntityNotFoundException(String.format("Student with id=%d not found!", student_id));
        }
        userService.removeUserFromMarathon(student_id, marathon_id);
        return "redirect:/students/" + marathon_id;
    }

    @PostMapping("/students/{marathon_id}/add")
    public String saveStudentToMarathon(@ModelAttribute(name = "student") User user, @PathVariable(name = "marathon_id") Long marathon_id) {
        logger.info("Add new student to marathon with id=%d", marathon_id);
        checkMarathonExists(marathon_id);
        user.setRole(User.Role.TRAINEE);
        userService.addUserToMarathon(user, marathon_id);
        return "redirect:/students/" + marathon_id;
    }

    @GetMapping("/students/{marathon_id}/add")
    public String addStudentToMarathon(Model model, @PathVariable(name = "marathon_id") Long marathon_id) {
        logger.info("Show page to add new student to marathon with id=%d", marathon_id);
        checkMarathonExists(marathon_id);
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("marathonId", marathon_id);
        model.addAttribute("mode", "Add");
        return "changeStudent";
    }

    @GetMapping("/students/{marathon_id}/edit/{student_id}")
    public String showEditStudentPage(Model model, @PathVariable(name = "marathon_id") Long marathon_id, @PathVariable(name = "student_id") Long student_id) {
        logger.info("Show edit student page for student with id=%d and marathon with id=%d", student_id, marathon_id);
        checkMarathonExists(marathon_id);
        User user = checkStudentExists(student_id);
        model.addAttribute("user", user);
        model.addAttribute("mode", "Edit");
        model.addAttribute("marathonId", marathon_id);
        return "changeStudent";
    }

    @PostMapping("/students/{marathon_id}/edit/{student_id}")
    public String editStudentPage(Model model, @PathVariable(name = "marathon_id") Long marathon_id, @PathVariable(name = "student_id") Long student_id, @ModelAttribute(name = "student") User user) {
        logger.info("Edit student with id=%d and marathon with id=%d", student_id, marathon_id);
        checkMarathonExists(marathon_id);
        checkStudentExists(student_id);
        user.setId(student_id);
        user.setRole(User.Role.TRAINEE);
        userService.createOrUpdateUser(user);
        return "redirect:/students/" + marathon_id;
    }

    private Marathon checkMarathonExists(Long marathon_id) {
        Marathon marathon = marathonService.getMarathonById(marathon_id);
        if (marathon == null) {
            throw new EntityNotFoundException(String.format("Marathon with id=%d not found!", marathon_id));
        }
        return marathon;
    }

    private User checkStudentExists(Long student_id) {
        User student = userService.getStudentById(student_id);
        if (student == null) {
            throw new EntityNotFoundException(String.format("Student with id=%d not found!", student_id));
        }
        return student;
    }
}
