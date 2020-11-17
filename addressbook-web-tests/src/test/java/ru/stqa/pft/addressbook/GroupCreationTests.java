package ru.stqa.pft.addressbook;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.openqa.selenium.*;

public class GroupCreationTests {
  private WebDriver wd;

  @BeforeMethod(alwaysRun = true)
  public void setUp() throws Exception {
    wd = new ChromeDriver();
    wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    wd.get("http://localhost/addressbook/");
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
    login("admin", "secret");
  }

  private void login(String username, String password) {
>>>>>>> 41fb297b68ae670fee9a63dee877024ee6fc5fd7
>>>>>>> ed3b2d85a41ff41e1972b229a6bf7212e1fc8b90
    wd.findElement(By.name("user")).clear();
    wd.findElement(By.name("user")).sendKeys(username);
    wd.findElement(By.name("pass")).clear();
    wd.findElement(By.name("pass")).sendKeys(password);
    wd.findElement(By.xpath("//input[@value='Login']")).click();
  }

  @Test
  public void testGroupCreation() throws Exception {
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> ed3b2d85a41ff41e1972b229a6bf7212e1fc8b90
    wd.findElement(By.linkText("groups")).click();
    wd.findElement(By.name("new")).click();
=======
    gotoGroupPage("groups");
    initGroupCreation("new");
    fillGroupForm(new GroupData("test1", "test2", "test3"));
    submitGroupCreation("submit");
    returnToGroupPage("groups");
    logout("Logout");
  }

  private void logout(String logout) {
    wd.findElement(By.linkText(logout)).click();
  }

  private void returnToGroupPage(String groups) {
    wd.findElement(By.linkText(groups)).click();
  }

  private void submitGroupCreation(String submit) {
    wd.findElement(By.name(submit)).click();
  }

  private void fillGroupForm(GroupData groupData) {
>>>>>>> 41fb297b68ae670fee9a63dee877024ee6fc5fd7
    wd.findElement(By.name("group_name")).click();
    wd.findElement(By.name("group_name")).clear();
    wd.findElement(By.name("group_name")).sendKeys(groupData.getName());
    wd.findElement(By.name("group_header")).click();
    wd.findElement(By.name("group_header")).clear();
    wd.findElement(By.name("group_header")).sendKeys(groupData.getHeader());
    wd.findElement(By.name("group_footer")).click();
    wd.findElement(By.name("group_footer")).clear();
<<<<<<< HEAD
    wd.findElement(By.name("group_footer")).sendKeys("test3");
    wd.findElement(By.name("submit")).click();
    wd.findElement(By.linkText("groups")).click();
=======
    wd.findElement(By.name("group_footer")).sendKeys(groupData.getFooter());
  }

  private void initGroupCreation(String s) {
    wd.findElement(By.name(s)).click();
  }

  private void gotoGroupPage(String groups) {
    wd.findElement(By.linkText(groups)).click();
>>>>>>> 41fb297b68ae670fee9a63dee877024ee6fc5fd7
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() throws Exception {
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
