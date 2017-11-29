package person.liuxx.movie.config;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.alibaba.fastjson.JSON;

import person.liuxx.movie.business.path.PathRule;
import person.liuxx.util.log.LogUtil;

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
        List<PathRule> result = new ArrayList<>();
        try
        {
            String text = Files.lines(pathRules.getFile().toPath()).collect(Collectors.joining());
            result = JSON.parseArray(text, PathRule.class);
            result = Objects.isNull(result) ? new ArrayList<>() : result;
        } catch (IOException e)
        {
            log.error("从配置文件path_rule.json中获取信息失败！");
            log.error(LogUtil.errorInfo(e));
        }
        log.debug("解析结果：{}", result);
        return result;
    }
}
