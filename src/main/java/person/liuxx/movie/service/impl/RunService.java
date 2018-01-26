package person.liuxx.movie.service.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import person.liuxx.movie.domain.MovieDO;
import person.liuxx.movie.dto.MovieDTO;
import person.liuxx.util.log.LogUtil;

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
        MovieDO a = new MovieDO();

        String name = "code";
        a.setActress("ACTER");
        a.setCode("000212");
        a.setId(32L);
        a.setLabel("LD");
        a.setLevel(3);
        a.setPath("D:/t");
        log.debug("a", a);
        MovieDTO ac =MovieDTO.of(a);
        log.debug("MovieDO ac:{}", ac);
//        try
//        {
//            PropertyDescriptor desc = BeanUtilsBean.getInstance()
//                    .getPropertyUtils()
//                    .getPropertyDescriptor(a, name);
//            // getPropertyDescriptor(a, name);
//            log.debug("desc:{}", desc);
//            log.debug("desc.getReadMethod():{}", desc.getReadMethod());
//            Method m = MethodUtils.getAccessibleMethod(MovieDO.class, desc.getReadMethod());
//            System.out.println(m);
//            Class<?> clazz = desc.getReadMethod().getDeclaringClass();
//            log.debug("clazz:{}", clazz);
//            boolean show = BeanUtilsBean.getInstance().getPropertyUtils().isReadable(a, name);
//            boolean show2 = BeanUtilsBean.getInstance().getPropertyUtils().isWriteable(a, name);
//            System.out.println(show);
//            System.out.println(show2);
//            BeanUtils.copyProperties(ac, a);
//            log.debug("MovieDO ac:{}", ac);
//        } catch (Exception e)
//        {
//            log.debug(LogUtil.errorInfo(e));
//        }
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
