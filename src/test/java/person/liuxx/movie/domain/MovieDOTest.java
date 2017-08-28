package person.liuxx.movie.domain;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.base.Objects;

import person.liuxx.movie.dto.MovieDTO;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2017年8月28日 下午4:44:43
 * @since 1.0.0
 */
public class MovieDOTest
{
    /**
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2017年8月28日 下午4:44:43
     * @since 1.0.0
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    /**
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2017年8月28日 下午4:44:43
     * @since 1.0.0
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    /**
     * {@link person.liuxx.movie.domain.MovieDO#createOptional(person.liuxx.movie.dto.MovieDTO)}
     * 的测试方法。
     */
    @Test
    public void testCreateOptional()
    {
        // 参数对象MovieDTO为null，返回Optional.empty()
        Optional<MovieDO> optional = MovieDO.createOptional(null);
        boolean assertResult = Objects.equal(Optional.empty(), optional);
        assertTrue(assertResult);
        // 参数对象不为null，但是code字段和path字段都为null，返回Optional.empty()
        MovieDTO m = new MovieDTO();
        optional = MovieDO.createOptional(m);
        assertResult = Objects.equal(Optional.empty(), optional);
        assertTrue(assertResult);
        // 参数对象不为null，code字段不为null,但是path字段为null，返回Optional.empty()
        m.setCode("A-001");
        optional = MovieDO.createOptional(m);
        assertResult = Objects.equal(Optional.empty(), optional);
        assertTrue(assertResult);
        // 参数对象不为null，code字段和path字段都不为null,但是path路径无效，返回Optional.empty()
        m.setPath("K:/temp");
        optional = MovieDO.createOptional(m);
        assertResult = Objects.equal(Optional.empty(), optional);
        assertTrue(assertResult);
        // 参数对象不为null，code字段和path字段都不为null,path路径有效，返回有值的Optional
        m.setPath("D:/temp");
        optional = MovieDO.createOptional(m);
        assertResult = Objects.equal(Optional.empty(), optional);
        assertFalse(assertResult);
    }
}
