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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import person.liuxx.movie.business.path.PathRule;
import person.liuxx.movie.config.ElConfig;
import person.liuxx.movie.dao.MovieRepository;
import person.liuxx.movie.domain.MovieDO;
import person.liuxx.movie.dto.MovieDTO;
import person.liuxx.movie.service.UpdateVersionService;
import person.liuxx.util.file.FileName;
import person.liuxx.util.file.FileUtil;
import person.liuxx.util.log.LogUtil;

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
    @Autowired
    private ElConfig ruleConfig;
    private Logger log = LoggerFactory.getLogger(UpdateVersionServiceImpl.class);

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
                if (!FileUtil.existsFile(Paths.get(m.getPath())))
                {
                    List<PathRule> ruleList = ruleConfig.listPathRule();
                    Optional<MovieDTO> optional = Optional.ofNullable(MovieDTO.of(m));
                    log.debug("ruleList:{}", ruleList);
                    log.debug("optional:{}", optional);
                    log.debug("optional:{}", optional.flatMap(d -> d.targetPath(ruleList)));
                    log.debug("optional:{}", optional.flatMap(d -> d.targetPath(ruleList)).map(
                            p -> p.resolve(Paths.get(m.getPath()).getFileName())));
                    Optional<Path> targetPath = optional.flatMap(d -> d.targetPath(ruleList)).map(
                            p -> p.resolve(m.getCode() + FileUtil.getFileName(Paths.get(m
                                    .getPath())).get().getExtension()));
                    log.debug("targetPath:{}", targetPath);
                    targetPath.filter(p -> FileUtil.existsFile(p)).ifPresent(p ->
                    {
                        log.debug("p:{}", p);
                        m.setPath(p.toString());
                    });
                }
                log.debug("m:{}", m);
                Path source = Paths.get(m.getPath());
                m.setPath(source.toString());
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
            log.error(LogUtil.errorInfo(e));
        }
        return Optional.of("OK");
    }
}
