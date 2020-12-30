package ru.stqa.pft.rest.tests;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.rest.appmanager.ApplicationManager;
import java.io.IOException;
import static com.jayway.restassured.RestAssured.authentication;
import static com.jayway.restassured.RestAssured.basic;

public class TestBase {
  public static final ApplicationManager app = new ApplicationManager();

  public boolean isIssueOpen(int issueId)  {
    if (app.rest().getIssueStatus(issueId).equals("Open")){
      return true;
    }
    else {
      return false;
    }
  }

  public void skipIfNotFixed(int issueId) {
    if(isIssueOpen(issueId))
      throw new SkipException("Ignored because of issue: " + issueId);
  }


  @BeforeSuite
  public void setUp() throws IOException {
    app.init();
    authentication = basic(app.getProperty("rest.username"), app.getProperty("rest.password"));
  }

}