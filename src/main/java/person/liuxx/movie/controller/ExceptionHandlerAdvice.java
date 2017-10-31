package person.liuxx.movie.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import person.liuxx.util.log.LogUtil;
import person.liuxx.util.service.reponse.ErrorResponse;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年10月31日 下午3:11:37
 * @since 1.0.0
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice
{
    private Logger log = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(
    { Exception.class })
    public ErrorResponse exceptionHandler(Exception e)
    {
        log.error(LogUtil.errorInfo(e));
        switch (e.getClass().getName())
        {
        case "person.liuxx.movie.exception.MovieSaveFailedException":
            {
                return new ErrorResponse(500, 50002, "视频保存失败", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        case "person.liuxx.movie.exception.MovieRemoveFailedException":
            {
                return new ErrorResponse(500, 50003, "书籍删除失败", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        case "person.liuxx.movie.exception.MovieUpdateFailedException":
            {
                return new ErrorResponse(500, 50004, "视频更新失败", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        case "person.liuxx.movie.exception.MovieLoadFailedException":
            {
                return new ErrorResponse(500, 50005, "视频加载失败", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        case "person.liuxx.movie.exception.MovieNotFoundException":
            {
                return new ErrorResponse(404, 40402, "视频获取失败", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        case "java.lang.IllegalArgumentException":
            {
                return new ErrorResponse(400, 40001, "请求参数格式错误", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        default:
            {
                return new ErrorResponse(500, 50001, "未知错误", "失败信息：" + LogUtil.errorInfo(e),
                        "more info");
            }
        }
    }
}
