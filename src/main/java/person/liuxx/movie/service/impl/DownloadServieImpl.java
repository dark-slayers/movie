package person.liuxx.movie.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import person.liuxx.movie.dto.DownloadVO;
import person.liuxx.movie.service.DownloadServie;
import person.liuxx.util.service.exception.SearchException;

/**
 * @author 刘湘湘
 * @version 1.0.0<br>
 *          创建时间：2019年10月29日 上午10:35:49
 * @since 1.0.0
 */
@Service
public class DownloadServieImpl implements DownloadServie
{
    @Override
    public Optional<DownloadVO> getDownloadVO(String path)
    {
        DownloadVO vo = new DownloadVO();
        vo.setPath(path);
        Path dir = Paths.get(path);
        try
        {
            Map<Boolean, List<Path>> map = Files.walk(dir)
                    .filter(Objects::nonNull)
                    .filter(p -> Objects.equals(dir, p.getParent()))
                    .collect(Collectors.partitioningBy(p -> Files.isDirectory(p)));
            List<String> dirList = pathToString(map, Boolean.TRUE, dir);
            vo.setDirList(dirList);
            List<String> fileList = pathToString(map, Boolean.FALSE, dir);
            vo.setFileList(fileList);
        } catch (IOException e)
        {
            throw new SearchException("获取文件列表失败!", e);
        }
        return Optional.of(vo);
    }

    private List<String> pathToString(Map<Boolean, List<Path>> map, Boolean b, Path dir)
    {
        return map.getOrDefault(b, new ArrayList<>())
                .stream()
                .map(p -> dir.relativize(p))
                .map(p -> p.toString())
                .collect(Collectors.toList());
    }
}
