package controller;

import dao.JavaMailDAO;
import model.Usuario;
import dao.UsuarioDAO;
import frame.CadastrarFrame;
import frame.TableUsuarios;
import frame.UsuarioFrame;
import java.util.List;

public class UsuarioController {
    public static void usuarioFrame() {
        UsuarioFrame usuarioFrame = new UsuarioFrame();
        usuarioFrame.show(true);
    }
    public static void cadastrarFrame() {
        CadastrarFrame cadastrarFrame = new CadastrarFrame();
        cadastrarFrame.show(true);
    }
    public static Usuario verificarLogin(int id, String senha) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setSenha(SistemaController.encriptografar(senha));
        
        if(usuario.getSenha().equals(usuarioDAO.listar(usuario.getId()).get(0).getSenha())) {
            usuario = usuarioDAO.listar(usuario.getId()).get(0);
            return usuario;
        }  else {
            usuario = null;
            return usuario;
        }
    }

    public static void cadastrar(String nome, String email, String senha, String dataNascimento, String cargo) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(SistemaController.encriptografar(senha));
        usuario.setDataNascimento(dataNascimento);
        usuario.setCargo(cargo);
        usuarioDAO.adicionar(usuario);
    }

    public static void recuperarSenha(int id) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario();
        usuario.setId(id);
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
    public static void atualizar(int id, String nome, String email, String senha, String dataNascimento, String cargo) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario novoUsuario = new Usuario();
        novoUsuario = usuarioDAO.listar(id).get(0);
        if(!nome.equals("")) {
            novoUsuario.setNome(nome);
        }
        if(!email.equals("")) {
            novoUsuario.setEmail(email);
        }
        if(!senha.equals("")) {
            novoUsuario.setSenha(SistemaController.encriptografar(senha));
        }
        if(!dataNascimento.equals("dd/mm/aaaa")) {
            novoUsuario.setDataNascimento(dataNascimento);
        }
        if(!cargo.equals("")) {
            novoUsuario.setCargo(cargo);
        }
        usuarioDAO.atualizar(novoUsuario, 6);
    }
    public static void tableUsuariosFrame() {
        TableUsuarios tableUsuarios = new TableUsuarios();
        tableUsuarios.show(true);
    }
    public static List<Usuario> listaUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.listar(0);
    }
    public static Usuario infoPessoais(int id) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.listar(id).get(0);
    }
}
