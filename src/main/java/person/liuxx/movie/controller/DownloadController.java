package person.liuxx.movie.controller;

import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import person.liuxx.movie.dto.DownloadVO;
import person.liuxx.movie.service.DownloadServie;
import person.liuxx.movie.service.ResponseService;
import person.liuxx.util.service.exception.SearchException;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2019年10月29日 上午10:20:43
 * @since 1.0.0
 */
@RestController
@Api(value = "下载控制器")
public class DownloadController
{
    @Autowired
    private DownloadServie service;
    @Autowired
    private ResponseService responseService;

    @ApiOperation(value = "获取指定文件夹下的文件列表和文件夹列表", notes = "获取指定文件夹下的文件列表和文件夹列表")
    @GetMapping("/download/vo")
    public DownloadVO list(@RequestParam String path)
    {
        return service.getDownloadVO(path).<SearchException> orElseThrow(() ->
        {
            throw new SearchException("获取文件列表失败！");
        });
    }

    @ApiOperation(value = "根据path获取文件", notes = "根据path获取文件")
    @ApiImplicitParam(name = "path", value = "文件路径", required = true, dataType = "String")
    @GetMapping(value = "/download/file")
    @ResponseBody
    public ResponseEntity<Resource> downloadResource(@RequestParam String path)
    {
        return Optional.ofNullable(path)
                .map(p -> Paths.get(p))
                .flatMap(p -> responseService.getResource(p))
                .<SearchException> orElseThrow(() ->
                {
                    throw new SearchException("获取文件失败！");
                });
    }
}
