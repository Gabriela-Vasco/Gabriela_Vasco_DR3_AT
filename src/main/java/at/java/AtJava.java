package at.java;

import at.java.controller.UsuarioController;
import spark.Spark;

public class AtJava {
    public static void main(String[] args) {
        UsuarioController usuarioController = new UsuarioController();

        usuarioController.respostasRequisicoes();

        Spark.awaitInitialization();

        Spark.awaitStop();
    }
}
