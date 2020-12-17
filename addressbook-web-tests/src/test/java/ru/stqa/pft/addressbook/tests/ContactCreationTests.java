package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Set<ContactData> before = app.contact().all();
    ContactData contact = new ContactData()
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
    app.contact().create(contact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size() + 1);

    contact.withId(after.stream().mapToInt((p) -> p.getId()).max().getAsInt());
    before.add(contact);
    Assert.assertEquals(before, after);
  }

}
