package person.liuxx.movie.controller;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import person.liuxx.movie.domain.MovieDO;
import person.liuxx.movie.dto.MovieDTO;
import person.liuxx.movie.exception.MovieSaveFailedException;
import person.liuxx.movie.service.MovieService;

import person.liuxx.util.log.LogUtil;
import person.liuxx.util.service.reponse.ErrorResponse;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月28日 下午3:22:15
 * @since 1.0.0
 */
@RestController
@RequestMapping("/movie")
@Api(value = "视频对象控制器")
public class MovieController
{
    private Logger log = LogManager.getLogger();
    @Autowired
    private MovieService movService;

    @ApiOperation(value = "添加一个新的视频", notes = "解析传来的MovDTO信息，增加新的视频")
    @ApiImplicitParams(
    { @ApiImplicitParam(name = "movie", value = "视频信息实体MovieDTO", required = true,
            dataType = "MovieDTO") })
    @PostMapping("/info")
    @ResponseStatus(value = HttpStatus.CREATED)
    public MovieDO save(@RequestBody MovieDTO movie)
    {
        log.info("请求添加视频：{}", movie);
        if (Objects.isNull(movie))
        {
            throw new IllegalArgumentException("请求参数不能为空！");
        }
        return movService.save(movie).orElseThrow(() ->
        {
            throw new MovieSaveFailedException("添加视频失败，视频信息：" + movie);
        });
    }

    @ExceptionHandler(
    { MovieSaveFailedException.class, })
    public ErrorResponse exceptionHandler(Exception e)
    {
        log.error(LogUtil.errorInfo(e));
        switch (e.getClass().getName())
        {
        case "person.liuxx.movie.exception.MovieSaveFailedException":
            {
                return new ErrorResponse(500, 50002, "视频保存失败", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        case "person.liuxx.movie.exception.MovieRemoveFailedException":
            {
                return new ErrorResponse(500, 50003, "书籍删除失败", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        case "person.liuxx.movie.exception.MovieUpdateFailedException":
            {
                return new ErrorResponse(500, 50004, "视频更新失败", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        case "person.liuxx.movie.exception.MovieLoadFailedException":
            {
                return new ErrorResponse(500, 50005, "视频加载失败", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        case "person.liuxx.movie.exception.MovieNotFoundException":
            {
                return new ErrorResponse(404, 40402, "视频获取失败", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        case "java.lang.IllegalArgumentException":
            {
                return new ErrorResponse(400, 40001, "请求参数格式错误", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        default:
            {
                return new ErrorResponse(500, 50001, "未知错误", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        }
    }
}
