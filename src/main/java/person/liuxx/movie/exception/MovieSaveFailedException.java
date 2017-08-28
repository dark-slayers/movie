package person.liuxx.movie.exception;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月28日 下午3:20:46
 * @since 1.0.0
 */
public class MovieSaveFailedException extends RuntimeException
{
    private static final long serialVersionUID = 5275081566512889977L;

    public MovieSaveFailedException(String message)
    {
        super(message);
    }

    public MovieSaveFailedException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
