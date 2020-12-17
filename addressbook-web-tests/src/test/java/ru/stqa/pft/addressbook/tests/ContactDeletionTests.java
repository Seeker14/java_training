package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

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
  public void testContactDeletion() {
    Contacts before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    app.alertSuccess();
    Contacts after = app.contact().all();
    assertEquals(after.size(), before.size() - 1);

    assertThat(after, equalTo(before.without(deletedContact)));
  }

}
