package dev.teamproject.meeting;

import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dev.teamproject.common.CommonTypes;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class MeetingDTOTests {
  private static Validator validator;

  @BeforeAll
  static void setUpValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testMeetingDTOGettersAndSetters() {
    // Arrange
    MeetingDTO meetingDTO = new MeetingDTO();
    Integer meetingId = 101;
    Integer organizerId = 202;
    String type = "group";
    String description = "Weekly team meeting";
    String status = "valid";
    String recurrence = "weekly";
    LocalTime startTime = LocalTime.of(9, 0);
    LocalTime endTime = LocalTime.of(10, 0);
    CommonTypes.Day startDay = CommonTypes.Day.Monday;
    CommonTypes.Day endDay = CommonTypes.Day.Friday;

    // Act
    meetingDTO.setMeetingId(meetingId);
    meetingDTO.setOrganizerId(organizerId);
    meetingDTO.setType(type);
    meetingDTO.setDescription(description);
    meetingDTO.setStatus(status);
    meetingDTO.setRecurrence(recurrence);
    meetingDTO.setStartTime(startTime);
    meetingDTO.setEndTime(endTime);
    meetingDTO.setStartDay(startDay);
    meetingDTO.setEndDay(endDay);

    // Assert
    assertThat(meetingDTO.getMeetingId()).isEqualTo(meetingId);
    assertThat(meetingDTO.getOrganizerId()).isEqualTo(organizerId);
    assertThat(meetingDTO.getType()).isEqualTo(type);
    assertThat(meetingDTO.getDescription()).isEqualTo(description);
    assertThat(meetingDTO.getStatus()).isEqualTo(status);
    assertThat(meetingDTO.getRecurrence()).isEqualTo(recurrence);
    assertThat(meetingDTO.getStartTime()).isEqualTo(startTime);
    assertThat(meetingDTO.getEndTime()).isEqualTo(endTime);
    assertThat(meetingDTO.getStartDay()).isEqualTo(startDay);
    assertThat(meetingDTO.getEndDay()).isEqualTo(endDay);
  }

  @Test
  void testOrganizerIdIsNotNull() {
    // Arrange
    MeetingDTO meetingDTO = new MeetingDTO();
    meetingDTO.setMeetingId(101);
    meetingDTO.setType("group");
    meetingDTO.setDescription("Weekly team meeting");
    meetingDTO.setStatus("valid");
    meetingDTO.setRecurrence("weekly");
    meetingDTO.setStartTime(LocalTime.of(9, 0));
    meetingDTO.setEndTime(LocalTime.of(10, 0));
    meetingDTO.setStartDay(CommonTypes.Day.Monday);
    meetingDTO.setEndDay(CommonTypes.Day.Friday);

    // Act
    Set<ConstraintViolation<MeetingDTO>> violations = validator.validate(meetingDTO);

    // Assert
    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(
        violation -> violation.getPropertyPath().toString().equals("organizerId") &&
        violation.getMessage().equals("Organizer ID is required for saving the meeting")
    );
  }

  @Test
  void testValidMeetingDTO() {
    // Arrange
    MeetingDTO meetingDTO = new MeetingDTO();
    meetingDTO.setMeetingId(101);
    meetingDTO.setOrganizerId(202);
    meetingDTO.setType("group");
    meetingDTO.setDescription("Weekly team meeting");
    meetingDTO.setStatus("valid");
    meetingDTO.setRecurrence("weekly");
    meetingDTO.setStartTime(LocalTime.of(9, 0));
    meetingDTO.setEndTime(LocalTime.of(10, 0));
    meetingDTO.setStartDay(CommonTypes.Day.Monday);
    meetingDTO.setEndDay(CommonTypes.Day.Friday);

    // Act
    Set<ConstraintViolation<MeetingDTO>> violations = validator.validate(meetingDTO);
    // Assert
    assertThat(violations).isEmpty();
  }
}
