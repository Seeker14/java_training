package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.telnet.TelnetClient;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JamesHelper {

  private final ApplicationManager app;

  private final TelnetClient telnet;
  private InputStream in;
  private PrintStream out;

  private final Session mailSession;
  private String mailserver;
  private Store store;

  public JamesHelper(ApplicationManager app) {
    this.app = app;
    telnet = new TelnetClient();
    mailSession = Session.getDefaultInstance(System.getProperties());  //создается почтовая сессия
  }

  //проверка на существование пользователя
  public boolean doesUserExist(String name) {
    initTelnetSession();
    write("verify " + name);
    String result = readUntil("exist");
    closeTelnetSession();
    return result.trim().equals("User " + name + " exist");
  }

  //создание нового пользователя
  public void createUser(String name, String passwd) {
    initTelnetSession();                                         //устанавливается соединение по протоколу telnet
    write("adduser " + name + " " + passwd);               //пишем команду
    String result = readUntil("User " + name + " added"); //ждём появления на консоли данного текста
    closeTelnetSession();                                        //разрыв соединения
  }

  //удаление пользователя
  public void deleteUser(String name) {
    initTelnetSession();
    write("delusr " + name);
    String result = readUntil("User " + name + " deleted");
    closeTelnetSession();
  }

  public void initTelnetSession() {
    //получение свойств из конфигурационного файла
    mailserver = app.getProperty("mailserver.host");
    int port = Integer.parseInt(app.getProperty("mailserver.port"));
    String login = app.getProperty("mailserver.adminlogin");
    String password = app.getProperty("mailserver.adminpassword");

    try {
      telnet.connect(mailserver, port);                //установка соединения с почтовым сервером
      in = telnet.getInputStream();                    //берём входной поток после установки соединения (чтобы читать те данные, которые telnet отправляет нам)
      out = new PrintStream(telnet.getOutputStream()); //берём выходной поток после установки соединения (чтобы писать, отправлять команды telnet)
    } catch (Exception ex) {
      // TODO Auto-generated catch block
      ex.printStackTrace();
    }

    //cхема взаимодействия с James по Telnet
    // Don't know  why it doesn't allow login at the first attempt
    readUntil("Login id:");                      //текст, который пишет сервер
    write(login);                                       //дождаться текст, который ожидаем от сервера, после написать туда что-то
    readUntil("Password:");
    write(password);

    //Read welcome message
    readUntil("Welcome "+login+". HELP for a list of commands");

    // Second login attempt, must be successful
    readUntil("Login id:");
    write(login);
    readUntil("Password:");
    write(password);

    //Read welcome message
    readUntil("Welcome "+login+". HELP for a list of commands");
  }

  //посимвольно читаются данные из входного потока и сравниваются с заданным шаблоном
  //как только прочитан фрагмент, который соответсвует этому шаблону, ожидание завершается
  public String readUntil(String pattern) {
    try {
      char lastChar = pattern.charAt(pattern.length() - 1);
      StringBuffer sb = new StringBuffer();
      char ch = (char) in.read();
      while (true) {
        System.out.println(ch);
        sb.append(ch);
        if (ch == lastChar) {
          if (sb.toString().endsWith(pattern)) {
            return sb.toString();
          }
        }
        ch = (char) in.read();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  //запись команд в выходной поток
  private void write(String value) {
    try {
      out.println(value);
      out.flush();
      System.out.println(value);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void closeTelnetSession() {
    write("quit");
  }

  //
  public List<MailMessage> waitForMail(String username, String password, long timeout) throws MessagingException {
    long now = System.currentTimeMillis();                        //запоминаем время начала ожидания
    while (System.currentTimeMillis() < now + timeout) {          //проверяем, что текущее время не превышает момент старта + заданный timeout
      List<MailMessage> allMail = getAllMail(username, password); //пытаемся получить всю почту =>
      if (allMail.size() > 0) {                                   //если найдено хотя бы одно письмо =>
        return allMail;                                           //возвращаем список писем
      }
      try {
        Thread.sleep(1000);                                 //если почты нет в заданный промежуток времени, то ждем 1000 миллисекунд =>
      } catch (InterruptedException ex) {                         //идём на второй заход
        ex.printStackTrace();
      }
    }
    throw new Error("No mail :(");                                //если время истекло, выходим из цикла и выбрасываем исключение
  }

  //получение сообщений из почтового ящика и их преобразование в модельные объекты типа MailMessage
  public List<MailMessage> getAllMail(String username, String password) throws MessagingException {
    Folder inbox = openInbox(username, password);                             //протокол POP3 требует выполнение действия на открытие почтового ящика
    List<MailMessage> messages = Arrays.asList(inbox.getMessages()).stream()
            .map((m) -> toModelMail(m)).collect(Collectors.toList());         //берём список писем => превращаем в поток => применяем функцию, которая превращает из в модельные объекты => собираем поток обратно в список
    closeFolder(inbox);                                                       //протокол POP3 требует выполнение действия на закрытие почтового ящика
    return messages;                                                          //возвращаем собранный список
  }

  //преобразование почтового сообщения в модельный объект типа MailMessage
  // Известно, что mantis присылает письма в обычном текстовом формате
  // Mantis присылает два письма, первое - Администратору, о том,что зарегистрирован новый пользователь,
  // второе - Пользователю, в нем содержится ссылка для продолжения регистрации
  public static MailMessage toModelMail(Message m) {
    try {
      return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());  //получаем список адресов => берём первый адрес => получаем содержимое письма => преобразуем его в строку => по полученным данным строим модельный объект
    } catch (MessagingException ex) {
      ex.printStackTrace();
      return null;
    } catch (IOException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  //открытие почтового ящика
  private Folder openInbox(String username, String password) throws MessagingException {
    store = mailSession.getStore("pop3");                   //берём почтовую сессию и сообщаем, что хотим использовать протокол POP3
    store.connect(mailserver, username, password);                  //устанавливает соединение по протоколу POP3
    Folder folder = store.getDefaultFolder().getFolder("INBOX");    //получаем доступ к папке INBOX, по протолку POP3 можно получить доступ только к этой папке
    folder.open(Folder.READ_WRITE);                                 //открываем папку на чтение
    return folder;                                                  //возвращается открытая папка в метод getAllMail
  }

  //закрытие почтового ящика
  private void closeFolder(Folder folder) throws MessagingException {
    folder.close(true);                                      //параметр true означает, что нужно удалить все письма, помеченные к удалению
    store.close();                                                   //закрывается соединение с почтовым сервером
  }

  //удаление всех писем, полученных пользователем
  //применяется, если в тесте используется один и тот же почтовый ящик
  public void drainEmail(String username, String password) throws MessagingException {
    Folder inbox = openInbox(username, password);
    for (Message message : inbox.getMessages()) {
      message.setFlag(Flags.Flag.DELETED, true);                 //каждое сообщение помечается флагом DELETED
    }
    closeFolder(inbox);                                              //при закрытии папки все помеченные письма будут удалены
  }
}
