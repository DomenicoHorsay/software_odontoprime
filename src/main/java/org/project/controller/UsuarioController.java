package org.project.controller;



import org.project.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private static final List<Usuario> usuarios = new ArrayList<>();

    public static boolean cadastrarUsuario(String usuario, String senha) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(usuario)) {
                return false; // Usuário já existe
            }
        }
        usuarios.add(new Usuario(usuario, senha, usuario)); // Adiciona o novo usuário
        return true;
    }

    public static Usuario autenticarUsuario(String usuario, String senha) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(usuario) && u.getSenha().equals(senha)) {
                return u; // Retorna o usuário autenticado
            }
        }
        return null; // Credenciais inválidas
    }
}
