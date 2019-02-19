package person.liuxx.movie.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.alibaba.fastjson.JSON;

import person.liuxx.movie.business.path.PathRule;
import person.liuxx.util.service.exception.SearchException;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月30日 上午10:57:37
 * @since 1.0.0
 */
@Configuration
@ComponentScan
public class ElConfig
{
    private Logger log = LoggerFactory.getLogger(ElConfig.class);
    @Value("classpath:path_rule.json")
    private Resource pathRules;

    public List<PathRule> listPathRule()
    {
        List<PathRule> result = getFile().map(f -> f.toPath())
                .map(p -> readText(p))
                .map(t -> JSON.parseArray(t, PathRule.class))
                .orElse(new ArrayList<>());
        log.debug("解析结果：{}", result);
        return result;
    }

    private Optional<File> getFile()
    {
        try
        {
            if (Objects.isNull(pathRules) || Objects.isNull(pathRules.getFile()))
            {
                return Optional.empty();
            }
            return Optional.of(pathRules.getFile());
        } catch (IOException e)
        {
            throw new SearchException("读取配置文件失败！", e);
        }
    }

    private String readText(Path p)
    {
        try
        {
            return Files.lines(p).collect(Collectors.joining());
        } catch (IOException e)
        {
            throw new SearchException("读取配置文件失败！", e);
        }
    }
}
