package person.liuxx.movie.service.impl;

import java.io.IOException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import person.liuxx.movie.config.ElConfig;
import person.liuxx.movie.dto.ProxyWebDTO;
import person.liuxx.movie.proxy.ProxyAddress;
import person.liuxx.movie.proxy.ProxyTestResult;
import person.liuxx.movie.proxy.ProxyTestTask;
import person.liuxx.movie.proxy.ProxyWebParseUrl;
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
    @Autowired
    private ElConfig config;

    @Override
    public List<ProxyTestResult> listTestResult(String targetAddress)
    {
        List<ProxyTestResult> result = new ArrayList<>();
        List<ProxyAddress> info = readList();
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

    private List<ProxyAddress> readList()
    {
        List<ProxyAddress> result = new ArrayList<>();
        try
        {
            String s = config.listProxyWebs();
            List<ProxyWebDTO> list = JSON.parseArray(s, ProxyWebDTO.class);
            result = list.stream()
                    .flatMap(p -> ProxyWebParseUrl.createStream(p))
                    .flatMap(u -> u.getProxyAddressStream())
                    .collect(Collectors.toList());
        } catch (IOException e)
        {
            log.error(LogUtil.errorInfo(e));
        }
        return result;
    }
}
