package person.liuxx.movie.dto;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月25日 下午5:15:33
 * @since 1.0.0
 */
public class MovieDTO
{
    private String code;
    private String path;
    private int level;
    private String actress;
    private String uniform;

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
        return "MovieDTO [code=" + code + ", path=" + path + ", level=" + level + ", actress="
                + actress + ", uniform=" + uniform + "]";
    }
}
