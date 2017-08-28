package person.liuxx.movie.test;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月28日 下午4:57:51
 * @since 1.0.0
 */
public class Run
{
    /**
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2017年8月28日 下午4:57:51
     * @since 1.0.0
     * @param args
     */
    public static void main(String[] args)
    {
        System.out.println(Optional.empty() == Optional.empty());
        System.out.println(Objects.equals(Optional.empty(), Optional.empty()));
    }
}
