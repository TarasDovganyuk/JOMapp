package com.softserve.edu.jom;

import com.softserve.edu.jom.model.*;
import com.softserve.edu.jom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@SpringBootApplication
public class JomApplication implements CommandLineRunner {
    private UserService userService;
    private MarathonService marathonService;
    private SprintService sprintService;
    private TaskService taskService;
    private ProgressService progressService;


    public static void main(String[] args) {
        SpringApplication.run(JomApplication.class, args);
    }

    @Transactional
    public void run(String... arg) throws Exception {
        addMentors();
        addStudents();
        getAllStudents();
        addUpdateDeleteMarathons();
        addSprintsToMarathon(1L);
        addSprintsToMarathon(3L);
        addTasksToSprint(1L);
        addTasksToSprint(2L);
        addTasksForStudent();
        updateProgress();
        setStatusToProgress();
        addUserToMarathon();
        getAllProgressByUserIdMarathonId();
        getAllProgressByUserIdSprintId();
    }

    private void addMentors() {
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
    }

    private void addStudents() {
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setPassword("qwertyqwert123");
            user.setRole(User.Role.TRAINEE);
            user.setFirstName("TraineeName" + i);
            user.setLastName("TraineeSurname" + i);
            user.setEmail("traineeUser" + i + "@gmail.com");
            userService.createOrUpdateUser(user);
        }
        User user1 = userService.getUserById(13L);
        System.out.println(user1);
    }


    private void addUpdateDeleteMarathons() {
        for (int i = 0; i < 5; i++) {
            Marathon marathon = new Marathon();
            marathon.setTitle("Marathon" + i);
            marathonService.createOrUpdate(marathon);
        }
        Marathon marathon = marathonService.getMarathonById(1L);
        System.out.println(marathon);
        marathon.setTitle("MarathonFirst");
        marathonService.createOrUpdate(marathon);

        System.out.println(marathonService.getMarathonById(1L));
        System.out.println(marathonService.getAll());

        marathonService.deleteMarathonById(2L);
        System.out.println(marathonService.getAll());
    }

    private void addUserToMarathon() {
        userService.addUserToMarathon(userService.getUserById(11L), marathonService.getMarathonById(1L));
        userService.addUserToMarathon(userService.getUserById(12L), marathonService.getMarathonById(1L));
        userService.addUserToMarathon(userService.getUserById(13L), marathonService.getMarathonById(1L));
        userService.addUserToMarathon(userService.getUserById(11L), marathonService.getMarathonById(3L));
        userService.addUserToMarathon(userService.getUserById(12L), marathonService.getMarathonById(3L));
        userService.addUserToMarathon(userService.getUserById(13L), marathonService.getMarathonById(3L));
    }

    private void getAllStudents() {
        System.out.println(userService.getAllByRole("Trainee"));
    }

    private void addSprintsToMarathon(Long marathonId) {
        for (int i = 0; i < 5; i++) {
            Sprint sprint = new Sprint();
            sprint.setTitle("Sprint" + i);
            sprint.setStartDate(LocalDateTime.now());
            sprint.setFinish(LocalDateTime.now());
            sprintService.addSprintToMarathon(sprint, marathonService.getMarathonById(marathonId));
        }


        System.out.println(sprintService.getSprintById(1L));
    }

    private void addTasksToSprint(Long sprintId) {
        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            task.setTitle("task " + i + sprintId);
            taskService.addTaskToSprint(task, sprintService.getSprintById(sprintId));
        }

        System.out.println(taskService.getTaskById(1L));
    }

    private void addTasksForStudent() {
        progressService.addTaskForStudent(taskService.getTaskById(1L), userService.getUserById(11L));
        progressService.addTaskForStudent(taskService.getTaskById(2L), userService.getUserById(11L));
        progressService.addTaskForStudent(taskService.getTaskById(3L), userService.getUserById(11L));

        progressService.addTaskForStudent(taskService.getTaskById(11L), userService.getUserById(12L));
        progressService.addTaskForStudent(taskService.getTaskById(12L), userService.getUserById(12L));
        progressService.addTaskForStudent(taskService.getTaskById(13L), userService.getUserById(12L));
    }

    private void updateProgress() {
        Progress progress = progressService.getProgressById(1L);
        progress.setTask(taskService.getTaskById(5L));
        progressService.addOrUpdateProgress(progress);
        System.out.println(progressService.getProgressById(1L));
    }

    private void setStatusToProgress() {
        Progress progress = progressService.getProgressById(1L);
        progress.setStatus(Progress.TaskStatus.PASS);
        progressService.setStatus(Progress.TaskStatus.PASS, progress);
        System.out.println(progressService.getProgressById(1L));
    }


    private void getAllProgressByUserIdMarathonId() {
        System.out.println(progressService.allProgressByUserIdAndMarathonId(11L, 3L));
    }

    private void getAllProgressByUserIdSprintId() {
        System.out.println(progressService.allProgressByUserIdAndSprintId(12L, 1L));
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMarathonService(MarathonService marathonService) {
        this.marathonService = marathonService;
    }

    @Autowired
    public void setSprintService(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setProgressService(ProgressService progressService) {
        this.progressService = progressService;
    }
}
