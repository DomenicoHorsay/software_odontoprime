package org.project.util;


import org.project.model.Usuario;

public class Session {
    private static Usuario usuarioLogado;

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static void encerrarSessao() {
        usuarioLogado = null;
    }

    public static boolean isAutenticado() {
        return usuarioLogado != null;
    }
}
