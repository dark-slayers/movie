package person.liuxx.movie.proxy;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月29日 下午2:21:54
 * @since 1.0.0
 */
public class ProxyAddress
{
    private final String host;
    private final int port;

    public ProxyAddress(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public String getHost()
    {
        return host;
    }

    public int getPort()
    {
        return port;
    }

    @Override
    public String toString()
    {
        return "ProxyAddress [host=" + host + ", port=" + port + "]";
    }
}
