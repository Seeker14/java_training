package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

  @Test
  public void testContactModification(){
    app.getContactHelper().initContactModification();
    app.getContactHelper().fillContactForm(new ContactData("Ivan", "Ivanovich", "Petrov", "Petay85", "Bank \"Otkritie\"", "74959090909", "79250257725", "74958880808", "ivan.petrov@open.ru", "15", "September", "1985", null,"Moscow, Gagarina street, house 9, apartment 180"), false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returntoHomePage();
  }
}
