package person.liuxx.movie.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

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
    @Override
    public Optional<MovieDO> save(MovieDTO mov)
    {
        MovieDO movie = new MovieDO();
        movie.setId(1L);
        movie.setCode("TEST-AOP-001");
        movie.setLevel(3);
        movie.setActress("UNKNOWN");
        movie.setPath("D:\\temp");
        movie.setUniform("UNKNOWN");
        return Optional.of(movie);
    }
}
