package person.liuxx.movie.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import person.liuxx.movie.dao.MovieRepository;
import person.liuxx.movie.domain.MovieDO;
import person.liuxx.movie.dto.MovieDTO;
import person.liuxx.movie.service.MovieService;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月28日 下午3:21:48
 * @since 1.0.0
 */
@Service
public class MovieServiceImpl implements MovieService
{
    @Autowired
    private MovieRepository movieDao;

    @Override
    public Optional<MovieDO> save(MovieDTO movie)
    {
        Optional<MovieDO> movieOptional = MovieDO.createOptional(movie);
        Optional<MovieDO> result = movieOptional.map(m -> movieDao.save(m));
        return result;
    }
}
