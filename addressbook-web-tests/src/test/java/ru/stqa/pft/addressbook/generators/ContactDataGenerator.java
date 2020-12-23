package ru.stqa.pft.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter (names = "-c", description = "Contact count")
  public int count;

  @Parameter (names = "-f", description = "Target file")
  public String file;

  @Parameter (names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    if (format.equals("csv")) {
      saveAsCsv(contacts, new File(file));
    } else if (format.equals("xml")) {
      saveAsXml(contacts, new File(file));
    } else if (format.equals("json")) {
      saveAsJson(contacts, new File(file));
    } else {
      System.out.println("Unrecognized format " + format);
    }
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(contacts);
    try (Writer writer = new FileWriter(file)){
      writer.write(json);
    }
  }

  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);
    String xml = xstream.toXML(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }

  private void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
    System.out.println(new File(".").getAbsolutePath());
    try (Writer writer = new FileWriter(file)) {
      for (ContactData contact : contacts) {
        writer.write(String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\n",
                contact.getFirstname(), contact.getMiddlename(), contact.getLastname(),
                contact.getNickname(), contact.getCompany(), contact.getHomeaddress(),
                contact.getHomephone(), contact.getMobilephone(), contact.getWorkphone(),
                contact.getEmail(), contact.getEmail2(), contact.getEmail3(), contact.getBday(),
                contact.getBmonth(), contact.getByear(),
                contact.getGroup(), contact.getAdditionaladdress()));
      }
    }
  }

  private List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withFirstname(String.format("Test %s", i))
              .withMiddlename(String.format("Testovich %s", i))
              .withLastname(String.format("Testoviy %s", i))
              .withNickname(String.format("Test %s", i))
              .withCompany(String.format("QA %s", i))
              .withHomeaddress(String.format("city Bugs, house %s", i))
              .withHomephone(String.format("11%s", i))
              .withMobilephone(String.format("11111%s", i))
              .withWorkphone(String.format("11111111%s", i))
              .withEmail(String.format("test_%s@mail.ru", i))
              .withEmail2(String.format("test2_%s@mail.ru", i))
              .withEmail3(String.format("test3_%s@mail.ru", i))
              .withBday(String.format("1", i))
              .withBmonth(String.format("February", i))
              .withByear(String.format("199%s", i))
              .withGroup(String.format("test %s", i))
              .withAdditionalAddress(String.format("city Fix, house %s", i)));
    }
    return contacts;
  }
}
