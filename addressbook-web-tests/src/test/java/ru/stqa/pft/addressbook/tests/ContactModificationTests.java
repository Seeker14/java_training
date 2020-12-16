package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().homePage();
    if (app.contact().list().size() == 0){
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
    List<ContactData> before = app.contact().list();
    int index = before.size() - 1;
    ContactData contact = new ContactData()
            .withId(before.get(index).getId())
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
    app.contact().modify(index, contact);
    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

}
