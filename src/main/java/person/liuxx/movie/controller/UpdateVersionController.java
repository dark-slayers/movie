package person.liuxx.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import person.liuxx.movie.service.UpdateVersionService;
import person.liuxx.util.service.exception.UpdateException;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月28日 下午3:22:15
 * @since 1.0.0
 */
@RestController
@Api(value = "版本升级更新数据控制器")
public class UpdateVersionController
{
    @Autowired
    private UpdateVersionService updateVersionService;

    @ApiOperation(value = "获取视频列表", notes = "获取视频列表")
    @GetMapping("/new_version")
    public String version()
    {
        return updateVersionService.updateVersion().<UpdateException> orElseThrow(() ->
        {
            throw new UpdateException("版本升级更新数据失败");
        });
    }
}
