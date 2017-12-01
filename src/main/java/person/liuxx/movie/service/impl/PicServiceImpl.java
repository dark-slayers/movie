package person.liuxx.movie.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import person.liuxx.movie.dao.MovieRepository;
import person.liuxx.movie.domain.MovieDO;
import person.liuxx.movie.service.PicService;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年12月1日 下午4:48:13
 * @since 1.0.0
 */
@Service
public class PicServiceImpl implements PicService
{
    @Autowired
    private MovieRepository movieDao;

    @Override
    public Optional<String> getPic(HttpServletResponse response, String code)
    {
        Optional<MovieDO> movie = movieDao.findByCode(code);
        movie.map(m -> Paths.get(m.getMainPic())).ifPresent(p ->
        {
            try (InputStream fis = Files.newInputStream(p);
                    OutputStream os = response.getOutputStream();)
            {
                int count = 0;
                byte[] buffer = new byte[1024 * 8];
                while ((count = fis.read(buffer)) != -1)
                {
                    os.write(buffer, 0, count);
                    os.flush();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        return Optional.of("ok");
    }
}
