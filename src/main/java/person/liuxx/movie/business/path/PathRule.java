package person.liuxx.movie.business.path;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年10月31日 下午4:13:39
 * @since 1.0.0
 */
public class PathRule
{
    private String actress;
    private String label;
    private String path;

    public PathRule()
    {
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

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    @Override
    public String toString()
    {
        return "PathRule [actress=" + actress + ", label=" + label + ", path=" + path + "]";
    }
}
