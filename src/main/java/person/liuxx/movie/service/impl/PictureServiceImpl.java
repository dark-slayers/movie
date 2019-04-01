package person.liuxx.movie.service.impl;

import java.nio.file.Paths;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import person.liuxx.movie.dao.MovieRepository;
import person.liuxx.movie.entity.MovieDO;
import person.liuxx.movie.service.PictureService;
import person.liuxx.movie.service.ResponseService;
import person.liuxx.util.service.reponse.EmptySuccedResponse;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年12月1日 下午4:48:13
 * @since 1.0.0
 */
@Service
public class PictureServiceImpl implements PictureService
{
    @Autowired
    private MovieRepository movieDao;
    @Autowired
    private ResponseService responseService;

    @Override
    public Optional<EmptySuccedResponse> getPicture(HttpServletResponse response, String code)
    {
        Optional<MovieDO> movie = movieDao.findByCode(code);
        return movie.map(m -> m.getMainPic()).map(s -> Paths.get(s)).flatMap(p -> responseService
                .getResource(response, p));
    }
}
