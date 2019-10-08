package HttpClinets;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class HttpUtils {

    //连接池管理器
    private PoolingHttpClientConnectionManager cm;

    public HttpUtils() {
        this.cm = new PoolingHttpClientConnectionManager();
        //设置最大连接数
        this.cm.setMaxTotal(100);
        //设置最大主机连接数
        this.cm.setDefaultMaxPerRoute(10);

    }

    public String doGetHtml(String url) {
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).build();
        CloseableHttpResponse resp = null;
        HttpGet get = new HttpGet(url);
        get.setConfig(this.getConfig());
        try {
            resp = client.execute(get);
            if (resp.getStatusLine().getStatusCode() == 200) {
                if (resp != null) {
                    String content = EntityUtils.toString(resp.getEntity(), "gb2312");
                    return content;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (resp != null) {
                try {
                    resp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return " ";
    }
    private RequestConfig getConfig() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(1000)
                .setConnectionRequestTimeout(500)
                .setSocketTimeout(10000)//数据传输的最长时间
                .build();
        return config;
    }
    public String doGetImage(String url) {
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(cm).build();
        CloseableHttpResponse resp = null;
        HttpGet get = new HttpGet(url);
        get.setConfig(this.getConfig());
        try {
            resp = client.execute(get);
            if (resp.getStatusLine().getStatusCode() == 200) {
                if (resp != null) {
                    //获取图片的后缀名字
                    String extName=url.substring(url.lastIndexOf("."));
                    //创建图片，并进行重命名
                    String picName= UUID.randomUUID().toString()+extName;
                    //下载图片
                    OutputStream fos=new FileOutputStream(new File("E:\\images\\"+picName));
                    resp.getEntity().writeTo(fos);
                    return picName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (resp != null) {
                try {
                    resp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return " ";
    }


}
