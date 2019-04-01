package person.liuxx.movie.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import person.liuxx.movie.dto.MovieDTO;
import person.liuxx.movie.entity.MovieDO;
import person.liuxx.util.service.reponse.EmptySuccedResponse;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月28日 下午3:21:38
 * @since 1.0.0
 */
public interface MovieService
{
    /**
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2017年8月28日 下午3:23:07
     * @since 1.0.0
     * @param mov
     * @return
     */
    Optional<MovieDO> save(MovieDTO movie);

    /**
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2017年11月29日 下午4:22:28
     * @since 1.0.0
     * @return
     */
    List<MovieDO> list();

    Optional<ResponseEntity<Resource>> getResource(String code);

    Optional<EmptySuccedResponse> getResource(HttpServletResponse response, String code);
}
