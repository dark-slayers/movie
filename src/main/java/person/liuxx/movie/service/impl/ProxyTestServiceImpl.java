package person.liuxx.movie.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import person.liuxx.movie.proxy.ProxyAddress;
import person.liuxx.movie.proxy.ProxyTestResult;
import person.liuxx.movie.proxy.ProxyTestTask;
import person.liuxx.movie.proxy.source.ProxySource;
import person.liuxx.movie.service.ProxyTestService;
import person.liuxx.util.log.LogUtil;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月30日 下午1:59:27
 * @since 1.0.0
 */
@Service
public class ProxyTestServiceImpl implements ProxyTestService
{
    private Logger log = LogManager.getLogger();
    private static AtomicBoolean taskIsRunning = new AtomicBoolean(false);
    private static ConcurrentSkipListSet<ProxyAddress> addressSet = new ConcurrentSkipListSet<ProxyAddress>();
    private static Map<String, Long> map = new ConcurrentHashMap<>();

    @Override
    public Map<String, Long> mapTestResult(String targetAddress)
    {
        Map<String, Long> result = new HashMap<>();
        result.putAll(map);
        return result;
    }

    @Override
    public List<ProxyTestResult> listTestResult(String targetAddress)
    {
        List<ProxyTestResult> result = new ArrayList<>();
        Set<ProxyAddress> info = addressSet;
        ExecutorService executor = Executors.newFixedThreadPool(30);
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

    @Override
    public void startTask(String targetAddress)
    {
        synchronized (map)
        {
            if (taskIsRunning.get())
            {
                return;
            }
            taskIsRunning.set(true);
            map.clear();
            Set<ProxyAddress> info = addressSet;
            ExecutorService executor = Executors.newFixedThreadPool(30);
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
                        map.put(r.getAddress().getHost() + ":" + r.getAddress().getPort(), r
                                .getTime());
                    }
                }
                taskIsRunning.set(false);
            } catch (ExecutionException e)
            {
                log.error(LogUtil.errorInfo(e));
            } catch (InterruptedException e)
            {
                log.error(LogUtil.errorInfo(e));
            }
        }
    }

    public void flushAddressList()
    {
        ProxySource.allSource().flatMap(s -> s.getProxyAddressStream()).forEach(a -> addressSet.add(
                a));
    }
}
