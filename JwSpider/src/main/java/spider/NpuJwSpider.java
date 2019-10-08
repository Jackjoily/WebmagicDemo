package spider;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author jackjoily
 * @time ${Date}
 * @Description
 **/
public class NpuJwSpider {
    private String accout;
    private String password;
    static CookieStore cookieStore = new BasicCookieStore();
    static CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
    HttpClientContext context = HttpClientContext.create();
    HttpResponse response = null;
    String rawHtml;

    public NpuJwSpider(String accout, String password) {
        this.accout = accout;
        this.password = password;
    }

    public void login() {
        String result = null;
        HttpGet getLoginPage = new HttpGet("http://yjsjy.nwpu.edu.cn/pyxx/account/login?error=3#");// 教务处登陆页面get
        try {
            String code;
            client.execute(getLoginPage, context);
            getVerifyingCode(client);
            System.out.println("请输入验证码：");
            Scanner in = new Scanner(System.in);
            code = in.nextLine();
            // 设定post参数，和上图中的内容一致
            ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
            postData.add(new BasicNameValuePair("targetUrl", null));
            postData.add(new BasicNameValuePair("username", accout));// 学号
            postData.add(new BasicNameValuePair("password", password));// 密码
            postData.add(new BasicNameValuePair("captcha", code));// 验证码
            HttpPost post = new HttpPost("http://yjsjy.nwpu.edu.cn/pyxx/account/logon");// 构建post对象
            post.setEntity(new UrlEncodedFormEntity(postData));
            response = client.execute(post, context);
            //获取到请求的Cookie值
            List<Cookie> cookies = cookieStore.getCookies();
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getValue() + "======================");
            }
            // 请求教务处首页
            HttpGet get = new HttpGet("http://yjsjy.nwpu.edu.cn/pyxx/home/index");
            response = client.execute(get);
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (ClientProtocolException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void getVerifyingCode(CloseableHttpClient client) {// 这里使用的是把验证码下载下来的方式，分来想用自动识别的，但是自动识别的正确率太低了
        HttpGet getVerifyCode = new HttpGet("http://yjsjy.nwpu.edu.cn/pyxx/captcha/imageCode");// 验证码get
        FileOutputStream fileOutputStream = null;
        HttpResponse response;
        try {
            response = client.execute(getVerifyCode);// 获取验证码
            fileOutputStream = new FileOutputStream(new File("d:/verifyCode.jpeg"));
            response.getEntity().writeTo(fileOutputStream);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void vistiWithCookie() {
//        HttpGet get = new HttpGet("http://yjsjy.nwpu.edu.cn/pyxx/pygl/pyjhcx");
        HttpGet get = new HttpGet("http://yjsjy.nwpu.edu.cn/pyxx/xjgl/xj/editgrxx");
        //http://yjsjy.nwpu.edu.cn/pyxx/xjgl/xj/editgrxx个人信息网址
        get.setHeader("Cookie", "JSESSIONID=DFAEB2E580BCE9D2762D1A4DF7C02307.pyxx_server; sso_username=20192637");
        try (CloseableHttpResponse res = client.execute(get)) {
            Document doc = Jsoup.parse(EntityUtils.toString(res.getEntity()));
//            Elements elements = doc.select("#sample-table-1 tr");
            System.out.println(doc);
//            for (int i = 1; i < elements.size(); i++) {
//                System.out.println(elements.get(i).select(" tr td").get(3).text());
//                System.out.println("=============");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NpuJwSpider test = new NpuJwSpider("", "");
        test.login();
    }
}
