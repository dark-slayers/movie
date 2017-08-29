package person.liuxx.movie.proxy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.hc.client5.http.impl.sync.CloseableHttpClient;
import org.apache.hc.client5.http.impl.sync.HttpClients;
import org.apache.hc.client5.http.protocol.ClientProtocolException;
import org.apache.hc.client5.http.sync.methods.HttpGet;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.ResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import person.liuxx.util.base.StringUtil;
import person.liuxx.util.log.LogUtil;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月29日 下午2:42:00
 * @since 1.0.0
 */
public class ProxyListTest
{
    private Logger log = LogManager.getLogger();
    private String targetAddress;
    private final static Path PROXY_LIST_PATH = Paths.get("D:/temp/proxyList.txt");
    private final static String[] PROXY_URL =
    { "http://31f.cn/http-proxy/", "http://31f.cn/https-proxy/" };

    public ProxyListTest(String target)
    {
        targetAddress = target;
    }

    public List<ProxyTestResult> run()
    {
        List<ProxyTestResult> result = new ArrayList<>();
        List<ProxyAddress> info = new ArrayList<>();
        try
        {
            info = readListFromNet();
            if (info.size() <= 0)
            {
                info = readList();
            }
        } catch (IOException e)
        {
            log.error(LogUtil.errorInfo(e));
        }
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CompletionService<ProxyTestResult> service = new ExecutorCompletionService<ProxyTestResult>(
                executor);
        for (final ProxyAddress p : info)
        {
            service.submit(new ProxyTestTask(p, targetAddress));
        }
        try
        {
            for (int i = 0, max = info.size(); i < max; i++)
            {
                Future<ProxyTestResult> f = service.take();
                ProxyTestResult r = f.get();
                if (r.getTime() > 0)
                {
                    result.add(r);
                }
            }
            result.stream().forEach(r -> log.info("测试结果：{}", r));
        } catch (ExecutionException e)
        {
            log.error(LogUtil.errorInfo(e));
        } catch (InterruptedException e)
        {
            log.error(LogUtil.errorInfo(e));
        }
        return result;
    }

    private List<ProxyAddress> readList() throws IOException
    {
        return Files.lines(PROXY_LIST_PATH).filter(l -> !StringUtil.isEmpty(l)).map(l ->
        {
            String[] array = l.split("\\t");
            String host = array[0];
            int port = Integer.parseInt(array[1]);
            return new ProxyAddress(host, port);
        }).collect(Collectors.toList());
    }

    List<ProxyAddress> readListFromNet()
    {
        List<ProxyAddress> result = new ArrayList<>();
        for (String url : PROXY_URL)
        {
            result.addAll(readListFromNet(url));
        }
        return result;
    }

    private List<ProxyAddress> readListFromNet(String url)
    {
        List<ProxyAddress> info = new ArrayList<>();
        try (CloseableHttpClient httpclient = HttpClients.createDefault())
        {
            final HttpGet httpget = new HttpGet(url);
            log.info("Executing request {} -> {}", httpget.getMethod(), httpget.getUri());
            final ResponseHandler<String> responseHandler = new ResponseHandler<String>()
            {
                @Override
                public String handleResponse(final ClassicHttpResponse response) throws IOException
                {
                    final int status = response.getCode();
                    if (status >= HttpStatus.SC_SUCCESS && status < HttpStatus.SC_REDIRECTION)
                    {
                        final HttpEntity entity = response.getEntity();
                        try
                        {
                            return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
                        } catch (final ParseException ex)
                        {
                            throw new ClientProtocolException(ex);
                        }
                    } else
                    {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            final String responseBody = httpclient.execute(httpget, responseHandler);
            Document doc = Jsoup.parse(responseBody);
            Elements content = doc.getElementsByClass("table table-striped");
            Element table = content.get(0);
            Elements trs = table.getElementsByTag("tr");
            List<String> list = new ArrayList<>();
            for (Element tr : trs)
            {
                String text = tr.text();
                list.add(text);
            }
            info = list.stream().map(l -> l.split(" ")).filter(a -> a[1].contains(".")).map(a ->
            {
                String host = a[1];
                int port = Integer.parseInt(a[2]);
                return new ProxyAddress(host, port);
            }).collect(Collectors.toList());
        } catch (IOException | URISyntaxException e)
        {
            e.printStackTrace();
        }
        return info;
    }
}
