package person.liuxx.movie.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import person.liuxx.movie.dao.MovieRepository;
import person.liuxx.movie.dto.MovieDTO;
import person.liuxx.movie.entity.MovieDO;
import person.liuxx.movie.manager.MovieManager;
import person.liuxx.movie.service.MovieService;
import person.liuxx.movie.service.ResponseService;
import person.liuxx.util.service.reponse.EmptySuccedResponse;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月28日 下午3:21:48
 * @since 1.0.0
 */
@Service
public class MovieServiceImpl implements MovieService
{
    private Logger log = LoggerFactory.getLogger(MovieServiceImpl.class);
    @Autowired
    private MovieRepository movieDao;
    @Autowired
    private MovieManager movieManager;
    @Autowired
    private ResponseService responseService;

    @Override
    public Optional<MovieDO> save(MovieDTO movie)
    {
        log.info("添加新的视频文件：{}", movie);
        Optional<MovieDO> result = movieManager.formatAndMove(movie).flatMap(m -> m.mapToDO());
        result.ifPresent(m -> movieDao.save(m));
        return result;
    }

    @Override
    public List<MovieDO> list()
    {
        log.info("获取视频列表...");
        return movieDao.findAll();
    }

    @Override
    public Optional<ResponseEntity<Resource>> getResource(String code)
    {
        return getFilePath(code).flatMap(p -> responseService.getResource(p));
    }

    @Override
    public Optional<EmptySuccedResponse> getResource(HttpServletResponse response, String code)
    {
        log.info("download file,code:{}", code);
        return getFilePath(code).flatMap(p -> responseService.getResource(response, p));
    }

    private Optional<Path> getFilePath(String code)
    {
        return movieDao.findByCode(code).map(m -> m.getPath()).map(p -> Paths.get(p));
    }
}
