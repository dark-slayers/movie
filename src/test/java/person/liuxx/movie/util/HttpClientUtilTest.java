package person.liuxx.movie.util;

import static org.junit.Assert.fail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import person.liuxx.movie.proxy.source.ProxySource;
import person.liuxx.movie.proxy.source.ProxySourceXiCi;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年10月25日 下午3:23:19
 * @since 1.0.0
 */
public class HttpClientUtilTest
{
    private Logger log = LogManager.getLogger();

    /**
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2017年10月25日 下午3:23:19
     * @since 1.0.0
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
    }

    /**
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2017年10月25日 下午3:23:19
     * @since 1.0.0
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * {@link person.liuxx.movie.util.HttpClientUtil#simpleGet(java.lang.String)}
     * 的测试方法。
     */
    @Test
    public void testSimpleGet()
    {
        // Optional<String> response =
        // HttpClientUtil.simpleGet("http://www.xicidaili.com/nn/");
        // response.ifPresent(r -> log.debug("返回结果：{}", r));
        ProxySource p = new ProxySourceXiCi();
        p.getProxyAddressStream().forEach(log::debug);
    }

    /**
     * {@link person.liuxx.movie.util.HttpClientUtil#cookieGet(java.lang.String, java.lang.String)}
     * 的测试方法。
     */
    @Test
    public void testCookieGet()
    {
        fail("尚未实现");
    }
}
