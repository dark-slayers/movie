package person.liuxx.movie.service;

import java.util.List;

import person.liuxx.movie.domain.ProxyAddressDO;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月30日 下午1:58:18
 * @since 1.0.0
 */
public interface ProxyTestService
{
    List<ProxyAddressDO> listAddress();

    void flushAddressList();

    /**
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2017年9月4日 下午4:19:26
     * @since 1.0.0
     */
    void testAddressList();
}
