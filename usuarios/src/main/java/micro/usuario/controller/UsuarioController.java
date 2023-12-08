package micro.usuario.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import micro.usuario.entity.Usuario;
import micro.usuario.service.UsuarioService;

@RestController
@RequestMapping("user")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired 
    private ApplicationContext context;

    @GetMapping("/crash")
    public void crash(){
        ((ConfigurableApplicationContext)context).close();
    }

    @GetMapping("users")
    public ResponseEntity<List<Usuario>> list() {
        return usuarioService.findUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        return usuarioService.getById(id);
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@Valid @RequestBody Usuario user, BindingResult result) {
        if (result.hasErrors()) {
            return getErrores(result);
        }
        return usuarioService.save(user);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Usuario user, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return getErrores(result);
        }
        return usuarioService.update(id, user);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return usuarioService.delete(id);
    }

    private ResponseEntity<?> getErrores(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

    @GetMapping("/users-curso")
    public ResponseEntity<?> getUsersByCourse(@RequestParam List<Long> ids){
        return usuarioService.listIds(ids);
    }
}
