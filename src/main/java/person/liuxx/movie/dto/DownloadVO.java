package person.liuxx.movie.dto;

import java.util.List;

import lombok.Data;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2019年10月29日 上午10:25:15
 * @since 1.0.0
 */
@Data
public class DownloadVO
{
    private String path;
    private List<String> dirList;
    private List<String> fileList;
}
