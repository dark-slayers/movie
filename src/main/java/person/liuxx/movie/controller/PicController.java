package person.liuxx.movie.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import person.liuxx.movie.service.PicService;
import person.liuxx.util.service.exception.SearchException;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年12月1日 下午3:42:35
 * @since 1.0.0
 */
@RestController
@Api(value = "图片对象控制器")
public class PicController
{
    @Autowired
    private PicService picService;

    @ApiOperation(value = "根据code获取图片", notes = "根据code获取图片，直接将图片的字节流写入响应的HttpServletResponse")
    @ApiImplicitParam(name = "code", value = "视频编号", required = true, dataType = "String")
    @RequestMapping(value = "/pic/{code}")
    public String showPic(HttpServletResponse response, @PathVariable String code)
    {
        return picService.getPic(response, code).<SearchException>orElseThrow(() ->
        {
            throw new SearchException("获取图片失败！");
        });
    }
}
