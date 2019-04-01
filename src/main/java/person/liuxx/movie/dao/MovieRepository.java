package person.liuxx.movie.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import person.liuxx.movie.entity.MovieDO;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月25日 下午5:10:06
 * @since 1.0.0
 */
public interface MovieRepository extends JpaRepository<MovieDO, Long>
{
    List<MovieDO> findByCodeContaining(String code);

    Optional<MovieDO> findByCode(String code);
}
