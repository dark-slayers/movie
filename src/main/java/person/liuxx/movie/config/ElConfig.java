package person.liuxx.movie.config;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

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
    @Value("classpath:proxywebs.json")
    private Resource proxyWebs;

    public String listProxyWebs() throws IOException
    {
        return IOUtils.toString(proxyWebs.getInputStream());
    }
}
