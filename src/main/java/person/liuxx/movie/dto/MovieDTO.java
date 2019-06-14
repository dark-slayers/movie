package person.liuxx.movie.dto;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;

import person.liuxx.movie.business.path.PathRule;
import person.liuxx.movie.entity.MovieDO;
import person.liuxx.util.base.StringUtil;
import person.liuxx.util.service.exception.DataChangeException;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月25日 下午5:15:33
 * @since 1.0.0
 */
public class MovieDTO
{
    private static final String UNKNOWN = "UNKNOWN";
    private String code;
    private String path;
    private int level;
    private String actress;
    private String label;
    private String mainPic;

    public MovieDTO()
    {
    }

    public Optional<MovieDO> mapToDO()
    {
        format();
        MovieDO result = new MovieDO();
        try
        {
            BeanUtils.copyProperties(result, this);
            return Optional.of(result);
        } catch (IllegalAccessException | InvocationTargetException e)
        {
            throw new DataChangeException("数据转化异常：", e);
        }
    }

    public static MovieDTO of(MovieDO m)
    {
        MovieDTO result = new MovieDTO();
        try
        {
            BeanUtils.copyProperties(result, m);
        } catch (IllegalAccessException | InvocationTargetException e)
        {
            throw new DataChangeException("数据转化异常：", e);
        }
        return result;
    }

    /**
     * 对信息进行格式化
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2017年11月29日 上午10:03:45
     * @since 1.0.0
     * @return
     */
    public MovieDTO format()
    {
        code = code.toUpperCase();
        level = (level < 1) ? 1 : level;
        actress = StringUtil.isEmpty(actress) ? UNKNOWN : getActress();
        label = StringUtil.isEmpty(actress) ? UNKNOWN : getLabel();
        path = Paths.get(path).toString();
        return this;
    }

    /**
     * 从指定的规则列表中筛选出目标路径，如果没有获取到路径，返回Optional.empty()
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2017年11月29日 上午10:02:25
     * @since 1.0.0
     * @param ruleList
     * @return
     */
    public Optional<Path> targetPath(List<PathRule> ruleList)
    {
        format();
        Optional<Path> result = ruleList.stream().filter(r -> isEffective(r)).findAny().map(
                r -> Paths.get(r.getPath(), String.valueOf(level), code));
        return result;
    }

    private boolean isEffective(PathRule rule)
    {
        if (Objects.equals(rule.getActress(), actress))
        {
            return true;
        }
        return Objects.equals(rule.getLabel(), label);
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public String getActress()
    {
        return actress;
    }

    public void setActress(String actress)
    {
        this.actress = actress;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getMainPic()
    {
        return mainPic;
    }

    public void setMainPic(String mainPic)
    {
        this.mainPic = mainPic;
    }

    @Override
    public String toString()
    {
        return "MovieDTO [code=" + code + ", path=" + path + ", level=" + level + ", actress="
                + actress + ", label=" + label + ", mainPic=" + mainPic + "]";
    }
}
