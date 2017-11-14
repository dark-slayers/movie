package person.liuxx.movie.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import person.liuxx.movie.config.ElConfig;

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
        log.info("ProxyWebParseUrl : {}");
        return "TEST OVER !\n";
    }
}
