package person.liuxx.movie.service;

import java.util.List;
import java.util.Map;

import person.liuxx.movie.proxy.ProxyTestResult;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月30日 下午1:58:18
 * @since 1.0.0
 */
public interface ProxyTestService
{
    List<ProxyTestResult> listTestResult(String targetAddress);

    Map<String, Long> mapTestResult(String targetAddress);

    void startTask(String targetAddress);
}
