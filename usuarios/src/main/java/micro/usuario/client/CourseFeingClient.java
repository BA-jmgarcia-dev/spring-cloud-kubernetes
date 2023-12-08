package micro.usuario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cursos-service")
public interface CourseFeingClient {

    @DeleteMapping("curso/delete-course-user/{id}")
    void deleteUser(@PathVariable Long id);

}
