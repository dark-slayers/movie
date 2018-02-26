package person.liuxx.movie.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2018年1月26日 下午2:53:01
 * @since 1.0.0
 */
public class RunService
{
    private Logger log = LoggerFactory.getLogger(RunService.class);

    public void run()
    {
        log.debug("RunService:{}", "run");
    }

    /**
     * @author 刘湘湘
     * @version 1.0.0<br>
     *          创建时间：2018年1月26日 下午2:53:01
     * @since 1.0.0
     * @param args
     */
    public static void main(String[] args)
    {
        new RunService().run();
    }
}
