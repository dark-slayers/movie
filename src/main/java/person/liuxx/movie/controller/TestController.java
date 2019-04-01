package person.liuxx.movie.controller;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import person.liuxx.movie.entity.MovieDO;
import person.liuxx.movie.service.MovieService;
import person.liuxx.util.file.FileUtil;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月30日 上午11:17:45
 * @since 1.0.0
 */
@RestController
public class TestController
{
    private Logger log = LogManager.getLogger();
    @Autowired
    private MovieService movieService;

    @GetMapping("/test/db")
    public List<String> test()
    {
        log.info("test db !");
        List<String> result = new ArrayList<>();
        List<MovieDO> list = movieService.list();
        for (MovieDO m : list)
        {
            String info = m.getCode();
            boolean hasError = false;
            if (!FileUtil.existsFile(Paths.get(m.getPath())))
            {
                hasError = true;
                info = info + ":视频文件不存在";
            }
            if (!FileUtil.existsFile(Paths.get(m.getMainPic())))
            {
                hasError = true;
                info = info + ",图片文件不存在";
            }
            if (hasError)
            {
                result.add(info + "!");
            }
        }
        return result;
    }
}
