import myexceptions.DataLengthException;
import myexceptions.DateException;
import myexceptions.GenderException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class PersonProfileInput {

    public String[] inputPersonData() throws Exception {

        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("_____________________________________");
                System.out.println("""
                        Введите следующие данные в произвольном порядке в одну строку через пробел:
                        - Фамилия, Имя, Отчество (кириллица/латиница),\s
                        - Дата рождения (в формате dd.mm.yyyy),\s
                        - Номер телефона (11 цифр - без символов и пробелов),\s
                        - Пол (мужской - m/м, женский - f/ж)
                        """);
                String inputData = scanner.nextLine();
                String[] profileData = inputData.split(" ");
                checkProfileLength(profileData);
                return profileData;
            } catch (DataLengthException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public Person createProfile(String[] personData) throws Exception {
        HashMap<String, String> personProfile = new HashMap<>();
        List<String> fullName = new ArrayList<>();
        for (String profile : personData) {
            if (profile.length() == 1) {
                try {
                    checkProfileGender(profile);
                    personProfile.put("gender", profile);
                } catch (GenderException e) {
                    System.out.println(e.getMessage());
                }
            } else if (profile.matches("\\d{1,2}\\.\\d{1,2}\\.\\d{4}")) {
                try {
                    checkProfileBirthDay(profile);
                    personProfile.put("birthday", profile);
                } catch (DateException e) {
                    System.out.println(e.getMessage());
                }
            } else if (profile.matches("[78]9[0-9]{9}")) {
                personProfile.put("phoneNumber", profile);
            } else if ((profile.matches("[a-zA-Z-]{2,20}") || profile.matches("[а-яА-я-]{2,20}"))) {
                fullName.add(profile);
            } else {
                System.out.println("Ошибка в данных: <" + profile + ">. Профиль не сохранен, попробуйте еще раз.");
            }
        }

        if (fullName.size() != 3 || personProfile.size() != 3) {
            return null;
        } else {
            String lastName = fullName.get(0).substring(0, 1).toUpperCase() + fullName.get(0).substring(1);
            String firstName = fullName.get(1).substring(0, 1).toUpperCase() + fullName.get(1).substring(1);
            String patronymic = fullName.get(2).substring(0, 1).toUpperCase() + fullName.get(2).substring(1);
            String birthday = personProfile.get("birthday");
            long phoneNumber = Long.parseLong(personProfile.get("phoneNumber"));
            String gender = personProfile.get("gender").toLowerCase();
            return new Person(lastName, firstName, patronymic, birthday, phoneNumber, gender);
        }
    }


    public void savePersonProfileToFile(Person person) {
        if (person != null) {
            String filePath = "src/personfile/"
                    + person.getLastName() + ".txt";
            try (FileReader reader = new FileReader(filePath);
                 FileWriter fileNameProfile = new FileWriter(filePath, true)) {
                fileNameProfile.write("<" + person.getLastName() + "> ");
                fileNameProfile.write("<" + person.getFirstName() + "> ");
                fileNameProfile.write("<" + person.getPatronymic() + "> ");
                fileNameProfile.write("<" + person.getBirthday() + "> ");
                fileNameProfile.write("<" + person.getPhoneNumber() + "> ");
                fileNameProfile.write("<" + person.getGender() + ">");
                fileNameProfile.append("\n");
                System.out.println("Данные профиля " + person + "успешно записаны в файл <"
                        + person.getLastName() + ".txt>");
                fileNameProfile.flush();
            } catch (IOException e) {
                try (FileWriter fileNameProfile = new FileWriter(filePath, false)) {
                    fileNameProfile.write("<" + person.getLastName() + "> ");
                    fileNameProfile.write("<" + person.getFirstName() + "> ");
                    fileNameProfile.write("<" + person.getPatronymic() + "> ");
                    fileNameProfile.write("<" + person.getBirthday() + "> ");
                    fileNameProfile.write("<" + person.getPhoneNumber() + "> ");
                    fileNameProfile.write("<" + person.getGender() + ">");
                    fileNameProfile.append("\n");
                    System.out.println("Данные профиля " + person + "успешно записаны в файл <"
                            + person.getLastName() + ".txt>");
                    fileNameProfile.flush();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    public void checkProfileLength(String[] userData) throws Exception {
        if (userData.length < 6) {
            throw new DataLengthException("Указаны не все данные! Профиль не сохранен, попробуйте еще раз.");
        } else if (userData.length > 6) {
            throw new DataLengthException("Указаны лишние данные! Профиль не сохранен, попробуйте еще раз.");
        }
    }


    public void checkProfileGender(String gender) throws Exception {
        if (!gender.matches("[fmFMмжМЖ]")) {
            throw new GenderException("Ошибка в данных: <" + gender
                    + ">. Профиль не сохранен, попробуйте еще раз.");
        }
    }


    public void checkProfileBirthDay(String date) throws Exception {
        String[] splitDate = date.split("\\.");
        if (Integer.parseInt(splitDate[2]) < 1924 || Integer.parseInt(splitDate[2]) > 2024) {
            throw new DateException("Ошибка в данных: <"
                    + date + ">. Профиль не сохранен, попробуйте еще раз.");
        }
        if (Integer.parseInt(splitDate[1]) < 1 || Integer.parseInt(splitDate[1]) > 12) {
            throw new DateException("Ошибка в данных: <"
                    + date + ">. Профиль не сохранен, попробуйте еще раз.");
        } else {
            if (Integer.parseInt(splitDate[0]) < 1 || Integer.parseInt(splitDate[0]) > 31) {
                throw new DateException("Ошибка в данных: <"
                        + date + ">. Профиль не сохранен, попробуйте еще раз.");
            } else {
                if (Integer.parseInt(splitDate[1]) == 4 || Integer.parseInt(splitDate[1]) == 6 ||
                        Integer.parseInt(splitDate[1]) == 9 || Integer.parseInt(splitDate[1]) == 11) {
                    if (Integer.parseInt(splitDate[0]) == 31) {
                        throw new DateException("Ошибка в данных: <"
                                + date + ">. Профиль не сохранен, попробуйте еще раз.");
                    }
                } else if (Integer.parseInt(splitDate[1]) == 2 && Integer.parseInt(splitDate[2]) % 4 != 0) {
                    if (Integer.parseInt(splitDate[0]) > 28) {
                        throw new DateException("Ошибка в данных: <"
                                + date + ">. Профиль не сохранен, попробуйте еще раз.");
                    }
                }
            }
        }
    }
}
