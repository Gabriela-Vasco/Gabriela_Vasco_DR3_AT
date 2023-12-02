package at.java.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import at.java.dto.UsuarioDTOInput;
import at.java.service.UsuarioService;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.put;

public class UsuarioController {
    private final UsuarioService usuarioService = new UsuarioService();
    private final ObjectMapper objMapper = new ObjectMapper();

    public void respostasRequisicoes(){
        get("/usuarios", (request, response) -> {
            response.type("application/json");
            response.status(200);
            String json = objMapper.writeValueAsString(usuarioService.listarUsuarios());
            return json;
        });

        get("/usuarios/:id", (request, response) -> {
            response.type("application/json");
            String idParam = request.params("id");
            int id = Integer.parseInt(idParam);
            String json = objMapper.writeValueAsString(usuarioService.buscarUsuario(id));
            response.status(200);
            return json;
        });

        delete("/usuarios/:id", (request, response) -> {
            response.type("application/json");
            String idParam = request.params("id");
            int id = Integer.parseInt(idParam);
            usuarioService.excluirUsuario(id);
            response.status(200);
            return "Usuario excluido com sucesso.";
        });

        put("/usuarios", (request, response) -> {
            UsuarioDTOInput usuarioDTOInput = objMapper.readValue(request.body(), UsuarioDTOInput.class);
            usuarioService.alterarUsuario(usuarioDTOInput);
            response.type("application/json");
            response.status(200);
            return "Usuario alterado com sucesso.";
        });

        post("/usuario", (request, response) -> {
            UsuarioDTOInput usuarioDTOInput = objMapper.readValue(request.body(), UsuarioDTOInput.class);
            usuarioService.inserirUsuario(usuarioDTOInput);
            response.type("application/json");
            response.status(201);
            return "Usuario inserido com sucesso.";
        });
    }
}
