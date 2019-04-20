package saeed.example.com.firestoreappedmt;

public class AddressBook {

    private String name;
    private String family;
    private String emailAddress;

    public AddressBook()
    {

    }



    public AddressBook(String name, String family, String emailAddress) {
        this.name = name;
        this.family = family;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
