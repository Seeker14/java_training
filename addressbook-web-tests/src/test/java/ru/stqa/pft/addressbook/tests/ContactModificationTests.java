package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactModificationTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().homePage();
    if (app.contact().all().size() == 0){
      app.contact().create(new ContactData()
              .withFirstname("Ivan")
              .withMiddlename("Ivanovich")
              .withLastname("Petrov")
              .withNickname("Petay85")
              .withCompany("Bank \"Otkritie\"")
              .withHomephone("74959090909")
              .withMobilephone("79250257725")
              .withWorkphone("74958880808")
              .withEmail("ivan.petrov@open.ru")
              .withBday("15")
              .withBmonth("September")
              .withByear("1985")
              .withGroup("test1")
              .withHomeaddress("Moscow, Gagarina street, house 9, apartment 180"));
    }
  }

  @Test
  public void testContactModification(){
    Set<ContactData> before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId())
            .withFirstname("Ivan")
            .withMiddlename("Ivanovich")
            .withLastname("Petrov")
            .withNickname("Petay85")
            .withCompany("Bank \"Otkritie\"")
            .withHomephone("74959090909")
            .withMobilephone("79250257725")
            .withWorkphone("74958880808")
            .withEmail("ivan.petrov@open.ru")
            .withBday("15")
            .withBmonth("September")
            .withByear("1985")
            .withGroup("test1")
            .withHomeaddress("Moscow, Gagarina street, house 9, apartment 180");
    app.contact().modify(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size());

    before.remove(modifiedContact);
    before.add(contact);
    Assert.assertEquals(before, after);
  }

}
