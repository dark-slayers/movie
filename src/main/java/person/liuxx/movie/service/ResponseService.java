package person.liuxx.movie.service;

import java.nio.file.Path;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import person.liuxx.util.service.reponse.EmptySuccedResponse;

/**
 * @author 刘湘湘
 * @since 2019年2月19日 下午3:31:59
 */
public interface ResponseService
{
    Optional<ResponseEntity<Resource>> getResource(Path path);

    Optional<EmptySuccedResponse> getResource(HttpServletResponse response, Path path);
}
