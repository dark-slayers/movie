package person.liuxx.movie.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/** 
* @author  刘湘湘 
* @version 1.0.0<br>创建时间：2017年8月30日 下午4:35:16
* @since 1.0.0 
*/
@Configuration
@ComponentScan("person.liuxx.movie.task")
@EnableScheduling
public class TaskSchedulerConfig
{
}
