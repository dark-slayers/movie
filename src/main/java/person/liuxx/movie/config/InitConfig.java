package person.liuxx.movie.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
* @author 刘湘湘 
* @since 2019年2月19日 下午2:16:45
*/
@Configuration
public class InitConfig
{
    @Bean
    public ServletRegistrationBean<StatViewServlet> servletRegistrationBean()
    {
        // Druid数据源配置
        return new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(), "/druid/*");
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean()
    {
        FilterRegistrationBean<WebStatFilter> registrationBean = new FilterRegistrationBean<>();
        // Druid数据源配置
        registrationBean.setFilter(new WebStatFilter());
        return registrationBean;
    }
}