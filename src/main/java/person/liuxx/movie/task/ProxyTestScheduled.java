package person.liuxx.movie.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import person.liuxx.movie.service.ProxyTestService;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月30日 下午4:31:36
 * @since 1.0.0
 */
@Service
public class ProxyTestScheduled
{
    @Autowired
    private ProxyTestService proxyTestService;

    @Scheduled(fixedRate = 600000)
    public void reportCurrentTime()
    {
        proxyTestService.flushAddressList();
        proxyTestService.testAddressList();
    }
}
