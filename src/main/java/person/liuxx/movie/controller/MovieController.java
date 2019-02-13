package person.liuxx.movie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月28日 下午3:22:15
 * @since 1.0.0
 */
@RestController
@Api(value = "视频对象控制器")
public class MovieController
{
    @Autowired
    private MovieService movieService;

    @ApiOperation(value = "添加一个新的视频", notes = "解析MovDTO信息，增加新的视频")
    @ApiImplicitParams(
    { @ApiImplicitParam(name = "movie", value = "视频信息实体MovieDTO", required = true,
            dataType = "MovieDTO") })
    @PostMapping("/movie")
    @ResponseStatus(value = HttpStatus.CREATED)
    public MovieDO save(@RequestBody MovieDTO movie)
    {
        return movieService.save(movie).<MovieSaveFailedException> orElseThrow(() ->
        {
            throw new MovieSaveFailedException("添加视频失败，视频信息：" + movie);
        });
    }

    @ApiOperation(value = "获取视频列表", notes = "获取视频列表")
    @GetMapping("/movies")
    public List<MovieDO> list()
    {
        return movieService.list();
    }
}
