# Finalproject

## Run project





## TBD: 3 layers
1. Controller (API endpoints)
2. Service (business logic)
3. repository 
4. DB models


## SQL Tables

Participant Table:
```
CREATE TABLE Participant (
    PID INT NOT NULL AUTO_INCREMENT,
    MID INT NOT NULL,
    UID INT NOT NULL,
    role ENUM('participant', 'organizer') DEFAULT NULL,
    join_at TIMESTAMP DEFAULT NULL,
    status ENUM('decline', 'accept', 'waiting') DEFAULT NULL,
    PRIMARY KEY (PID),
    FOREIGN KEY (UID) REFERENCES User(UID),
    FOREIGN KEY (MID) REFERENCES Meeting(MID)
);
```
Meeting table:
```
CREATE TABLE Meeting (
    MID INT NOT NULL AUTO_INCREMENT,
    organizer_id INT NOT NULL,
    type ENUM('group', 'one_on_one') DEFAULT NULL,
    description TEXT DEFAULT NULL,
    start_time TIMESTAMP DEFAULT NULL,
    end_time TIMESTAMP DEFAULT NULL,
    recurrence ENUM('daily', 'weekly', 'monthly', 'none') DEFAULT NULL,
    created_at TIMESTAMP DEFAULT NULL,
    num_invite_participant INT DEFAULT NULL,
    num_accept_participant INT DEFAULT NULL,
    status ENUM('Valid', 'Invalid') DEFAULT NULL,
    PRIMARY KEY (MID),
    FOREIGN KEY (organizer_id) REFERENCES User(UID)
);
```
User table:
```
CREATE TABLE User (
    UID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) DEFAULT NULL,
    email VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (UID)
);
```

TimeSlot table:
```
CREATE TABLE TimeSlot (
    TID INT AUTO_INCREMENT PRIMARY KEY,
    UID INT DEFAULT NULL,
    day ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday') DEFAULT NULL,
    start_time TIME DEFAULT NULL,
    end_time TIME DEFAULT NULL,
    availability ENUM('available', 'busy') DEFAULT NULL
    FOREIGN KEY (UID) REFERENCES User(UID)
);
```


Generate all tables:
```
CREATE TABLE User (
    UID INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) DEFAULT NULL,
    email VARCHAR(100) DEFAULT NULL,
    PRIMARY KEY (UID)
);
CREATE TABLE TimeSlot (
    TID INT AUTO_INCREMENT PRIMARY KEY,
    UID INT DEFAULT NULL,
    day ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday') DEFAULT NULL,
    start_time TIME DEFAULT NULL,
    end_time TIME DEFAULT NULL,
    availability ENUM('available', 'busy') DEFAULT NULL,
    FOREIGN KEY (UID) REFERENCES User(UID)
);
CREATE TABLE Meeting (
    MID INT NOT NULL AUTO_INCREMENT,
    organizer_id INT NOT NULL,
    type ENUM('group', 'one_on_one') DEFAULT NULL,
    description TEXT DEFAULT NULL,
    start_time TIMESTAMP DEFAULT NULL,
    end_time TIMESTAMP DEFAULT NULL,
    recurrence ENUM('daily', 'weekly', 'monthly', 'none') DEFAULT NULL,
    created_at TIMESTAMP DEFAULT NULL,
    num_invite_participant INT DEFAULT NULL,
    num_accept_participant INT DEFAULT NULL,
    status ENUM('Valid', 'Invalid') DEFAULT NULL,
    PRIMARY KEY (MID),
    FOREIGN KEY (organizer_id) REFERENCES User(UID)
);
CREATE TABLE Participant (
    PID INT NOT NULL AUTO_INCREMENT,
    MID INT NOT NULL,
    UID INT NOT NULL,
    role ENUM('participant', 'organizer') DEFAULT NULL,
    join_at TIMESTAMP DEFAULT NULL,
    status ENUM('decline', 'accept', 'waiting') DEFAULT NULL,
    PRIMARY KEY (PID),
    FOREIGN KEY (UID) REFERENCES User(UID),
    FOREIGN KEY (MID) REFERENCES Meeting(MID)
);

```