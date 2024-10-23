/* insert values table projects */;
INSERT INTO allocationmanager.projects VALUES
(UUID_TO_BIN(UUID()), '2025-06-01 10:00:00.000000', 'External Grant', '2024-09-01 10:00:00.000000', 'Web Application Development', 'John Doe', 200, 75000),
(UUID_TO_BIN(UUID()), '2025-05-20 09:00:00.000000', 'Internal Budget', '2024-10-05 09:00:00.000000', 'Cloud Infrastructure Upgrade', 'Sophia Martinez', 250, 100000),
(UUID_TO_BIN(UUID()), '2025-12-01 15:30:00.000000', 'Government Funding', '2024-08-15 15:30:00.000000', 'Data Science Initiative', 'Robert Brown', 300, 120000),
(UUID_TO_BIN(UUID()), '2025-01-10 23:32:58.336000', 'Internal Budget', '2024-10-10 23:32:58.336000', 'AI Development Project', 'Alice Johnson', 120, 50000),
(UUID_TO_BIN(UUID()), '2025-03-30 14:00:00.000000', 'Private Donation', '2024-09-15 14:00:00.000000', 'AI Research Study', 'Liam Williams', 180, 60000),
(UUID_TO_BIN(UUID()), '2025-02-15 12:00:00.000000', 'Corporate Sponsorship', '2024-11-01 12:00:00.000000', 'Mobile App Launch', 'Emily Smith', 150, 90000);

/* insert values table employees */;
INSERT INTO allocationmanager.employees VALUES
(UUID_TO_BIN(UUID()), 'alice.johnson@example.com', 'Project Manager', 'Alice Johnson', 'Master\'s in Business Administration', 'Agile Management, Risk Assessment', 75000, 3600),
(UUID_TO_BIN(UUID()), 'sophia.wilson@example.com', 'DevOps Engineer', 'Sophia Wilson', 'Bachelor\'s in Information Technology', 'Cloud Services, CI/CD Pipelines', 70000, 1800),
(UUID_TO_BIN(UUID()), 'michael.brown@example.com', 'UI/UX Designer', 'Michael Brown', 'Bachelor\'s in Design', 'User Research, Prototyping', 65000, 5400),
(UUID_TO_BIN(UUID()), 'emily.davis@example.com', 'Data Analyst', 'Emily Davis', 'Bachelor\'s in Statistics', 'Data Visualization, Predictive Analytics', 55000, 7200),
(UUID_TO_BIN(UUID()), 'james.taylor@example.com', 'Systems Administrator', 'James Taylor', 'Bachelor\'s in Network Administration', 'Server Management, Network Security', 58000, 3600),
(UUID_TO_BIN(UUID()), 'john.smith@example.com', 'Software Engineer', 'John Smith', 'Bachelor\'s in Computer Science', 'Machine Learning, Web Development', 60000, 0);