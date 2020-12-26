package ru.stqa.pft.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {
  private CloseableHttpClient httpclient;
  private ApplicationManager app;

  public HttpSession(ApplicationManager app) {
    this.app = app;
    httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build(); //создание новой сессии по протоколу http; устанавливается стратегия перенаправления
  }

  public boolean login(String username, String password) throws IOException {
    HttpPost post = new HttpPost(app.getProperty("web.baseUrl") + "/login.php"); //создается будущий запрос POST (пока пустой)
    List<NameValuePair> params = new ArrayList<NameValuePair>();                 // формируется набора параметров + строки ниже
    params.add(new BasicNameValuePair("username", username));
    params.add(new BasicNameValuePair("password", password));
    params.add(new BasicNameValuePair("secure_session", "on"));
    params.add(new BasicNameValuePair("return", "index.php"));
    post.setEntity(new UrlEncodedFormEntity(params));                            //параметры упаковываются и помещаются в заранее созданный запрос POST
    CloseableHttpResponse response = httpclient.execute(post);                   //отправка запроса, в результате чего формируется ответ
    String body = getTextFrom(response);
    return body.contains(String.format("<span class=\"user-info\">%s</span>", username)); //проверка, действительно ли пользователь успешно авторизовался
  }

  private String getTextFrom(CloseableHttpResponse response) throws IOException {
    try {
      return EntityUtils.toString(response.getEntity());
    } finally {
      response.close();
    }
  }

  //определяет, какой пользователь сейчас авторизован
  public boolean isLoggedInAs(String username) throws IOException {
    HttpGet get = new HttpGet(app.getProperty("web.baseUrl") + "/index.php");      //создается будущий запрос GET (пока пустой)
    CloseableHttpResponse response = httpclient.execute(get);                      //выполняем запрос и получаем ответ
    String body = getTextFrom(response);                                           //получаем текст
    return body.contains(String.format("<span class=\"user-info\">%s</span>", username)); //проверяем, что в тексте страницы содержится нужный фрагмент
  }
}