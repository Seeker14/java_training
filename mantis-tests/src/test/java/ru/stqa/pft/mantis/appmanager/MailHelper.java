package ru.stqa.pft.mantis.appmanager;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MailHelper {

  private ApplicationManager app;
  private final Wiser wiser;

  //инициализация почтового сервера Wiser
  public MailHelper(ApplicationManager app) {
    this.app = app;
    wiser = new Wiser();
  }

  //ожидание почтового сообщения (кол-во писем, котороые должно придти; время, в течение которого ждём)
  public List<MailMessage> waitForMail(int count, long timeout) throws MessagingException, IOException {
    long start = System.currentTimeMillis();                                                          //запоминаем текущее время
    while (System.currentTimeMillis() < start + timeout) {                                            //проверка в цикле, что время ожидания ещё не истекло
      if (wiser.getMessages().size() >= count) {                                                      //проверка в цикле, если кол-во писем прило достаточно - ожидаение прекращается
        return wiser.getMessages().stream().map((m) -> toModelMail(m)).collect(Collectors.toList());  //преобразование реальных объектов в модельные
      }
      try {
        Thread.sleep(1000);                                                                      //подождать 1000 миллисекунд, если кол-во почты пришло не достаточно, чтобы завершить цикл выше. Снова возвращаемся к циклу выше, пока не придёт нужное кол-во писем или время ожиданя не истечёт
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
    }
    throw new Error("No mail :(");
  }

  //mantis присылает письма в текстовом формате
  //при регистрации mantis присылает 2 письма: (1) администратору, (2) пользователю
  public static MailMessage toModelMail(WiserMessage m) {
    try {
      MimeMessage mm = m.getMimeMessage();
      return new MailMessage(mm.getAllRecipients()[0].toString(), (String) mm.getContent());   //взяли список получателей, оставили 1 из них, поскольку он единственный; преобразовали в строку, поскольку письмо текстовое => попадает в модельный объект
    } catch (MessagingException ex) {                                                          // оставшаяся часть - перехват ошибок, которые могут возникнуть
      ex.printStackTrace();
      return null;
    } catch (IOException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  //запуск почтового сервера Wiser
  public void start() {
    wiser.start();
  }

  //остановка почтового сервере Wiser
  public void stop() {
    wiser.stop();
  }
}
