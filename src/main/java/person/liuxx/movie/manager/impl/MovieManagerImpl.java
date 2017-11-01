package person.liuxx.movie.manager.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import person.liuxx.movie.business.path.PathRule;
import person.liuxx.movie.config.ElConfig;
import person.liuxx.movie.domain.MovieDO;
import person.liuxx.movie.dto.MovieDTO;
import person.liuxx.movie.manager.MovieManager;
import person.liuxx.util.base.StringUtil;
import person.liuxx.util.file.FileName;
import person.liuxx.util.file.FileUtil;
import person.liuxx.util.log.LogUtil;

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
    @Autowired
    private ElConfig ruleConfig;

    @Override
    public Optional<MovieDO> format(MovieDTO movieFile)
    {
        return Optional.ofNullable(movieFile).filter(m -> isValid(m)).flatMap(m -> move(m));
    }

    private boolean isValid(MovieDTO movieFile)
    {
        return Optional.ofNullable(movieFile)
                .filter(m -> !StringUtil.isBlank(m.getCode()))
                .filter(m -> !StringUtil.isBlank(m.getPath()))
                .filter(m -> FileUtil.existsFile(Paths.get(m.getPath())))
                .isPresent();
    }

    private Optional<MovieDO> move(MovieDTO movieFile)
    {
        log.info("移动指定的视频文件...");
        String code = movieFile.getCode().toUpperCase();
        List<PathRule> ruleList = ruleConfig.listPathRule()
                .map(r -> JSON.parseArray(r, PathRule.class))
                .orElse(new ArrayList<>());
        Optional<Path> targetPath = ruleList.stream()
                .filter(r -> Objects.equals(r.getActress(), movieFile.getActress()))
                .findAny()
                .map(r -> Paths.get(r.getPath(), String.valueOf(movieFile.getLevel()), code));
        Path movieFilePath = Paths.get(movieFile.getPath());
        FileName movieFileName = FileUtil.getFileName(movieFilePath).get();
        Optional<MovieDO> op = targetPath.map(p ->
        {
            if (!Files.exists(p))
            {
                try
                {
                    Files.createDirectories(p);
                    Files.move(movieFilePath, Paths.get(p.toString(), code + "." + movieFileName
                            .getExtension()));
                    // movieFilePath.toFile().renameTo(code);
                } catch (Exception e)
                {
                    log.error(LogUtil.errorInfo(e));
                }
            }
            MovieDO result = new MovieDO();
            result.setCode(code);
            result.setLevel(movieFile.getLevel());
            result.setPath(movieFile.getPath());
            String tempActress = StringUtil.isEmpty(movieFile.getActress()) ? "UNKNOWN"
                    : movieFile.getActress();
            result.setActress(tempActress);
            String tempLabel = StringUtil.isEmpty(movieFile.getLabel()) ? "UNKNOWN"
                    : movieFile.getLabel();
            result.setLabel(tempLabel);
            return result;
        });
        return op;
    }
}
