package dev.teamproject.meeting;

import static org.assertj.core.api.Assertions.assertThat;

import dev.teamproject.common.CommonTypes;
import dev.teamproject.meeting.MeetingDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalTime;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the MeetingDTO class, which is a Data Transfer
 * Object (DTO) representing a meeting. These tests ensure that the getters
 * and setters of the MeetingDTO class work correctly,
 * and that the validation annotations are applied properly.
 */
public class MeetingDtoTests {
  private static Validator validator;

  @BeforeAll
  static void setUpValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void testMeetingDtoGettersAndSetters() {
    // Arrange
    MeetingDto meetingDto = new MeetingDto();
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
    meetingDto.setMeetingId(meetingId);
    meetingDto.setOrganizerId(organizerId);
    meetingDto.setType(type);
    meetingDto.setDescription(description);
    meetingDto.setStatus(status);
    meetingDto.setRecurrence(recurrence);
    meetingDto.setStartTime(startTime);
    meetingDto.setEndTime(endTime);
    meetingDto.setStartDay(startDay);
    meetingDto.setEndDay(endDay);

    // Assert
    assertThat(meetingDto.getMeetingId()).isEqualTo(meetingId);
    assertThat(meetingDto.getOrganizerId()).isEqualTo(organizerId);
    assertThat(meetingDto.getType()).isEqualTo(type);
    assertThat(meetingDto.getDescription()).isEqualTo(description);
    assertThat(meetingDto.getStatus()).isEqualTo(status);
    assertThat(meetingDto.getRecurrence()).isEqualTo(recurrence);
    assertThat(meetingDto.getStartTime()).isEqualTo(startTime);
    assertThat(meetingDto.getEndTime()).isEqualTo(endTime);
    assertThat(meetingDto.getStartDay()).isEqualTo(startDay);
    assertThat(meetingDto.getEndDay()).isEqualTo(endDay);
  }

  @Test
  void testOrganizerIdIsNotNull() {
    // Arrange
    MeetingDto meetingDto = new MeetingDto();
    meetingDto.setMeetingId(101);
    meetingDto.setType("group");
    meetingDto.setDescription("Weekly team meeting");
    meetingDto.setStatus("valid");
    meetingDto.setRecurrence("weekly");
    meetingDto.setStartTime(LocalTime.of(9, 0));
    meetingDto.setEndTime(LocalTime.of(10, 0));
    meetingDto.setStartDay(CommonTypes.Day.Monday);
    meetingDto.setEndDay(CommonTypes.Day.Friday);

    // Act
    Set<ConstraintViolation<MeetingDto>> violations = validator.validate(meetingDto);

    // Assert
    assertThat(violations).isNotEmpty();
    assertThat(violations).anyMatch(
            violation -> violation.getPropertyPath().toString().equals("organizerId")
                    && violation.getMessage()
                            .equals("Organizer ID is required for saving the meeting")
    );
  }

  @Test
  void testValidMeetingDto() {
    // Arrange
    MeetingDto meetingDto = new MeetingDto();
    meetingDto.setMeetingId(101);
    meetingDto.setOrganizerId(202);
    meetingDto.setType("group");
    meetingDto.setDescription("Weekly team meeting");
    meetingDto.setStatus("valid");
    meetingDto.setRecurrence("weekly");
    meetingDto.setStartTime(LocalTime.of(9, 0));
    meetingDto.setEndTime(LocalTime.of(10, 0));
    meetingDto.setStartDay(CommonTypes.Day.Monday);
    meetingDto.setEndDay(CommonTypes.Day.Friday);

    // Act
    Set<ConstraintViolation<MeetingDto>> violations = validator.validate(meetingDto);

    // Assert
    assertThat(violations).isEmpty();
  }
}
