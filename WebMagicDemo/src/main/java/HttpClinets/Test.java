package HttpClinets;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {

    static HttpUtils utils = new HttpUtils();

    public static void main(String[] args) {
        String html = utils.doGetHtml("https://www.bilibili.com/video/av20965295?p=65");
        Document doc = Jsoup.parse(html);
        System.out.println("==============");
      Elements elements= doc.select("div.comment-list");
        for (Element element : elements) {
            System.out.println(element);
        }

    }
}
