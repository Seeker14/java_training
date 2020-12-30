package ru.stqa.pft.rest.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;
import static org.testng.Assert.assertEquals;

public class RestAssuredTests extends TestBase {

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = app.rest().getIssues();
    Issue newIssue = new Issue().withSubject("MyNewTest").withDescription("DescriptionForMyNewTest");
    int issueId = app.rest().createIssue(newIssue);
    Set<Issue> newIssues = app.rest().getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
    app.rest().getIssueStatus(issueId);
  }
}