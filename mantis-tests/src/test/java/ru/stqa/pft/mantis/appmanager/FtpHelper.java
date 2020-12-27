package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {

  private final ApplicationManager app;
  private FTPClient ftp;

  //выполняется инициализация, создается FTPClient, который будет устанавливать соединение, передавать файлы и т.п.
  public FtpHelper(ApplicationManager app) {
    this.app = app;
    ftp = new FTPClient();
  }

  //загрузка нового файла и временное переименование старого
  //1 параметр upload: локальный файл, который должен быть загружен на удаленную машину
  //2 параметр upload: имя удаленного файла, куда все это загружается
  //3 параметр upload: имя резервной копии, если удаленный файл уже существует
  public void upload(File file, String target, String backup) throws IOException {
    ftp.connect(app.getProperty("ftp.host"));                                 //установка соединения с сервером
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password")); //выполнение авторизации
    ftp.deleteFile(backup);                                                   //удаление предыдущей резервной копии
    ftp.rename(target, backup);                                               //переименование удаленного файла, делаем резервную копию
    ftp.enterLocalPassiveMode();                                              //пассивный режим передачи данных
    ftp.storeFile(target, new FileInputStream(file));                         //передается файл, данные читаются из локального файла, передаются на удаленную машину и сохраняются в удаленном файле target
    ftp.disconnect();                                                         //разрыв соединения после завершения передачи файла
  }

  //восстановаление исходного конфигурационного файла
  public void restore(String target, String backup) throws IOException {
    ftp.connect(app.getProperty("ftp.host"));                                 //установка соединения с удаленной машиной
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password")); //выполнение авторизации
    ftp.deleteFile(target);                                                   //удаление файла, загруженного нами ранее
    ftp.rename(backup, target);                                               //восстановление оригинального файла из резервной копии
    ftp.disconnect();                                                         //разрыв соединения
  }
}
