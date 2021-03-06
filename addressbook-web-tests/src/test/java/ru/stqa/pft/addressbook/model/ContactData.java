package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table (name = "addressbook")
@XStreamAlias("contact")
public class ContactData {

  @XStreamOmitField
  @Id
  @Column (name = "id")
  private int id = Integer.MAX_VALUE;
  @Expose
  @Column (name = "firstname")
  private String firstname;
  @Expose
  @Column (name = "middlename")
  private String middlename;
  @Expose
  @Column (name = "lastname")
  private String lastname;
  @Expose
  @Column (name = "nickname")
  private String nickname;
  @Expose
  @Column (name = "company")
  private String company;
  @Expose
  @Column (name = "address")
  @Type (type = "text")
  private String homeaddress;
  @Expose
  @Column (name = "home")
  @Type (type = "text")
  private String homephone;
  @Expose
  @Column (name = "mobile")
  @Type (type = "text")
  private String mobilephone;
  @Expose
  @Column (name = "work")
  @Type (type = "text")
  private String workphone;
  @Expose
  @Column (name = "email")
  @Type (type = "text")
  private String email;
  @Expose
  @Column (name = "email2")
  @Type (type = "text")
  private String email2;
  @Expose
  @Column (name = "email3")
  @Type (type = "text")
  private String email3;
  @Expose
  @Column (name = "bday")
  @Transient            //поле не будет извлечено, будет пропущено
  private String bday;
  @Expose
  @Transient            //поле не будет извлечено, будет пропущено
  private String bmonth;
  @Expose
  @Transient            //поле не будет извлечено, будет пропущено
  private String byear;
  @Expose
  @Column (name = "address2")
  @Type (type = "text")
  private String additionaladdress;
  @Transient            //поле не будет извлечено, будет пропущено
  private String allPhones;
  @Transient
  private String allEmails;
  @Transient            //поле не будет извлечено, будет пропущено
  private File photo;

  @ManyToMany (fetch = FetchType.EAGER)
  @JoinTable (name = "address_in_groups",
          joinColumns = @JoinColumn (name = "id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
  private Set<GroupData> groups = new HashSet<GroupData>();

  public File getPhoto() { return photo; }

  public String getAllEmails() { return allEmails; }

  public String getAllPhones() { return allPhones; }

  public String getEmail2() { return email2; }

  public String getEmail3() { return email3; }

  public String getHomeaddress() { return homeaddress; }

  public String getFirstname() { return firstname; }

  public String getMiddlename() {
    return middlename;
  }

  public String getLastname() {
    return lastname;
  }

  public String getNickname() {
    return nickname;
  }

  public String getCompany() {
    return company;
  }

  public String getHomephone() {
    return homephone;
  }

  public String getWorkphone() {
    return workphone;
  }

  public String getEmail() {
    return email;
  }

  public String getBday() {
    return bday;
  }

  public String getBmonth() {
    return bmonth;
  }

  public String getByear() {
    return byear;
  }

  public String getAdditionaladdress() {
    return additionaladdress;
  }

  public int getId() {
    return id;
  }

  public String getMobilephone() {
    return mobilephone;
  }

  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public ContactData withFirstname(String firstname) {
    this.firstname = firstname;
    return this;
  }

  public ContactData withMiddlename(String middlename) {
    this.middlename = middlename;
    return this;
  }

  public ContactData withLastname(String lastname) {
    this.lastname = lastname;
    return this;
  }

  public ContactData withNickname(String nickname) {
    this.nickname = nickname;
    return this;
  }

  public ContactData withCompany(String company) {
    this.company = company;
    return this;
  }

  public ContactData withHomephone(String homephone) {
    this.homephone = homephone;
    return this;
  }

  public ContactData withMobilephone(String mobilephone) {
    this.mobilephone = mobilephone;
    return this;
  }

  public ContactData withWorkphone(String workphone) {
    this.workphone = workphone;
    return this;
  }

  public ContactData withEmail(String email) {
    this.email = email;
    return this;
  }

  public ContactData withBday(String bday) {
    this.bday = bday;
    return this;
  }

  public ContactData withBmonth(String bmonth) {
    this.bmonth = bmonth;
    return this;
  }

  public ContactData withByear(String byear) {
    this.byear = byear;
    return this;
  }

  public ContactData withAdditionalAddress(String additionaladdress) {
    this.additionaladdress = additionaladdress;
    return this;
  }

  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public ContactData withEmail2(String email2) {
    this.email2 = email2;
    return this;
  }

  public ContactData withEmail3(String email3) {
    this.email3 = email3;
    return this;
  }

  public ContactData withHomeaddress(String homeaddress) {
    this.homeaddress = homeaddress;
    return this;
  }

  public ContactData withAllEmails(String allEmails) {
    this.allEmails = allEmails;
    return this;
  }

  public ContactData withPhoto(File photo) {
    this.photo = photo;
    return this;
  }

  public Groups getGroups() {
    return new Groups(groups);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return id == that.id &&
            Objects.equals(firstname, that.firstname) &&
            Objects.equals(middlename, that.middlename) &&
            Objects.equals(lastname, that.lastname) &&
            Objects.equals(nickname, that.nickname) &&
            Objects.equals(company, that.company) &&
            Objects.equals(homeaddress, that.homeaddress) &&
            Objects.equals(homephone, that.homephone) &&
            Objects.equals(mobilephone, that.mobilephone) &&
            Objects.equals(workphone, that.workphone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(email2, that.email2) &&
            Objects.equals(email3, that.email3) &&
            Objects.equals(additionaladdress, that.additionaladdress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstname, middlename, lastname, nickname, company, homeaddress, homephone, mobilephone, workphone, email, email2, email3, additionaladdress);
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "id=" + id +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            '}';
  }

  public ContactData inGroup(GroupData group) {
    groups.add(group);
    return this;
  }

}
