package person.liuxx.movie.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import person.liuxx.movie.config.ElConfig;
import person.liuxx.movie.proxy.ProxyAddress;
import person.liuxx.movie.proxy.source.ProxySource;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月30日 上午11:17:45
 * @since 1.0.0
 */
@RestController
public class TestController
{
    private Logger log = LogManager.getLogger();
    @Autowired
    ElConfig config;

    @GetMapping("/test")
    public String test()
    {
        Set<ProxyAddress> list = ProxySource.allSource()
                .flatMap(s -> s.getProxyAddressStream())
                .collect(Collectors.toSet());
        log.info("ProxyWebParseUrl : {}", list);
        return "TEST OVER !\n" + list;
    }
}
