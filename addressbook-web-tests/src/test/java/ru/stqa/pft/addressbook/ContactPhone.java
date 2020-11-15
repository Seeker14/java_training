package ru.stqa.pft.addressbook;

public class ContactPhone {
  private final String homephone;
  private final String mobilephone;
  private final String workphone;

  public ContactPhone(String homephone, String mobilephone, String workphone) {
    this.homephone = homephone;
    this.mobilephone = mobilephone;
    this.workphone = workphone;
  }

  public String getHomephone() {
    return homephone;
  }

  public String getMobilephone() {
    return mobilephone;
  }

  public String getWorkphone() {
    return workphone;
  }
}
