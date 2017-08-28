package person.liuxx.movie.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import person.liuxx.movie.domain.MovieDO;



/** 
* @author  刘湘湘 
* @version 1.0.0<br>创建时间：2017年8月25日 下午5:10:06
* @since 1.0.0 
*/
public interface MovieRepository extends JpaRepository<MovieDO, Long>
{
}
