package person.liuxx.movie.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import person.liuxx.movie.proxy.ProxyListTest;
import person.liuxx.movie.proxy.ProxyTestResult;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月29日 下午2:50:49
 * @since 1.0.0
 */
@RestController
@RequestMapping("/proxy")
@Api(value = "代理控制器")
public class ProxyController
{
    @ApiOperation(value = "获取代理地址列表的测试结果", notes = "获取测试代理后的测试结果")
    @GetMapping("/list")
    List<ProxyTestResult> list()
    {
        return new ProxyListTest("http://www.141jav.com/latest/2017-08-27/").run();
    }
}
