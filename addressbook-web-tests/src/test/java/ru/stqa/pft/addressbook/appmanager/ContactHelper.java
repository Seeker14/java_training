package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase{

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returntoHomePage() {
    click(By.linkText("home"));
  }

  public void submitContact() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("middlename"), contactData.getMiddlename());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("nickname"), contactData.getNickname());
    type(By.name("company"), contactData.getCompany());
    type(By.name("home"), contactData.getHomephone());
    type(By.name("mobile"), contactData.getMobilephone());
    type(By.name("work"), contactData.getWorkphone());
    type(By.name("email"), contactData.getEmail());
    click(By.name("bday"));
    new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactData.getBday());
    click(By.name("bday"));
    click(By.name("bmonth"));
    new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactData.getBmonth());
    click(By.name("bmonth"));
    type(By.name("byear"), contactData.getByear());
    if (creation){
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
    click(By.name("address2"));
    type(By.name("address2"), contactData.getHomeaddress());
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void deleteSelectedContact() {
    click(By.xpath("//*[@value='Delete']"));
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void initContactModification(int id) {
    click(By.xpath("(//img[@alt='Edit'])"));
  }

  public void submitContactModification() {
    click(By.xpath("//input[22]"));
  }

  public void createContact(ContactData contact, boolean b) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContact();
    returntoHomePage();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int getContactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  /* public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.cssSelector("tr[name=entry]"));
    for (WebElement element : elements){
      String firstname = element.getText();
      String lastname = element.getText();
      ContactData contact = new ContactData(firstname, null, lastname, null, null, null, null, null, null, null, null, null, null, null);
      contacts.add(contact);
    }
    return contacts;
  } */

  public List<ContactData> getContactList() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements){
      List<WebElement> fields = element.findElements(By.cssSelector("td"));
      String firstname = fields.get(2).getText();
      String lastname = fields.get(1).getText();
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData(id, firstname, null, lastname, null, null, null, null, null, null, null, null, null, null, null);
      contacts.add(contact);
    }
    return contacts;
  }
}
