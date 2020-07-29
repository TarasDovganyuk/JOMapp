package com.softserve.edu.jom.controller;

import com.softserve.edu.jom.model.User;
import com.softserve.edu.jom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class StudentController {
    private UserService userService;

    @Autowired
    public StudentController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/students")
    public String getUsers(Model model) {
        model.addAttribute("students", userService.getAllByRole("Trainee"));
        return "studentList";
    }

    @PostMapping("/addStudent")
    public String addStudent(Model model, @ModelAttribute(name = "student") User user) {
        try {
            user.setRole(User.Role.TRAINEE);
            userService.createOrUpdateUser(user);
            return "redirect:/students";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "changeStudent";
        }
    }

    @GetMapping("/addStudent")
    public String showAddStudentPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("mode", "add");
        return "changeStudent";
    }

    @GetMapping("/students/{marathon_id}")
    public String getStudentsByMarathonId(@PathVariable(name = "marathon_id") Long marathon_id, Model model) {
        List<User> userList = userService.getAllByRoleAndMarathonId(User.Role.TRAINEE.name(), marathon_id);
        model.addAttribute("students", userList);
        model.addAttribute("marathonId", marathon_id);
        return "studentList";
    }

    @GetMapping("/students/{marathon_id}/delete/{student_id}")
    public String getStudentsByMarathonId(@PathVariable(name = "marathon_id") Long marathon_id, @PathVariable(name = "student_id") Long student_id) {
        userService.removeUserFromMarathon(student_id, marathon_id);
        return "redirect:/students/" + marathon_id;
    }

    @PostMapping("/students/{marathon_id}/add")
    public String saveStudentToMarathon(@ModelAttribute(name = "student") User user, @PathVariable(name = "marathon_id") Long marathon_id) {
        user.setRole(User.Role.TRAINEE);
        userService.addUserToMarathon(user, marathon_id);
        return "redirect:/students/" + marathon_id;
    }

    @GetMapping("/students/{marathon_id}/add")
    public String addStudentToMarathon(Model model, @PathVariable(name = "marathon_id") Long marathon_id) {
        User user = new User();
        model.addAttribute("user", user);
        return "changeStudent";
    }

    @GetMapping("/students/{marathon_id}/edit/{student_id}")
    public String showEditStudentPage(Model model, @PathVariable(name = "marathon_id") Long marathon_id, @PathVariable(name = "student_id") Long student_id) {
        User user = userService.getUserById(student_id);
        model.addAttribute("user", user);
        model.addAttribute("mode", "edit");
        return "changeStudent";
    }

    @PostMapping("/students/{marathon_id}/edit/{student_id}")
    public String editStudentPage(Model model, @PathVariable(name = "marathon_id") Long marathon_id, @PathVariable(name = "student_id") Long student_id, @ModelAttribute(name = "student") User user) {
        user.setId(student_id);
        user.setRole(User.Role.TRAINEE);
        userService.createOrUpdateUser(user);
        return "redirect:/students";
    }
}
