package person.liuxx.movie.proxy;

import java.io.IOException;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private final Path proxyListPath = Paths.get("D:/temp/proxyList.txt");

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
            info = readList();
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
        return Files.lines(proxyListPath).filter(l -> !StringUtil.isEmpty(l)).map(l ->
        {
            String[] array = l.split("\\t");
            String host = array[0];
            int port = Integer.parseInt(array[1]);
            return new ProxyAddress(host, port);
        }).collect(Collectors.toList());
    }
}
