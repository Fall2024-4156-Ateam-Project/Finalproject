# Finalproject

## Setup and Running

JDK 17: This project used JDK 17 for development: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html  
Installing maven: https://maven.apache.org/download.cgi  
Build with maven: ```mvn -B package --file teamproject/pom.xml```  
Running the application: ```mvn spring-boot:run -D"spring-boot.run.arguments=setup"```  
Running the test: ```mvn clean test```
Check the test coverage report inside teamproject folder: ```mvn jacoco:report```. The report path will be ./target/site/jacoco/index.html  
Running the style checker inside teamproject folder: ```mvn checkstyle:check```  


## Architechture: 3 layers
1. Controller (API endpoints)
2. Service (business logic)
3. repository 
4. DB models

## Endpoints

### /api/v1/requests
#### POST/ 
• **Expected request body**: a request object in JSON.
{
    "user": {
        "uid": 1 
    },
    "timeSlot": {
        "tid": 2
    },
    "description": "Request 3",
    "status": "undecided"
}    
• **Expected Output**: the complete request object added    
• **Upon Success**: HTTP 200 Status Code is returned along with the request in the response body

#### GET /search
• **Expected Input Parameters**: tid (int) or requesterId (int)
• **Expected Output**: all requests with the given tid or rerquesterId
• **Upon Success**: HTTP 200 Status Code is returned along with the request in the response body
• **Upon Failure**: If no parameter is presented will return HTTP 400 status code with message: Invalid request: Please provide either 'userid' or 'tid'

#### GET /{userid}/{tid}
• **Expected Input Parameters**: N/A  
• **Expected Output**: The request object that has the given tid and uid.

#### PUT /description
• **Expected Input Parameters**:  tid (int) and userid (int) 
• **Expected request body**: a string representing the new description.
• **Expected Output**: The request object that has been updated.

#### PUT /status
• **Expected Input Parameters**:  tid (int) and userid (int) 
• **Expected request body**: a string representing the new status.
• **Expected Output**: The request object that has been updated.

#### DELETE /
• **Expected Input Parameters**:  tid (int) and userid (int) 
• **Expected Output**: HTTP 200 status code.

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
CREATE TABLE Request (
    requesterID INT NOT NULL,
    TID INT NOT NULL,
    description VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (requesterID, TID),
    FOREIGN KEY (requesterID) REFERENCES User(UID),
    FOREIGN KEY (TID) REFERENCES TimeSlot(TID)
);
ALTER TABLE Request
ADD status ENUM('UNDECIDED', 'APPROVED', 'REJECTED') NOT NULL DEFAULT 'UNDECIDED';

```

### Style Checking Report
Used the tool "checkstyle" to check the style of the code:

### Branch Coverage Reporting
Used JaCoCo to perform branch analysis to see the test coverage.

### Tools used

Maven Package Manager  
GitHub Actions CI  
Checkstyle  
PMD  
JUnit  
JaCoCo  
Postman  
