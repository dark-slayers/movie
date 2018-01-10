package person.liuxx.movie.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import person.liuxx.movie.dao.MovieRepository;
import person.liuxx.movie.domain.MovieDO;
import person.liuxx.movie.service.UpdateVersionService;
import person.liuxx.util.file.FileName;
import person.liuxx.util.file.FileUtil;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年1月10日 下午2:08:55
 * @since 1.0.0
 */
@Service
public class UpdateVersionServiceImpl implements UpdateVersionService
{
    @Autowired
    private MovieRepository movieDao;

    @Override
    public Optional<String> updateVersion()
    {
        List<MovieDO> list = movieDao.findAll();
        try
        {
            for (MovieDO m : list)
            {
                String path = m.getPath();
                path = Paths.get(path).toString();
                m.setPath(path);
                Path source = Paths.get(path);
                Path parent = source.getParent();
                Set<String> picExtension = new HashSet<>(Arrays.asList(new String[]
                { "jpg", "bmp", "gif" }));
                Optional<FileName> picFileNameOptional = Files.walk(parent)
                        .filter(p -> Objects.equals(parent, p.getParent()))
                        .filter(p -> FileUtil.existsFile(p))
                        .map(p -> FileUtil.getFileName(p).get())
                        .filter(p -> picExtension.contains(p.getExtension().toLowerCase()))
                        .findFirst();
                Optional<String> picOptional = picFileNameOptional.map(p -> parent.resolve(p
                        .toString())).map(p -> p.toString());
                picOptional.ifPresent(p -> m.setMainPic(p));
                movieDao.save(m);
            }
        } catch (Exception e)
        {
        }
        return Optional.of("OK");
    }
}
