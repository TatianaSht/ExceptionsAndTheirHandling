public class Person {

    private final String lastName;
    private final String firstName;
    private final String patronymic;
    private final String birthday;
    private final long phoneNumber;
    private final String gender;

    public Person(String lastName, String firstName, String patronymic, String birthday,
                  long phoneNumber, String gender) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }



    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getBirthday() {
        return birthday;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        if (this.getLastName() == null){
            return "Person doesn't exist.";
        } else
            return
                    "<Ф.И.О = " + lastName + " "
                            + firstName + " "
                            + patronymic + ", "
                            + "д.р. = " + birthday + ", "
                            + "тел. = " + phoneNumber + ", "
                            + "пол = " + gender + ">" + "\n";
    }
}
