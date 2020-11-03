package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTest1 {

  @Test
  public void testPoint1(){
    Point q1 = new Point (34,21);
    Point q2 = new Point (87, 53);

    Assert.assertEquals(q1.distance(q2), 61.91122676865643 );
  }
}
