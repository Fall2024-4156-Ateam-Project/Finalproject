package dev.teamproject.timeslot;

/**
 * Comparator class for comparing TimeSlot objects.
 * Used to sort TimeSlot instances based on specific criteria.
 */
public class TimeSlotComparator implements java.util.Comparator<TimeSlot> {

  @Override
  public int compare(TimeSlot t1, TimeSlot t2) {
    int dayCompare = t1.getStartDay().compareTo(t2.getStartDay());
    if (dayCompare != 0) {
      return dayCompare;
    }
    // compare time iff they are on same day
    return t1.getStartTime().compareTo(t2.getStartTime());
  }    
}
