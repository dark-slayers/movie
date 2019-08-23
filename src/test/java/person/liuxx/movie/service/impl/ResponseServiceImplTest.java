package person.liuxx.movie.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import person.liuxx.movie.MovieApplication;
import person.liuxx.movie.service.ResponseService;
import person.liuxx.util.service.exception.SearchException;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2019年8月22日 下午5:25:37
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MovieApplication.class)
@WebAppConfiguration
public class ResponseServiceImplTest
{
    private Logger log = LoggerFactory.getLogger(ResponseServiceImplTest.class);
    @Autowired
    ResponseService service;

    /**
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2019年8月22日 下午5:25:37
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
     *          创建时间：2019年8月22日 下午5:25:37
     * @since 1.0.0
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    /**
     * {@link person.liuxx.movie.service.impl.ResponseServiceImpl#getResource(java.nio.file.Path)}
     * 的测试方法。
     */
    @Test
    public void testGetResourcePath()
    {
       
    }
    void download(){
        Path path = Paths.get("E:/dshell/in.txt");
        ResponseEntity<Resource> r = Optional.ofNullable(path)
                .flatMap(p -> service.getResource(p))
                .<SearchException> orElseThrow(() ->
                {
                    throw new SearchException("获取文件失败！");
                });
        String fileName=r.getBody().getFilename();
        log.info("file {} download over !",fileName);
    }

    /**
     * {@link person.liuxx.movie.service.impl.ResponseServiceImpl#getResource(javax.servlet.http.HttpServletResponse, java.nio.file.Path)}
     * 的测试方法。
     */
    @Test
    public void testGetResourceHttpServletResponsePath()
    {
       
    }
}
