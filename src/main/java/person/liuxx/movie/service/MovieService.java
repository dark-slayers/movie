package person.liuxx.movie.service;

import java.util.List;
import java.util.Optional;

import person.liuxx.movie.domain.MovieDO;
import person.liuxx.movie.dto.MovieDTO;

/** 
* @author  刘湘湘 
* @version 1.0.0<br>创建时间：2017年8月28日 下午3:21:38
* @since 1.0.0 
*/
public interface MovieService
{

    /** 
    * @author  刘湘湘 
    * @version 1.0.0<br>创建时间：2017年8月28日 下午3:23:07
    * @since 1.0.0 
    * @param mov
    * @return
    */
    Optional<MovieDO> save(MovieDTO movie);

    /** 
    * @author  刘湘湘 
    * @version 1.0.0<br>创建时间：2017年11月29日 下午4:22:28
    * @since 1.0.0 
    * @return
    */
    List<MovieDO> list();
}
