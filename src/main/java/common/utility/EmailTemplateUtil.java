package common.utility;

public enum EmailTemplateUtil {
  AH_REMINDER(1, "Award Holder Reminder"), APPOINTMENT_BOOKING(2,
      "Appointment Booking"), PERSONAL_AND_AWARDS_DETAILS(3,
          "Personal & Awards Details"), SURETY_1_DETAILS(4,
              "Surety 1 Details");

  private int index;
  private String description;

  EmailTemplateUtil(int index, String description) {
    this.index = index;
    this.description = description;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
