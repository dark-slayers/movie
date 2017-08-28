package person.liuxx.movie.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
