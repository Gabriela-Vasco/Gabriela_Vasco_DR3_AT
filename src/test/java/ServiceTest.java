import at.java.dto.UsuarioDTOInput;
import at.java.service.UsuarioService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ServiceTest {
    @Test
    public void testeInsercao() {
        UsuarioService usuarioService = new UsuarioService();
        UsuarioDTOInput usuarioDTOInput = new UsuarioDTOInput();
        usuarioDTOInput.setNome("Gabriela");

        usuarioService.inserirUsuario(usuarioDTOInput);

        assertEquals(1,usuarioService.listarUsuarios().size());
    }
}
