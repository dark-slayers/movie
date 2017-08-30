package person.liuxx.movie.dto;

import java.util.Arrays;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月30日 上午11:08:33
 * @since 1.0.0
 */
public class ProxyWebDTO
{
    private String url;
    private boolean single;
    private int maxDeep;
    private String tableTag;
    private String tableTagType;
    private int ipIndex;
    private int portIndex;
    private String charset;
    private String[] subUrl;

    public ProxyWebDTO()
    {
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public boolean isSingle()
    {
        return single;
    }

    public void setSingle(boolean single)
    {
        this.single = single;
    }

    public int getMaxDeep()
    {
        return maxDeep;
    }

    public void setMaxDeep(int maxDeep)
    {
        this.maxDeep = maxDeep;
    }

    public String getTableTag()
    {
        return tableTag;
    }

    public void setTableTag(String tableTag)
    {
        this.tableTag = tableTag;
    }

    public String getTableTagType()
    {
        return tableTagType;
    }

    public void setTableTagType(String tableTagType)
    {
        this.tableTagType = tableTagType;
    }

    public int getIpIndex()
    {
        return ipIndex;
    }

    public void setIpIndex(int ipIndex)
    {
        this.ipIndex = ipIndex;
    }

    public int getPortIndex()
    {
        return portIndex;
    }

    public void setPortIndex(int portIndex)
    {
        this.portIndex = portIndex;
    }

    public String[] getSubUrl()
    {
        return subUrl;
    }

    public void setSubUrl(String[] subUrl)
    {
        this.subUrl = subUrl;
    }

    public String getCharset()
    {
        return charset;
    }

    public void setCharset(String charset)
    {
        this.charset = charset;
    }

    @Override
    public String toString()
    {
        return "ProxyWebDTO [url=" + url + ", single=" + single + ", maxDeep=" + maxDeep
                + ", tableTag=" + tableTag + ", tableTagType=" + tableTagType + ", ipIndex="
                + ipIndex + ", portIndex=" + portIndex + ", charset=" + charset + ", subUrl="
                + Arrays.toString(subUrl) + "]";
    }
}
