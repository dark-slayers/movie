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

import com.alibaba.fastjson.JSON;

import person.liuxx.movie.business.path.PathRule;
import person.liuxx.movie.config.ElConfig;
import person.liuxx.movie.domain.MovieDO;
import person.liuxx.movie.dto.MovieDTO;
import person.liuxx.movie.manager.MovieManager;
import person.liuxx.util.base.StringUtil;
import person.liuxx.util.file.FileUtil;
import person.liuxx.util.log.LogUtil;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年10月31日 下午2:50:50
 * @since 1.0.0
 */
public class MovieManagerImpl implements MovieManager
{
    Logger log = LoggerFactory.getLogger(MovieManagerImpl.class);
    @Autowired
    private ElConfig ruleConfig;

    @Override
    public Optional<MovieDO> format(MovieDTO movieFile)
    {
        return Optional.ofNullable(movieFile).filter(m -> isValid(m)).map(m ->
        {
            MovieDO result = new MovieDO();
            result.setCode(m.getCode().toUpperCase());
            result.setLevel(m.getLevel());
            result.setPath(m.getPath());
            String tempActress = StringUtil.isEmpty(m.getActress()) ? "UNKNOWN" : m.getActress();
            result.setActress(tempActress);
            String tempLabel = StringUtil.isEmpty(m.getLabel()) ? "UNKNOWN" : m.getLabel();
            result.setLabel(tempLabel);
            return result;
        });
    }

    private boolean isValid(MovieDTO movieFile)
    {
        return Optional.ofNullable(movieFile)
                .filter(m -> !StringUtil.isBlank(m.getCode()))
                .filter(m -> !StringUtil.isBlank(m.getPath()))
                .filter(m -> FileUtil.existsFile(Paths.get(m.getPath())))
                .isPresent();
    }

    private MovieDO move(MovieDTO movieFile)
    {
        List<PathRule> ruleList = ruleConfig.listPathRule()
                .map(r -> JSON.parseArray(r, PathRule.class))
                .orElse(new ArrayList<>());
        Optional<Path> targetPath = ruleList.stream()
                .filter(r -> Objects.equals(r.getActress(), movieFile.getActress()))
                .findAny()
                .map(r -> Paths.get(r.getPath(), movieFile.getLevel() + ""));
        Path movieFilePath = Paths.get(movieFile.getPath());
        String code = movieFile.getCode().toUpperCase();
        targetPath.map(p ->
        {
            if (!Files.exists(p))
            {
                try
                {
                    Files.createDirectories(p);
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
        MovieDO result = new MovieDO();
        result.setCode(movieFile.getCode().toUpperCase());
        result.setLevel(movieFile.getLevel());
        result.setPath(movieFile.getPath());
        String tempActress = StringUtil.isEmpty(movieFile.getActress()) ? "UNKNOWN"
                : movieFile.getActress();
        result.setActress(tempActress);
        String tempLabel = StringUtil.isEmpty(movieFile.getLabel()) ? "UNKNOWN"
                : movieFile.getLabel();
        result.setLabel(tempLabel);
        return result;
    }
}
