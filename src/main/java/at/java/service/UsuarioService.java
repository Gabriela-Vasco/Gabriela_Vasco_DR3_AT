package at.java.service;

import at.java.dto.UsuarioDTOInput;
import at.java.dto.UsuarioDTOOuput;
import at.java.model.Usuario;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {
    private final List<Usuario> listaUsuarios = new ArrayList<>();
    private final ModelMapper modelMapper = new ModelMapper();

    public List<UsuarioDTOOuput> listarUsuarios(){
        List<UsuarioDTOOuput> listaUsuariosDTOOutputs = modelMapper.map(listaUsuarios, List.class);
        return listaUsuariosDTOOutputs;
    }

    public void inserirUsuario(UsuarioDTOInput usuarioDTOInput){
        Usuario usuario = modelMapper.map(usuarioDTOInput, Usuario.class);
        listaUsuarios.add(usuario);
    }

    public void alterarUsuario(UsuarioDTOInput usuarioDTOInput){
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getId() == usuarioDTOInput.getId()) {
                usuario.setNome(usuarioDTOInput.getNome());
                usuario.setSenha(usuarioDTOInput.getSenha());
                break;
            }
        }
    }

    public UsuarioDTOOuput buscarUsuario(int id){
        UsuarioDTOOuput usuarioDTOOuput = null;
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getId() == id) {
                usuarioDTOOuput = modelMapper.map(usuario, UsuarioDTOOuput.class);
                break;
            }
        }
        return usuarioDTOOuput;
    }

    public void excluirUsuario(int id){
        listaUsuarios.removeIf(usuario -> usuario.getId() == id);
    }
}
