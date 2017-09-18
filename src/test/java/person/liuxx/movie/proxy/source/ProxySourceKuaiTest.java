package person.liuxx.movie.proxy.source;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import person.liuxx.movie.proxy.ProxyAddress;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年9月18日 下午5:00:21
 * @since 1.0.0
 */
public class ProxySourceKuaiTest
{
    /**
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2017年9月18日 下午5:00:21
     * @since 1.0.0
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    /**
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2017年9月18日 下午5:00:21
     * @since 1.0.0
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    /**
     * {@link person.liuxx.movie.proxy.source.ProxySource#getProxyAddressStream()}
     * 的测试方法。
     */
    @Test
    public void testGetProxyAddressStream()
    {
        ProxySource source = new ProxySourceKuai();
        List<ProxyAddress> list = source.getProxyAddressStream().collect(Collectors.toList());
        System.out.println(list);
        assertTrue(true);
    }
}
