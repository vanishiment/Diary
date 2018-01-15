package com.plant.diary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
  @Test public void addition_isCorrect() throws Exception {
    httpTest("9aafbb20b779ce8ad477b41a67aee48c");
    assertEquals(4, 2 + 2);
  }


  public  String  httpTest(String s) {
    String kk_httpid = null;

    final String JSurl = "http://101.132.26.203:9700/getModel?key="+ s.trim();
    try {
      URL url1 = null;
      try {
        url1 = new URL(JSurl);
      } catch (MalformedURLException e2) {
        e2.printStackTrace();
      }
      URLConnection rulConnection = null;
      String sCurrentLine = "";
      String sTotalString = "";
      try {
        rulConnection = url1.openConnection();
      } catch (IOException e) {
        e.printStackTrace();
      }
      HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
      httpUrlConnection.setDoInput(true);
      httpUrlConnection.setDoOutput(true);
      httpUrlConnection.setUseCaches(false);
      httpUrlConnection.setRequestMethod("GET");
      try {
        InputStream is = httpUrlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while ((sCurrentLine = reader.readLine()) != null) {
          if (sCurrentLine.length() > 0)
            sTotalString = sTotalString + sCurrentLine.trim();
        }
        kk_httpid = sTotalString;

        System.out.println("sTotalString:"+sTotalString);


        is.close();
      }

      catch (IOException e1) {
        e1.printStackTrace();
      }
    } catch (ProtocolException e1) {
      e1.printStackTrace();
    }

    return kk_httpid;
  }
}