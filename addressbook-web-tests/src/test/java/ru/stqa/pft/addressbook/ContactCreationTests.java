package ru.stqa.pft.addressbook;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class ContactCreationTests {
  private WebDriver wd;

  @BeforeMethod(alwaysRun = true)
  public void setUp() throws Exception {
    wd = new ChromeDriver();
    wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    wd.get("http://localhost/addressbook/");
    login("admin", "secret");
  }

  private void login(String username, String password) {
    wd.findElement(By.name("user")).clear();
    wd.findElement(By.name("user")).sendKeys(username);
    wd.findElement(By.name("pass")).click();
    wd.findElement(By.name("pass")).clear();
    wd.findElement(By.name("pass")).sendKeys(password);
    wd.findElement(By.id("LoginForm")).submit();
  }

  @Test
  public void testContactCreation() throws Exception {
    gotoCreateContactList();
    fillFIO(new ContactFIO("Ivan", "Ivanovich", "Petrov"));
    fillNickname("Petya85");
    fillNameCompany("Bank \"Otkritie\"");
    fillPhone(new ContactPhone("74959090909", "79250257725", "74958880808"));
    fillEmail("ivan.petrov@open.ru");
    fillBirthdate(new ContactBirthdate("15", "September", "1985"));
    fillHomeAddress("Moscow, Gagarina street, house 9, apartment 180");
    submitContact();
    returntoHomePage();
  }

  private void returntoHomePage() {
    wd.findElement(By.linkText("home")).click();
  }

  private void submitContact() {
    wd.findElement(By.xpath("(//input[@name='submit'])[2]")).click();
  }

  private void fillHomeAddress(String homeaddress) {
    wd.findElement(By.name("address2")).click();
    wd.findElement(By.name("address2")).sendKeys(homeaddress);
  }

  private void fillBirthdate(ContactBirthdate contactBirthdate) {
    wd.findElement(By.name("bday")).click();
    new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactBirthdate.getBday());
    wd.findElement(By.name("bday")).click();
    wd.findElement(By.name("bmonth")).click();
    new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactBirthdate.getBmonth());
    wd.findElement(By.name("bmonth")).click();
    wd.findElement(By.name("byear")).click();
    wd.findElement(By.name("byear")).clear();
    wd.findElement(By.name("byear")).sendKeys(contactBirthdate.getByear());
  }

  private void fillEmail(String email) {
    wd.findElement(By.name("email")).click();
    wd.findElement(By.name("email")).clear();
    wd.findElement(By.name("email")).sendKeys(email);
  }

  private void fillPhone(ContactPhone contactPhone) {
    wd.findElement(By.name("home")).click();
    wd.findElement(By.name("home")).clear();
    wd.findElement(By.name("home")).sendKeys(contactPhone.getHomephone());
    wd.findElement(By.name("mobile")).click();
    wd.findElement(By.name("mobile")).clear();
    wd.findElement(By.name("mobile")).sendKeys(contactPhone.getMobilephone());
    wd.findElement(By.name("work")).click();
    wd.findElement(By.name("work")).clear();
    wd.findElement(By.name("work")).sendKeys(contactPhone.getWorkphone());
  }

  private void fillNameCompany(String namecompany) {
    wd.findElement(By.name("company")).click();
    wd.findElement(By.name("company")).clear();
    wd.findElement(By.name("company")).sendKeys(namecompany);
  }


  private void fillNickname(String nickname) {
    wd.findElement(By.name("nickname")).sendKeys(nickname);
  }

  private void fillFIO(ContactFIO contactFIO) {
    wd.findElement(By.name("firstname")).click();
    wd.findElement(By.name("firstname")).clear();
    wd.findElement(By.name("firstname")).sendKeys(contactFIO.getFirstname());
    wd.findElement(By.name("middlename")).click();
    wd.findElement(By.name("middlename")).clear();
    wd.findElement(By.name("middlename")).sendKeys(contactFIO.getMiddlename());
    wd.findElement(By.name("lastname")).click();
    wd.findElement(By.name("lastname")).clear();
    wd.findElement(By.name("lastname")).sendKeys(contactFIO.getLastname());
    wd.findElement(By.name("nickname")).click();
    wd.findElement(By.name("nickname")).clear();
  }

  private void gotoCreateContactList() {
    wd.findElement(By.linkText("add new")).click();
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() throws Exception {
    wd.findElement(By.linkText("Logout")).click();
    wd.quit();
  }

  private boolean isElementPresent(By by) {
    try {
      wd.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

}
