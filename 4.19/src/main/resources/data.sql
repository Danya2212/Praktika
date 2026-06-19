INSERT INTO person (id, firstname, surname, lastname, birthday) VALUES
(1, 'Ivan', 'Ivanov', 'Ivanovich', '1990-05-15'),
(2, 'Maria', 'Petrova', 'Sergeevna', '1995-08-22'),
(3, 'Alexey', 'Sidorov', 'Petrovich', '1988-12-03');

INSERT INTO message (id, title, text, time, person_id) VALUES
(1, 'Greeting', 'Welcome to the system!', '2026-06-18 10:00:00', 1),
(2, 'Reminder', 'Do not forget to submit the assignment.', '2026-06-18 12:30:00', 1),
(3, 'Update', 'Project migrated to H2.', '2026-06-18 14:45:00', 2);
