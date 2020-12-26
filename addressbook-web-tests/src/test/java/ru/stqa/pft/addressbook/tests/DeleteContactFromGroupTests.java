package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.stream.Collectors;

public class DeleteContactFromGroupTests extends TestBase {
  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
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
              //         .withGroup("test1")
              .withAdditionalAddress("Moscow, Gagarina street, house 9, apartment 180"));
    }

    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testDeleteContactFromGroup() {
    Contacts beforeContacts = app.db().contacts();
    ContactData optContact = beforeContacts.iterator().next();
    Groups beforeGroups = app.db().groups();
    GroupData optGroup = beforeGroups.iterator().next();

    app.goTo().homePage();

    if (optContact.getGroups().isEmpty() || !optContact.getGroups().contains(optGroup)) {
      app.contact().selectGroupList("[all]");
      app.contact().addContactToGroup(optContact, optGroup);
      assertThat(optContact.getGroups().withAdded(optGroup), equalTo(app.db().contacts().stream().
              filter((c) -> c.getId() == optContact.getId()).collect(Collectors.toList()).get(0).getGroups()));
      app.goTo().homePage();
    }
    app.contact().removeContactFromGroup(optContact, optGroup);
    app.goTo().homePage();
    app.contact().selectGroupList("[all]");
    assertThat(optContact.getGroups().without(optGroup), equalTo(app.db().contacts().stream().
            filter((c) -> c.getId() == optContact.getId()).collect(Collectors.toList()).get(0).getGroups()));
  }
}
