package person.liuxx.movie.service;

import java.util.Optional;

import person.liuxx.movie.dto.DownloadVO;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2019年10月29日 上午10:27:10
 * @since 1.0.0
 */
public interface DownloadServie
{
    Optional<DownloadVO> getDownloadVO(String path);
}
