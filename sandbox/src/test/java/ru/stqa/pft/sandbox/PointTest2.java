package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTest2 {

  @Test
  public void testPoint2(){
    Point k1 = new Point(1024, 789);
    Point k2 = new Point (2890,1057);

    Assert.assertEquals(k1.distance(k2), 1885.1472091059627);
  }
}
