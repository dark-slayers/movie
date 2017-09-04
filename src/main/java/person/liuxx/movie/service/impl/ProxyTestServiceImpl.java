package person.liuxx.movie.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import person.liuxx.movie.dao.ProxyAddressRepository;
import person.liuxx.movie.domain.ProxyAddressDO;
import person.liuxx.movie.proxy.ProxyAddress;
import person.liuxx.movie.proxy.ProxyTestResult;
import person.liuxx.movie.proxy.ProxyTestTask;
import person.liuxx.movie.proxy.source.ProxySource;
import person.liuxx.movie.service.ProxyTestService;
import person.liuxx.util.base.StringUtil;
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
    @Autowired
    private ProxyAddressRepository proxyAddressDao;
    private static AtomicBoolean testIsRunning = new AtomicBoolean(false);
    private static AtomicBoolean addressIsFlushing = new AtomicBoolean(false);
    private static CountDownLatch hasList = new CountDownLatch(1);
    private static ConcurrentSkipListSet<ProxyAddress> addressSet = new ConcurrentSkipListSet<ProxyAddress>();
    private static final String DEFAULT_TARGET_URL = "https://www.facebook.com/";
    private String targetUrl;

    @Override
    public List<ProxyAddressDO> listAddress()
    {
        return proxyAddressDao.findAll();
    }

    @Override
    public void testAddressList()
    {
        try
        {
            hasList.await();
        } catch (InterruptedException e1)
        {
            log.error(LogUtil.errorInfo(e1));
        }
        if (testIsRunning.compareAndSet(false, true))
        {
            log.info("运行代理列表的有效性测试...");
            Set<ProxyAddress> info = addressSet;
            ExecutorService executor = Executors.newFixedThreadPool(30);
            CompletionService<ProxyTestResult> service = new ExecutorCompletionService<ProxyTestResult>(
                    executor);
            for (final ProxyAddress p : info)
            {
                service.submit(new ProxyTestTask(p, getTargetUrl()));
            }
            try
            {
                for (int i = 0, max = info.size(); i < max; i++)
                {
                    Future<ProxyTestResult> f = service.take();
                    ProxyTestResult r = f.get();
                    if (r.getTime() > 0)
                    {
                        Optional<ProxyAddressDO> optional = proxyAddressDao.findByHostAndPort(r
                                .getAddress().getHost(), r.getAddress().getPort());
                        if (optional.isPresent())
                        {
                            proxyAddressDao.setLastTestUrlAndLastUsableTimeAndUseTimeById(
                                    getTargetUrl(), LocalDateTime.now(), r.getTime(), optional.get()
                                            .getId());
                        } else
                        {
                            ProxyAddressDO address = new ProxyAddressDO();
                            address.setHost(r.getAddress().getHost());
                            address.setPort(r.getAddress().getPort());
                            address.setLastTestUrl(getTargetUrl());
                            address.setLastUsableTime(LocalDateTime.now());
                            address.setUseTime(r.getTime());
                            proxyAddressDao.save(address);
                        }
                    }
                }
            } catch (ExecutionException e)
            {
                log.error(LogUtil.errorInfo(e));
            } catch (InterruptedException e)
            {
                log.error(LogUtil.errorInfo(e));
            } finally
            {
                testIsRunning.set(false);
            }
            log.info("代理列表的有效性测试完毕！");
        }
    }

    @Override
    public void flushAddressList()
    {
        if (addressIsFlushing.compareAndSet(false, true))
        {
            log.info("刷新缓存的代理地址列表...");
            ProxySource.allSource().flatMap(s -> s.getProxyAddressStream()).forEach(a -> addressSet
                    .add(a));
            addressIsFlushing.set(false);
            hasList.countDown();
            log.info("缓存的代理地址列表刷新完毕！");
            log.info("最新获取的代理地址列表：{}", addressSet);
        }
    }

    public String getTargetUrl()
    {
        return StringUtil.isEmpty(targetUrl) ? DEFAULT_TARGET_URL : targetUrl;
    }

    public void setTargetUrl(String targetUrl)
    {
        this.targetUrl = targetUrl;
    }
}
