package person.liuxx.movie.domain;

import java.nio.file.Paths;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import person.liuxx.movie.dto.MovieDTO;
import person.liuxx.util.base.StringUtil;
import person.liuxx.util.file.FileUtil;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月25日 下午4:09:15
 * @since 1.0.0
 */
@Entity
@Table(name = "movie")
public class MovieDO
{
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false, length = 50)
    private String code;
    private String path;
    private int level;
    private String actress;
    private String uniform;

    /**
     * 使用MovieDTO对象的相关信息，创建一个没有id信息的MovieDO对象<br>
     * 创建之前会对数据有效性进行检查，检查未通过则返回Optional.empty()
     * 
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2017年8月28日 下午4:17:31
     * @since 1.0.0
     * @param movieDTO
     * @return
     */
    public static Optional<MovieDO> createOptional(MovieDTO movieDTO)
    {
        Optional<MovieDO> o = Optional.ofNullable(movieDTO).map(m ->
        {
            if (StringUtil.isAnyEmpty(m.getCode(), m.getPath()))
            {
                return null;
            }
            if (!FileUtil.existsDir(Paths.get(m.getPath())))
            {
                return null;
            }
            MovieDO result = new MovieDO();
            result.setCode(m.getCode());
            result.setLevel(m.getLevel());
            result.setPath(m.getPath());
            String tempActress = StringUtil.isEmpty(m.getActress()) ? "UNKNOWN" : m.getActress();
            result.setActress(tempActress);
            String tempUniform = StringUtil.isEmpty(m.getUniform()) ? "UNKNOWN" : m.getUniform();
            result.setUniform(tempUniform);
            return result;
        });
        return o;
    }

    public MovieDO()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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

    public String getUniform()
    {
        return uniform;
    }

    public void setUniform(String uniform)
    {
        this.uniform = uniform;
    }

    @Override
    public String toString()
    {
        return "MovDO [id=" + id + ", code=" + code + ", path=" + path + ", level=" + level
                + ", actress=" + actress + ", uniform=" + uniform + "]";
    }
}
