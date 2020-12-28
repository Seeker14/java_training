package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase{

  //@BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testRegistration() throws IOException, MessagingException {
    long now = System.currentTimeMillis();                                            //создаем уникальные идентификаторы для пользователей, чтобы каждый раз регистрировался новый пользователь (используемая функция возвращает текущее время в миллисекундах)
    String user = String.format("user%s", now);
    String password = "password";
    String email = String.format("user%s@localhost.localdomain", now);                //(1) параметр шаблон; (2) то, что должно подставиться вместо %s
    //app.james().createUser(user, password);                                           //создание пользователя на внешнем почтовом сервере
    app.registration().start(user, email);
    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);   //ожидаение письма: ждём 2 письма, ждём 10000 миллисекунд
    //List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000); //время ожидаения указано больше, поскольку на внешний почтовый сервер письма идут дольше
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);                           //завершаем регистрацию
    assertTrue (app.newSession().login(user, password));                             //авторизация: проверяем, что регистрация прошла успешно
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();    //ищем среди всех писем, все письма, отправленные на нужный адрес и берём первое
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();       //регулярное выражение: ищем текст, который начинается с http://, после которого идут 1 и более непробельных символов
    return regex.getText(mailMessage.text);                                                                 //применяем регулярное выражение к тексту письма и возвращаем получивашееся значение
  }

  //@AfterMethod (alwaysRun = true)   //почтовый сервер будет останавливаться, даже если тест завершится не успешно
  public void stopMailServer() {
    app.mail().stop();
  }
}
