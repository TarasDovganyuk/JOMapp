INSERT INTO users (id, email, first_name, last_name, password, role)
VALUES (1, 'alex@gmail.com', 'Alex', 'Smith', 'qweee1', 'TRAINEE'),
       (2, 'mari@gmail.com', 'Mariana', 'Kuzma', 'qweee2', 'TRAINEE'),
       (3, 'anna@gmail.com', 'Anna', 'Korzun', 'qweee3', 'TRAINEE'),
       (4, 'victor@gmail.com', 'Victor', 'Crud', 'qweee', 'MENTOR'),
       (5, 'gari@gmail.com', 'Gari', 'Curl', 'qweee', 'MENTOR');

INSERT INTO marathon (id, title)
VALUES (1, 'Java Online Marathon'),
       (2, 'JavaScript Online Marathon'),
       (3, 'C# Online Marathon');

INSERT INTO marathon_user(marathon_id, user_id)
VALUES (1, 1),
       (1, 3),
       (1, 4),
       (2, 2),
       (2, 3),
       (2, 5);

INSERT INTO sprint(id, start_date, finish, title, marathon_id)
VALUES (1, '2020-07-05', '2020-07-08', 'Spring MVC', 1),
       (2, '2020-07-09', '2020-07-12', 'Unit testing. Logging', 1),
       (3, '2020-08-07', '2020-08-10', 'Spring. Exception handling', 1);

INSERT INTO task(id, created, title, updated, sprint_id)
VALUES (1, '2020-07-05', 'Quiz', '2020-07-06', 1),
       (2, '2020-07-05', 'Practical task', '2020-07-06', 1),
       (3, '2020-07-09', 'Quiz', '2020-07-10', 2);

INSERT INTO progress(id, started, status, updated, task_id, trainee_id)
VALUES (1, '2020-07-05', 'PENDING', '2020-07-05', 1, 1),
       (2, '2020-07-05', 'PASS', '2020-07-06', 1, 3),
       (3, '2020-07-05', 'PASS', '2020-07-09', 2, 1),
       (4, '2020-07-05', 'PASS', '2020-07-09', 2, 3),
       (5, '2020-07-09', 'FAIL', '2020-07-10', 3, 1);
