# Finalproject

## Continuous Integration:
Here are the reports of CI histories: https://github.com/Fall2024-4156-Ateam-Project/Finalproject/actions/workflows/maven.yml

## Deployment:
The service has been deployed on: http://34.138.31.157:8080/

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

### API docs
- /api-docs (yaml docs)
- /swagger-ui/index.html (UI docs)

### /api/v1/meetings
------------------------------------------------------------------------------------------------------------------------
#### GET /findByStatus
• **Expected Input**: status(string but should match to valid, invalid, none)    
• **Expected Output**: the meeting matched with the status 
• **Upon Success**: HTTP 200 Status Code is returned along with the participant in the response body

#### GET /findByEmail
• **Expected Input**: email(string)    
• **Expected Output**: the meeting whose organizer matched with the email 
• **Upon Success**: HTTP 200 Status Code is returned along with the participant in the response body

#### GET /findByRecurrence
• **Expected Input**: recurrence(string but should match to daily, weekly, monthly, none)    
• **Expected Output**: all the meetings matched with the recurrence  
• **Upon Success**: HTTP 200 Status Code is returned along with the participant in the response body

#### GET /findByType
• **Expected Input**: type(string but should match to group, one_on_one)    
• **Expected Output**:  all the meetings matched with the type
• **Upon Success**: HTTP 200 Status Code is returned along with the participant in the response body

#### GET /findById
• **Expected Input**: id(int)    
• **Expected Output**:  the meeting matched with the id
• **Upon Success**: HTTP 200 Status Code is returned along with the participant in the response body

#### GET /get_all
• **Expected Input**: N/A.    
• **Expected Output**: All meetings in descending order in JSON   
• **Upon Success**: HTTP 200 Status Code is returned along with all participants in the response body

#### GET /findByOrganizer
• **Expected Input**: organizer(user in JSON type).    
• **Expected Output**: All meetings matched with the organizer  
• **Upon Success**: HTTP 200 Status Code is returned along with all participants in the response body

#### DELETE /
• **Expected Input**: meetingID(meeting in JSON type).    
• **Expected Output**: N/A.   
• **Upon Success**: HTTP 200 Status Code is returned

#### POST /saveMeeting
• **Expected Input**: meeting(meeting in JSON type).    
• **Expected Output**: A message indicating complete meeting object saved 
• **Upon Success**: HTTP 200 Status Code is returned

### /api/v1/participants
------------------------------------------------------------------------------------------------------------------------
#### POST /register
• **Expected Input**: a participant object in JSON.    
• **Expected Output**: N/A.    
• **Upon Success**: HTTP 200 Status Code is returned 

#### DELETE /
• **Expected Input**: a participant id.    
• **Expected Output**: N/A.      
• **Upon Success**: HTTP 200 Status Code is returned 

#### GET /findById
• **Expected Input**: id(int).    
• **Expected Output**: the participant matched the input id    
• **Upon Success**: HTTP 200 Status Code is returned along with the participant in the response body

#### GET /findAll
• **Expected Input**: N/A.    
• **Expected Output**: All participant in descending order in JSON   
• **Upon Success**: HTTP 200 Status Code is returned along with all participants in the response body

#### GET /findByMeeting
• **Expected Input**: mid(int)  
• **Expected Output**: All participant in JSON that is in this meeting if mid.  
• **Upon Success**: HTTP 200 Status Code is returned along with all participants in the response body

#### GET /findByUser
• **Expected Input**: user(user in JSON).   
• **Expected Output**: All participant in JSON that is the given user.   
• **Upon Success**: HTTP 200 Status Code is returned along with all participants in the response body

#### GET /findByStatus
• **Expected Input**: status(undecided, approved, rejected).    
• **Expected Output**: All participant in JSON that matches the status.  
• **Upon Success**: HTTP 200 Status Code is returned along with all participants in the response body

#### GET /findByRole
• **Expected Input**: role(participant, organizer).    
• **Expected Output**: All participant in JSON that matches the role.  
• **Upon Success**: HTTP 200 Status Code is returned along with all participants in the response body


### /api/v1/timeslots
------------------------------------------------------------------------------------------------------------------------
#### POST
• **Expected Input**: a timeslot object in JSON.    
• **Expected Output**: the complete user object added    
• **Upon Success**: HTTP 200 Status Code is returned 

#### POST /merge
• **Expected Input**: a timeslot object in JSON.    
• **Expected Output**: This endpoint allows a user to add a new timeslot into their existing schedule. The provided timeslot will be added to the user’s schedule, ensuring that it doesn’t conflict with existing timeslots. If conflicts occur, the overlapping timeslots are revised to accommodate the new timeslot.  
• **Upon Success**: HTTP 200 Status Code is returned


#### PUT /{id}
• **Expected Input**: a timeslot object in JSON.    
• **Expected Output**: This endpoint allows a user to update an existing timeslot in their schedule by replacing the target timeslot (identified by its id) with a new or modified timeslot. If the updated timeslot causes conflicts with other timeslots in the schedule, appropriate revisions will be made to ensure consistency.    
• **Upon Success**: HTTP 200 Status Code is returned


#### GET /{id}
• **Expected Input**: id(int).    
• **Expected Output**: the time slot matched the ID    
• **Upon Success**: HTTP 200 Status Code is returned 

#### GET /
• **Expected Input**: N/A.    
• **Expected Output**: all time slots in the descending order    
• **Upon Success**: HTTP 200 Status Code is returned 

#### GET /user
• **Expected Input**: Parameter, email(string).
• **Expected Output**: all time slot matched the input user email    
• **Upon Success**: HTTP 200 Status Code is returned 

#### GET /user/{uid}
• **Expected Input**: id(int).    
• **Expected Output**: all time slot matched the input user id    
• **Upon Success**: HTTP 200 Status Code is returned 

#### GET /day/{day}
• **Expected Input**: day(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday).    
• **Expected Output**: all time slot matched the input day   
• **Upon Success**: HTTP 200 Status Code is returned 

#### PUT /availability/{availability}
• **Expected Input**: availability(available, busy).    
• **Expected Output**: all time slot matched the input availability  
• **Upon Success**: HTTP 200 Status Code is returned 

#### DELETE /{id}
• **Expected Input**: id(int)    
• **Expected Output**: N/A.  
• **Upon Success**: HTTP 200 Status Code is returned 



### /api/v1/users
------------------------------------------------------------------------------------------------------------------------
#### POST /register
• **Expected Input**: a user object in JASON.    
• **Expected Output**: the complete user object added    
• **Upon Success**: HTTP 200 Status Code is returned along with the user in the response body

#### GET /findByName
• **Expected Input**: name(String).    
• **Expected Output**: the list of users matched with the input name       
• **Upon Success**: HTTP 200 Status Code is returned along with the users in the response body

#### GET /findById
• **Expected Input**: Id(int).    
• **Expected Output**: the user matched with the input ID    
• **Upon Success**: HTTP 200 Status Code is returned along with the user in the response body

#### GET /findByEmail
• **Expected Input**: email(String).    
• **Expected Output**: the list of users matched with the input email      
• **Upon Success**: HTTP 200 Status Code is returned along with the users in the response body

#### GET /get_all
• **Expected Input**: N/A  
• **Expected Output**: All users in the descending order 
• **Upon Success**: HTTP 200 Status Code is returned along with the users in the response body

#### DELETE /{id}
• **Expected Input**: id(int)    
• **Expected Output**: delete the user given the uid
• **Upon Success**: HTTP 200 Status Code is returned or 404 if the user is not found

### /api/v1/requests
-------------------------------------------------------------------------------------------------------------------------
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
    UNIQUE (email),
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
    UNIQUE (email),
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
ADD status ENUM('undecided', 'approved', 'rejected') NOT NULL DEFAULT 'undecided';
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
