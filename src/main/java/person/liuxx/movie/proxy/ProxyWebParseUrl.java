package person.liuxx.movie.proxy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.hc.client5.http.impl.sync.CloseableHttpClient;
import org.apache.hc.client5.http.impl.sync.HttpClients;
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

import person.liuxx.movie.dto.ProxyWebDTO;
import person.liuxx.util.base.StringUtil;
import person.liuxx.util.log.LogUtil;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月30日 下午1:29:11
 * @since 1.0.0
 */
public class ProxyWebParseUrl
{
    private Logger log = LogManager.getLogger();
    private final String url;
    private final String tableTag;
    private final String tableTagType;
    private final int ipIndex;
    private final int portIndex;
    private final String charset;

    public ProxyWebParseUrl(String url, String tableTag, String tableTagType, int ipIndex,
            int portIndex, String charset)
    {
        this.url = url;
        this.tableTag = tableTag;
        this.tableTagType = tableTagType;
        this.ipIndex = ipIndex;
        this.portIndex = portIndex;
        this.charset = charset;
    }

    public static Stream<ProxyWebParseUrl> createStream(ProxyWebDTO dto)
    {
        List<ProxyWebParseUrl> list = new ArrayList<>();
        for (String sub : dto.getSubUrl())
        {
            if (dto.isSingle())
            {
                String url = dto.getUrl() + sub + "/";
                ProxyWebParseUrl p = new ProxyWebParseUrl(url, dto.getTableTag(), dto
                        .getTableTagType(), dto.getIpIndex(), dto.getPortIndex(), dto.getCharset());
                list.add(p);
            } else
            {
                for (int i = 1, max = dto.getMaxDeep() + 1; i < max; i++)
                {
                    String url = dto.getUrl() + sub + "/" + i + "/";
                    ProxyWebParseUrl p = new ProxyWebParseUrl(url, dto.getTableTag(), dto
                            .getTableTagType(), dto.getIpIndex(), dto.getPortIndex(), dto
                                    .getCharset());
                    list.add(p);
                }
            }
        }
        return list.stream();
    }

    public Stream<ProxyAddress> getProxyAddressStream()
    {
        List<ProxyAddress> info = new ArrayList<>();
        try (CloseableHttpClient httpclient = HttpClients.createDefault())
        {
            final HttpGet httpget = new HttpGet(url);
            httpget.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
                    + " AppleWebKit/537.36 (KHTML, like Gecko)"
                    + " Chrome/60.0.3112.78 Safari/537.36");
            log.info("Executing request {} -> {}", httpget.getMethod(), httpget.getUri());
            final ResponseHandler<String> responseHandler = new ResponseHandler<String>()
            {
                @Override
                public String handleResponse(final ClassicHttpResponse response) throws IOException
                {
                    String result = null;
                    final int status = response.getCode();
                    if (status >= HttpStatus.SC_SUCCESS && status < HttpStatus.SC_REDIRECTION)
                    {
                        final HttpEntity entity = response.getEntity();
                        try
                        {
                            return entity != null ? EntityUtils.toString(entity, charset) : null;
                        } catch (final ParseException ex)
                        {
                            log.error("解析服务器返回结果出现异常！");
                        }
                    } else
                    {
                        log.error("请求：{}  >>>  未正常响应，服务器响应码：{}", url, status);
                    }
                    return result;
                }
            };
            final String responseBody = httpclient.execute(httpget, responseHandler);
            if (StringUtil.isEmpty(responseBody))
            {
                return Stream.empty();
            }
            Document doc = Jsoup.parse(responseBody);
            Element table = doc.getElementById(tableTag);
            if (Objects.equals("Class", tableTagType))
            {
                Elements content = doc.getElementsByClass(tableTag);
                table = content.get(0);
            }
            Elements trs = table.getElementsByTag("tr");
            List<String> list = new ArrayList<>();
            for (Element tr : trs)
            {
                String text = tr.text();
                list.add(text);
            }
            info = list.stream()
                    .map(l -> l.split(" "))
                    .filter(a -> a[ipIndex].contains("."))
                    .map(a ->
                    {
                        String host = a[ipIndex];
                        int port = Integer.parseInt(a[portIndex]);
                        return new ProxyAddress(host, port);
                    })
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e)
        {
            log.error(LogUtil.errorInfo(e));
        }
        return info.stream();
    }

    @Override
    public String toString()
    {
        return "ProxyWebParseUrl [url=" + url + ", tableTag=" + tableTag + ", tableTagType="
                + tableTagType + ", ipIndex=" + ipIndex + ", portIndex=" + portIndex + ", charset="
                + charset + "]";
    }
}
