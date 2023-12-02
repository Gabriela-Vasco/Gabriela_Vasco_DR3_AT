import at.java.dto.UsuarioDTOInput;
import at.java.model.Usuario;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class EndpointTest {
    @Test
    public void testeListagem() throws IOException {
        URL url = new URL("http://127.0.0.1:4567/usuarios");
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        int responseCode = conexao.getResponseCode();
        assertEquals(200,responseCode);
    }

    @Test
    public void testeInsercao() throws IOException {
        URL url = new URL("https://randomuser.me/api/");
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");

        int responseCode = conexao.getResponseCode();
        UsuarioDTOInput usuarioDTOInput = null;
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = null;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                jsonResponse = response.toString();

                JsonNode root = objectMapper.readTree(jsonResponse);
                JsonNode resultsNode = root.get("results").get(0);

                int id = resultsNode.get("dob").get("age").asInt();
                String nome = resultsNode.get("name").get("first").asText();
                String senha = resultsNode.get("login").get("password").asText();

                usuarioDTOInput = new UsuarioDTOInput();
                usuarioDTOInput.setId(id);
                usuarioDTOInput.setNome(nome);
                usuarioDTOInput.setSenha(senha);
            }
        }

        URL url2 = new URL("http://127.0.0.1:4567/usuario");
        HttpURLConnection conexao2 = (HttpURLConnection) url2.openConnection();
        conexao2.setRequestProperty("Accept", "application/json");
        conexao2.setDoOutput(true);
        conexao2.setRequestMethod("POST");

        UsuarioDTOInput usuarioDTOInput1 = new UsuarioDTOInput(usuarioDTOInput.getId(), usuarioDTOInput.getNome(), usuarioDTOInput.getSenha());
        String json = objectMapper.writeValueAsString(usuarioDTOInput1);
        conexao2.getOutputStream().write(json.getBytes());

        BufferedReader in = new BufferedReader(new InputStreamReader(conexao2.getInputStream()));
        StringBuffer response = new StringBuffer();
        String inputLine;

        while((inputLine = in.readLine()) != null){
            response.append(inputLine);
        } in .close();

    }
}
