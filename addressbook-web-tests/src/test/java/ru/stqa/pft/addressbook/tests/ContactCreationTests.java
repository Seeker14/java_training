package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
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
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((p) -> p.getId()).max().getAsInt()))));
  }

  @Test
  public void testBadContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstname("Ivan'")
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
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }

}
