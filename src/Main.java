public class Main {
    public static void main(String[] args) throws Exception {

        PersonProfileInput profile = new PersonProfileInput();
        String[] userDataArray = profile.inputPersonData();
        Person person = profile.createProfile(userDataArray);
        System.out.println("________________");
        profile.savePersonProfileToFile(person);
    }
}