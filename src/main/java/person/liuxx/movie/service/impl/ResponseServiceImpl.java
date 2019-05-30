package person.liuxx.movie.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import person.liuxx.movie.service.ResponseService;
import person.liuxx.util.file.FileName;
import person.liuxx.util.file.FileUtil;
import person.liuxx.util.service.exception.SearchException;
import person.liuxx.util.service.reponse.EmptySuccedResponse;

/**
 * @author 刘湘湘
 * @since 2019年2月19日 下午3:33:40
 */
@Service
public class ResponseServiceImpl implements ResponseService
{
    private static final int BUFFER_LENGTH = 1024 * 8;

    @Override
    public Optional<ResponseEntity<Resource>> getResource(Path path)
    {
        FileName fileName = FileUtil.getFileName(path).orElse(new FileName("ERROR", "TXT"));
        return Optional.ofNullable(path)
                .map(p -> p.toUri())
                .map(u -> uriToResource(u))
                .filter(r -> r.exists() || r.isReadable())
                .map(r -> resourceToEntity(r, fileName));
    }

    private Resource uriToResource(URI uri)
    {
        try
        {
            return new UrlResource(uri);
        } catch (MalformedURLException e)
        {
            throw new SearchException("获取资源文件失败!", e);
        }
    }

    private ResponseEntity<Resource> resourceToEntity(Resource r, FileName fileName)
    {
        String contentDisposition = "attachment; filename=\"" + fileName;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, reCode(contentDisposition))
                .body(r);
    }

    private String reCode(String contentDisposition)
    {
        try
        {
            return new String(contentDisposition.getBytes("UTF-8"), "ISO8859-1");
        } catch (UnsupportedEncodingException e)
        {
            throw new SearchException("获取资源文件失败!", e);
        }
    }

    @Override
    public Optional<EmptySuccedResponse> getResource(HttpServletResponse response, Path path)
    {
        try (InputStream fis = Files.newInputStream(path);
                OutputStream os = response.getOutputStream();)
        {
            int count = 0;
            byte[] buffer = new byte[BUFFER_LENGTH];
            while ((count = fis.read(buffer)) != -1)
            {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (IOException e)
        {
            throw new SearchException("获取资源文件失败!", e);
        }
        return Optional.of(new EmptySuccedResponse());
    }
}
