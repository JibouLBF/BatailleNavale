/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author harbalk
 */
public class Player {

    private String pseudo;
    private String firstname;
    private String surname;
    private String email;
    private int streetNumber;
    private String streetName;
    private String postcode;
    private String city;
    private String birthday;

    public String getPseudo() {
        return pseudo;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

    public String getBirthday() {
        return birthday;
    }

    
    public Player(String pseudo, String firstname, String surname, String email, int streetNumber, String streetName, String postcode, String city, String birthday) {
        this.pseudo = pseudo;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.postcode = postcode;
        this.city = city;
        this.birthday = birthday;
    }

    public Player(String pseudo) {
        this.pseudo = pseudo;
    }
    
    
    
}
