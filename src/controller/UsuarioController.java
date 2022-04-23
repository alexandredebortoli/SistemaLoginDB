package controller;

import dao.JavaMailDAO;
import model.Usuario;
import dao.UsuarioDAO;
import view.SistemaView;
import view.UsuarioView;

public class UsuarioController {
    
    public static void loginUsuario() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario();
        usuario = UsuarioView.loginUsuario(usuario);
        usuario.setSenha(SistemaController.encriptografar(usuario.getSenha()));
        
        if(usuario.getSenha().equals(usuarioDAO.listar(usuario.getId()).get(0).getSenha())) {
            usuario = usuarioDAO.listar(usuario.getId()).get(0);
            SistemaView.msgLogin(true, usuario.getNome());
            menuUsuario(usuario, usuarioDAO);
        } else {
            SistemaView.msgLogin(false, "");
        }
    }
    public static void menuUsuario(Usuario usuario, UsuarioDAO usuarioDAO) {
        int opcao = 0;
        do {
            opcao = UsuarioView.menuUsuario();
            switch (opcao) {
                case 0 -> SistemaView.msgDeslogar();
                case 1 -> {
                    UsuarioView.exibirUsuario(usuario);
                }
                case 2 -> {
                    usuario = atualizarUsuario(usuario, usuarioDAO);
                }
                default -> SistemaView.msgOpcaoInvalida();
            }
        } while (opcao != 0);
    }
    
    public static void cadastrarUsuario() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario();
        UsuarioView.cadastrarUsuario();
        usuario.setNome(SistemaView.cadastrarNome());
        usuario.setEmail(SistemaView.cadastrarEmail());
        usuario.setSenha(SistemaController.encriptografar(SistemaView.cadastrarSenha()));
        usuario.setDataNascimento(SistemaView.cadastrarDataNascimento());
        usuario.setCargo(SistemaView.cadastrarCargo());
        
        usuarioDAO.adicionar(usuario);
        SistemaView.msgCadastroSucesso(usuarioDAO.listar(0).get(usuarioDAO.listar(0).size()).getId());
    }
    
    public static void recuperarSenhaUsuario() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario();
        usuario.setId(SistemaView.pegarId());
        try {
            usuario = usuarioDAO.listar(usuario.getId()).get(0);
            usuario.setSenha(SistemaController.gerarNovaSenha());
            JavaMailDAO.enviarEmail(usuario.getEmail(), usuario.getSenha());
            usuario.setSenha(SistemaController.encriptografar(usuario.getSenha()));
            usuarioDAO.atualizar(usuario, 3); // opcao para atualizar a senha
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Usuario atualizarUsuario(Usuario usuario, UsuarioDAO usuarioDAO) {
        int escolha = UsuarioView.atualizarUsuario();
        switch (escolha) {
            case 1:
                usuario.setNome(SistemaView.atualizarNome());
                usuarioDAO.atualizar(usuario, escolha);
                break;
            case 2:
                usuario.setEmail(SistemaView.atualizarEmail());
                usuarioDAO.atualizar(usuario, escolha);
                break;
            case 3:
                usuario.setSenha(SistemaController.encriptografar(SistemaView.atualizarSenha()));
                usuarioDAO.atualizar(usuario, escolha);
                break;
            case 4:
                usuario.setDataNascimento(SistemaView.atualizarDataNascimento());
                usuarioDAO.atualizar(usuario, escolha);
                break;
            case 5:
                usuario.setCargo(SistemaView.atualizarCargo());
                usuarioDAO.atualizar(usuario, escolha);
                break;
            case 6:
                usuario.setNome(SistemaView.atualizarNome());
                usuario.setEmail(SistemaView.atualizarEmail());
                usuario.setSenha(SistemaController.encriptografar(SistemaView.atualizarSenha()));
                usuario.setDataNascimento(SistemaView.atualizarDataNascimento());
                usuario.setCargo(SistemaView.atualizarCargo());
                usuarioDAO.atualizar(usuario, escolha);
                break;
            default:
                SistemaView.msgOpcaoInvalida();
                break;
        }
        return usuario;
    }
}
