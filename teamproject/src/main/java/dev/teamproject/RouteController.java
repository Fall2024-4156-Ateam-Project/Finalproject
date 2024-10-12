package dev.teamproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class contains all the API routes for the system.
 */
@RestController
public class RouteController {

    /**
     * Redirects to the homepage.
     *
     * @return A String containing the name of the html file to be loaded.
     */
    @GetMapping({"/", "/index", "/home"})
    public String index() {
        return "Welcome, in order to make an API call direct your browser or Postman to an endpoint "
                + "\n\n This can be done using the following format: \n\n http:127.0.0"
                + ".1:8080/endpoint?arg=value";
    }


    /**
     * Returns the details of the specified department.
     *
     * @return A {@code ResponseEntity} object containing either the details of the Department and
     *         an HTTP 200 response or, an appropriate message indicating the proper response.
     */
    @GetMapping(value = "/retrieveMeeting", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> retrieveMeeting() {
        try {
            List<Object> objects = ProjectApplication.myFileDatabase.getObjects(); // Call method with parentheses
            List<Meeting> meetings = new ArrayList<>();

            // Filter to get only Meeting instances
            for (Object obj : objects) {
                if (obj instanceof Meeting) {
                    meetings.add((Meeting) obj);
                }
            }

            if (!meetings.isEmpty()) {
                return new ResponseEntity<>(meetings, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No meetings found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private ResponseEntity<?> handleException(Exception e) {
        System.out.println(e.toString());
        return new ResponseEntity<>("An Error has occurred", HttpStatus.OK);
    }


}