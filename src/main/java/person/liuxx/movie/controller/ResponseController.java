package person.liuxx.movie.controller;

import java.nio.file.Paths;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import person.liuxx.movie.service.MovieService;
import person.liuxx.movie.service.ResponseService;
import person.liuxx.util.service.exception.SearchException;
import person.liuxx.util.service.reponse.EmptySuccedResponse;

/**
 * @author 刘湘湘
 * @since 2019年2月19日 下午3:57:55
 */
@RestController
@Api(value = "资源控制器")
public class ResponseController
{
    private Logger log = LoggerFactory.getLogger(ResponseController.class);
    @Autowired
    private MovieService movieService;
    @Autowired
    private ResponseService responseService;

    @ApiOperation(value = "根据code获取文件", notes = "根据code获取文件，直接将文件的字节流写入响应的HttpServletResponse")
    @ApiImplicitParam(name = "code", value = "视频编号", required = true, dataType = "String")
    @RequestMapping(value = "/file/{code}")
    public EmptySuccedResponse getResource(HttpServletResponse response, @PathVariable String code)
    {
        log.info("download file,code:{}", code);
        return movieService.getResource(response, code).<SearchException> orElseThrow(() ->
        {
            throw new SearchException("获取文件失败！");
        });
    }

    @ApiOperation(value = "根据code获取文件", notes = "根据code获取文件")
    @ApiImplicitParam(name = "code", value = "视频编号", required = true, dataType = "String")
    @RequestMapping(value = "/file2/{code}")
    @ResponseBody
    public ResponseEntity<Resource> getResource(@PathVariable String code)
    {
        return movieService.getResource(code).<SearchException> orElseThrow(() ->
        {
            throw new SearchException("获取文件失败！");
        });
    }

    @ApiOperation(value = "根据path获取文件", notes = "根据path获取文件")
    @ApiImplicitParam(name = "path", value = "文件路径", required = true, dataType = "String")
    @GetMapping(value = "/file")
    @ResponseBody
    public ResponseEntity<Resource> downloadResource(@RequestParam String path)
    {
        log.info("download file:{}", path);
        return Optional.ofNullable(path)
                .map(p -> Paths.get(p))
                .flatMap(p -> responseService.getResource(p))
                .<SearchException> orElseThrow(() ->
                {
                    throw new SearchException("获取文件失败！");
                });
    }
}
