package person.liuxx.movie.manager;

import java.util.Optional;

import person.liuxx.movie.domain.MovieDO;
import person.liuxx.movie.dto.MovieDTO;

/** 
* @author  刘湘湘 
* @version 1.0.0<br>创建时间：2017年10月31日 下午2:50:33
* @since 1.0.0 
*/
public interface MovieManager
{

    /** 对MovieDTO进行格式化处理（文件重命名，文件移动等）
    * @author  刘湘湘 
    * @version 1.0.0<br>创建时间：2017年10月31日 下午2:53:19
    * @since 1.0.0 
    * @param movie
    * @return
    */
    Optional<MovieDO> formatAndMove(MovieDTO movie);
}
