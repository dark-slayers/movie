package person.liuxx.movie.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import org.apache.hc.client5.http.impl.sync.CloseableHttpClient;
import org.apache.hc.client5.http.impl.sync.HttpClients;
import org.apache.hc.client5.http.sync.methods.HttpGet;
import org.apache.hc.core5.http.io.ResponseHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import person.liuxx.util.log.LogUtil;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月31日 下午4:06:26
 * @since 1.0.0
 */
public class HttpClientUtil
{
    private static Logger log = LogManager.getLogger();

    public static Optional<String> simpleGet(String url)
    {
        try (CloseableHttpClient httpclient = HttpClients.createDefault())
        {
            final HttpGet httpget = new HttpGet(url);
            httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
                    + " AppleWebKit/537.36 (KHTML, like Gecko)"
                    + " Chrome/60.0.3112.78 Safari/537.36");
            log.info("Executing request {} -> {}", httpget.getMethod(), httpget.getUri());
            final ResponseHandler<Optional<String>> responseHandler = new SimpleResponseHandler();
            Optional<String> responseBody = httpclient.execute(httpget, responseHandler);
            return responseBody;
        } catch (IOException | URISyntaxException e)
        {
            log.error(LogUtil.errorInfo(e));
        }
        return Optional.empty();
    }
}
