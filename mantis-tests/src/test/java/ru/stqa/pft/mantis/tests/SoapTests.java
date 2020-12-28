package ru.stqa.pft.mantis.tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;



public class SoapTests extends TestBase {

  int issueId = 2;

  @Test
  public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
    try {
      skipIfNotFixed(issueId);
      Set<Project> projects = app.soap().getProjects();
      System.out.println(projects.size());
      for (Project project : projects) {
        System.out.println(project.getName());
      }
    } catch (SkipException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
    try {
      skipIfNotFixed(issueId);
      long now = System.currentTimeMillis();
      Set<Project> projects = app.soap().getProjects();
      Issue issue = new Issue().withSummary(String.format("Summary issue %s", now))
              .withDescription(String.format("Description issue %s ", now))
              .withProject(projects.iterator().next());
      Issue created = app.soap().addIssue(issue);
      Assert.assertEquals(issue.getSummary(), created.getSummary());
    } catch (SkipException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testOnBugStatus () throws RemoteException,  ServiceException, MalformedURLException {
    try {
      skipIfNotFixed(issueId);
      if (isIssueOpen(issueId)) {
        System.out.println("BUG OPEN");
      } else System.out.println("BUG Closed");
    } catch (SkipException e) {
      e.printStackTrace();
    }
  }
}
