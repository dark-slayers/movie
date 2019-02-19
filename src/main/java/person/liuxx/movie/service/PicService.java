package person.liuxx.movie.service;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import person.liuxx.util.service.reponse.EmptySuccedResponse;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年12月1日 下午4:48:04
 * @since 1.0.0
 */
public interface PicService
{
    /**
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2017年12月1日 下午4:51:58
     * @since 1.0.0
     * @param response
     * @param code
     * @return
     */
    Optional<EmptySuccedResponse> getPic(HttpServletResponse response, String code);
}
