package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().gotoHomePage();
    if (! app.getContactHelper().isThereAContact()){
      app.getContactHelper().createContact(new ContactData("Ivan", "Ivanovich", "Petrov", "Petay85", "Bank \"Otkritie\"", "74959090909", "79250257725", "74958880808", "ivan.petrov@open.ru", "15", "September", "1985", "test1", "Moscow, Gagarina street, house 9, apartment 180"), true);
    }
    List<ContactData> before = app.getContactHelper().addContactList();
    app.getContactHelper().selectContact(before.size() - 1);
    app.getContactHelper().deleteSelectedContact();
    app.alertSuccess();
    List<ContactData> after = app.getContactHelper().addContactList();
    Assert.assertEquals(after.size(), before.size() - 1);
  }
}
