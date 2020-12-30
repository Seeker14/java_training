package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GithubTests {

  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("c68d1ac4e0b6accab6574dd92cc19b411f4bc89a");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("Seeker14", "java_training")).commits();
    for (RepoCommit commit: commits.iterate(new ImmutableMap.Builder<String,String>().build())){
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }
}
