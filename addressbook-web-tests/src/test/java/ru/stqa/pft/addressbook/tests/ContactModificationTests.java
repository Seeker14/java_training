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
    app.goTo().gotoHomePage();
    if (! app.getContactHelper().isThereAContact()){
      app.getContactHelper().createContact(new ContactData("Ivan", "Ivanovich", "Petrov", "Petay85", "Bank \"Otkritie\"", "74959090909", "79250257725", "74958880808", "ivan.petrov@open.ru", "15", "September", "1985", "test1", "Moscow, Gagarina street, house 9, apartment 180"), true);
    }
  }

  @Test
  public void testContactModification(){
    List<ContactData> before = app.getContactHelper().getContactList();
    int index = before.size() - 1;
    ContactData contact = new ContactData(before.get(index).getId(),"Ivan", "Ivanovich", "Petrov", "Petay85", "Bank \"Otkritie\"", "74959090909", "79250257725", "74958880808", "ivan.petrov@open.ru", "15", "September", "1985", null,"Moscow, Gagarina street, house 9, apartment 180");
    app.getContactHelper().modifyContact(index, contact);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size());

    before.remove(index);
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

}
