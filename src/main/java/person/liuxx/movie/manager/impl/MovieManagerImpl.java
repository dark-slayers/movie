package person.liuxx.movie.manager.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import person.liuxx.movie.business.path.PathRule;
import person.liuxx.movie.config.ElConfig;
import person.liuxx.movie.domain.MovieDO;
import person.liuxx.movie.dto.MovieDTO;
import person.liuxx.movie.exception.MovieSaveFailedException;
import person.liuxx.movie.manager.MovieManager;
import person.liuxx.util.file.FileName;
import person.liuxx.util.file.FileUtil;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年10月31日 下午2:50:50
 * @since 1.0.0
 */
@Service
public class MovieManagerImpl implements MovieManager
{
    private Logger log = LoggerFactory.getLogger(MovieManagerImpl.class);
    private static Set<String> picExtension = new HashSet<>(Arrays.asList(new String[]
    { "jpg", "bmp", "gif" }));
    @Autowired
    private ElConfig ruleConfig;

    @Override
    public Optional<MovieDO> formatAndMove(MovieDTO movieFile)
    {
        return Optional.ofNullable(movieFile).flatMap(m -> move(m));
    }

    private Optional<MovieDO> move(MovieDTO movieFile)
    {
        log.info("查询分类规则，将视频移动至预设位置..");
        String code = movieFile.getCode();
        List<PathRule> ruleList = ruleConfig.listPathRule();
        Optional<Path> targetPath = movieFile.targetPath(ruleList);
        Optional<MovieDO> op = targetPath.flatMap(p ->
        {
            move(movieFile, p, code);
            return movieFile.mapToDO();
        });
        return op;
    }

    private void move(MovieDTO movieFile, Path target, String code)
    {
        try
        {
            log.info("检查视频目录中是否存在图片文件...");
            Path source = Paths.get(movieFile.getPath());
            Path parent = source.getParent();
            FileName movieFileName = FileUtil.getFileName(source).get();
            Optional<FileName> picFileNameOptional = Files.walk(parent)
                    .filter(p -> Objects.equals(parent, p.getParent()))
                    .filter(p -> FileUtil.existsFile(p))
                    .map(p -> FileUtil.getFileName(p).get())
                    .filter(p -> picExtension.contains(p.getExtension().toLowerCase()))
                    .findFirst();
            log.info("移动视频文件和图片文件...");
            if (!Files.exists(target))
            {
                Files.createDirectories(target);
                log.info("移动视频文件...");
                Path targetMovie = Paths.get(target.toString(), code + "." + movieFileName
                        .getExtension());
                Files.move(source, targetMovie);
                movieFile.setPath(targetMovie.toString());
                if (picFileNameOptional.isPresent())
                {
                    log.info("移动图片文件...");
                    FileName picFileName = picFileNameOptional.get();
                    Path targetPic = Paths.get(target.toString(), code + "." + picFileName
                            .getExtension());
                    Files.move(Paths.get(parent.toString(), picFileName.toString()), targetPic);
                    movieFile.setMainPic(targetPic.toString());
                }
                log.info("移动视频文件和图片文件成功，删除源文件夹！");
                Files.deleteIfExists(source.getParent());
            } else
            {
                throw new MovieSaveFailedException("目标文件夹已经存在！");
            }
        } catch (IOException e)
        {
            throw new MovieSaveFailedException("添加视频失败！", e);
        }
    }
}
