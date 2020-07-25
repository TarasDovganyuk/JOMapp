package com.softserve.edu.jom;

import com.softserve.edu.jom.model.Marathon;
import com.softserve.edu.jom.model.Sprint;
import com.softserve.edu.jom.model.Task;
import com.softserve.edu.jom.model.User;
import com.softserve.edu.jom.service.MarathonService;
import com.softserve.edu.jom.service.SprintService;
import com.softserve.edu.jom.service.TaskService;
import com.softserve.edu.jom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class JomApplication implements CommandLineRunner {
    private UserService userService;
    private MarathonService marathonService;
    private SprintService sprintService;
    private TaskService taskService;

    @Autowired
    public JomApplication(UserService userService, MarathonService marathonService,
                          SprintService sprintService, TaskService taskService) {
        this.userService = userService;
        this.marathonService = marathonService;
        this.sprintService = sprintService;
        this.taskService = taskService;
    }

    public static void main(String[] args) {
        SpringApplication.run(JomApplication.class, args);
    }

    public void run(String... arg) throws Exception {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setPassword("qwertyqwert123");
            user.setRole(User.Role.MENTOR);
            user.setFirstName("MentorName" + i);
            user.setLastName("MentorSurname" + i);
            user.setEmail("mentoruser" + i + "@gmail.com");
            userService.createOrUpdateUser(user);

            User user1 = userService.getUserById(1L);
            System.out.println(user1);

        }

        Marathon marathon = new Marathon();
        Marathon marathon2 = new Marathon();
        marathon.setTitle("Marathon1");
        marathonService.createOrUpdate(marathon);
        marathon.setTitle("Marathon11");
        marathonService.createOrUpdate(marathon);
        marathon2.setTitle("Marathon2");
        marathonService.createOrUpdate(marathon2);

        System.out.println(marathonService.getMarathonById(1L));
        System.out.println(marathonService.getAll());

        marathonService.deleteMarathonById(2L);
        System.out.println(marathonService.getAll());

        Sprint sprint = new Sprint();
        sprint.setTitle("Sprint1");
        sprint.setStartDate(LocalDate.now());
        sprint.setFinish(LocalDate.now());
        sprintService.addSprintToMarathon(sprint, marathon);

        System.out.println(sprintService.getSprintById(1L));

        Task task = new Task();
        task.setTitle("task1");
        taskService.addTaskToSprint(task, sprintService.getSprintById(1L));
        System.out.println(taskService.getTaskById(1L));
    }
}
