package person.liuxx.movie.service;

import java.util.Optional;

/** 
* @author  刘湘湘 
* @version 1.0.0<br>创建时间：2018年1月10日 下午2:01:43
* @since 1.0.0 
*/
public interface UpdateVersionService
{

    /** 版本升级更新数据
    * @author  刘湘湘 
    * @version 1.0.0<br>创建时间：2018年1月10日 下午2:06:04
    * @since 1.0.0 
    * @return
    */
    Optional<String> updateVersion();
}
