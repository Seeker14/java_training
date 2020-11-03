package ru.stqa.pft.sandbox;

import java.sql.SQLOutput;

public class MyFirstProgram {

  public static void main(String[] args) {
    hello("world");
    hello("user");
    hello("Lena");

    Square s = new Square(5);
    System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

    Rectangle r = new Rectangle(4,6);
    System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " = " + r.area());

    //Задание №2
    Point q1 = new Point(7, 2);
    Point q2 = new Point(10, 6);
    System.out.println("Расстояние между точками координат = " + q1.distance(q2));
  }

  public static void hello (String somebody){
    System.out.println("Hello, " + somebody + "!");
  }

  //Задание №2 - функция, вычисляющая расстояние между двумя точками
 /* public static double distance(Point p1, Point p2){
    return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y));
  } */

}