package person.liuxx.movie.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import person.liuxx.movie.config.ElConfig;
import person.liuxx.movie.dto.ProxyWebDTO;
import person.liuxx.movie.proxy.ProxyWebParseUrl;
import person.liuxx.util.log.LogUtil;

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
        // ProxyWebDTO p = new ProxyWebDTO();
        // p.setUrl("http://31f.cn/");
        // p.setSingle(true);
        // p.setMaxDeep(0);
        // p.setIpIndex(1);
        // p.setPortIndex(2);
        // p.setTableTag("table table-striped");
        // p.setTableTagType("Class");
        // p.setSubUrl(new String[]
        // { "http-proxy", "https-proxy" });
        // List<ProxyWebDTO> list = new ArrayList<>();
        // list.add(p);
        // ProxyWebDTO p1 = new ProxyWebDTO();
        // p1.setUrl("http://www.xicidaili.com/");
        // p1.setSingle(false);
        // p1.setMaxDeep(10);
        // p1.setIpIndex(0);
        // p1.setPortIndex(1);
        // p1.setTableTag("ip_list");
        // p1.setTableTagType("Id");
        // p1.setSubUrl(new String[]
        // { "wt", "wn", "nt", "nn" });
        // list.add(p1);
        String s;
        try
        {
            s = config.listProxyWebs();
            List<ProxyWebDTO> list = JSON.parseArray(s, ProxyWebDTO.class);
            List<ProxyWebParseUrl> l = list.stream()
                    .flatMap(p -> ProxyWebParseUrl.createStream(p))
                    .collect(Collectors.toList());
            log.info("ProxyWebParseUrl : {}", l);
        } catch (IOException e)
        {
            log.error(LogUtil.errorInfo(e));
        }
        return "TEST OVER !";
    }
}
