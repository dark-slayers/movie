package person.liuxx.movie.config;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import person.liuxx.util.log.LogUtil;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月30日 上午10:57:37
 * @since 1.0.0
 */
@Configuration
@ComponentScan
public class ElConfig
{
    Logger log = LoggerFactory.getLogger(ElConfig.class);
    @Value("classpath:proxywebs.json")
    private Resource proxyWebs;
    @Value("classpath:path_rule.json")
    private Resource pathRules;

    public String listProxyWebs() throws IOException
    {
        return IOUtils.toString(proxyWebs.getInputStream());
    }

    public Optional<String> listPathRule()
    {
        Optional<String> result = Optional.empty();
        try
        {
            String text = IOUtils.toString(pathRules.getInputStream());
            result = Optional.of(text);
        } catch (IOException e)
        {
            log.error("从配置文件path_rule.json中获取信息失败！");
            log.error(LogUtil.errorInfo(e));
        }
        return result;
    }
}
