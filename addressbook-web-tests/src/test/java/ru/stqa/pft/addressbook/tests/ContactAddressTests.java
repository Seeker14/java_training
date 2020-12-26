package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTests extends TestBase {

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
              .withHomeaddress("Ореховый бульвар, дом 7, корпус 2")
              .withHomephone("74959090909")
              .withMobilephone("79250257725")
              .withWorkphone("74958880808")
              .withEmail("ivan.petrov@open.ru")
              .withEmail2("elena.egorova@open.ru")
              .withEmail3("lesha.ivanov@list.ru")
              .withBday("15")
              .withBmonth("September")
              .withByear("1985")
       //       .withGroup("test1")
              .withAdditionalAddress("Moscow, Gagarina street, house 9, apartment 180"));
    }
  }

  @Test
  public void testContactAddress(){
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getHomeaddress(), equalTo(homeAddress(contactInfoFromEditForm)));
  }

  private String homeAddress(ContactData contact){
    return contact.getHomeaddress();
  }

}
