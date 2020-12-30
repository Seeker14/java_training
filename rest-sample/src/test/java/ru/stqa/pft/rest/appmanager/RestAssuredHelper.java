package ru.stqa.pft.rest.appmanager;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.restassured.RestAssured;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class RestAssuredHelper {
  private ApplicationManager app;

  public RestAssuredHelper(ApplicationManager app) {
    this.app = app;
  }

  public int createIssue(Issue newIssue) throws IOException {
    String json = RestAssured.given().param("subject", newIssue.getSubject())
            .param("description", newIssue.getDescription())
            .post(app.getProperty("rest.url") + "issues.json").asString();
    JsonElement parsed = new JsonParser().parse(json);
    return parsed.getAsJsonObject().get("issue_id").getAsInt();
  }

  public Set<Issue> getIssues() throws IOException {
    String json = RestAssured.get(app.getProperty("rest.url") + "issues.json").asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
    }.getType());
  }

  public String getIssueStatus(int issueId){
    String json = RestAssured.get(app.getProperty("rest.url") + "issues/" + issueId + ".json").asString();
    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    JsonArray issuesContent = issues.getAsJsonArray();
    String issueStatus = issuesContent.iterator().next().getAsJsonObject().get("state_name").getAsString();
    System.out.println(issueStatus);
    return issueStatus;
  }
}