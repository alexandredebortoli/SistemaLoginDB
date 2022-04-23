package controller;

import dao.AdministradorDAO;
import model.Administrador;
import dao.JavaMailDAO;
import dao.UsuarioDAO;
import model.Usuario;
import view.SistemaView;
import view.AdministradorView;
import view.UsuarioView;

public class AdministradorController {
    public static void loginAdministrador() {
        AdministradorDAO adminDAO = new AdministradorDAO();
        Administrador admin = new Administrador();
        admin = AdministradorView.loginAdministrador(admin);
        admin.setSenha(SistemaController.encriptografar(admin.getSenha()));
        
        if(admin.getSenha().equals(adminDAO.listar(admin.getId()).get(0).getSenha())) {
            admin = adminDAO.listar(admin.getId()).get(0);
            SistemaView.msgLogin(true, admin.getNome());
            menuAdministrador(admin, adminDAO);
        } else {
            SistemaView.msgLogin(false, "");
        }
        
    }
    public static void menuAdministrador(Administrador admin, AdministradorDAO adminDAO) {
        int opcao = 0;
        do {
            opcao = AdministradorView.menuAdministrador();
            switch (opcao) {
                case 0 -> SistemaView.msgDeslogar();
                case 1 -> {
                    AdministradorView.exibirAdministrador(admin);
                }
                case 2 -> {
                    int escolha = AdministradorView.atualizarAdminOuUser();
                    if(escolha == 1) {
                        Usuario usuario = new Usuario();
                        UsuarioDAO usuarioDAO = new UsuarioDAO();
                        usuario = usuarioDAO.listar(SistemaView.pegarId()).get(0);
                        usuario = UsuarioController.atualizarUsuario(usuario, usuarioDAO);
                    } else if(escolha == 2) {
                        admin = atualizarAdministrador(admin, adminDAO);
                    } else {
                        SistemaView.msgOpcaoInvalida();
                    }
                }
                case 3 -> {
                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    System.out.println("Usuários:");
                    for(Usuario u : usuarioDAO.listar(0)) {
                        UsuarioView.exibirUsuario(u);
                    }
                    System.out.println("Administradores:");                    
                    for(Administrador a : adminDAO.listar(0)) {          
                        AdministradorView.exibirAdministrador(a);
                    }
                }
                case 4 -> {
                    cadastrarAdministrador();
                }
                default -> SistemaView.msgOpcaoInvalida();
            }
        } while (opcao != 0);
    }
    
    public static void cadastrarAdministrador() {
        AdministradorDAO adminDAO = new AdministradorDAO();
        Administrador admin = new Administrador();
        AdministradorView.cadastrarAdministrador();
        admin.setNome(SistemaView.cadastrarNome());
        admin.setEmail(SistemaView.cadastrarEmail());
        admin.setSenha(SistemaController.encriptografar(SistemaView.cadastrarSenha()));
        admin.setDataNascimento(SistemaView.cadastrarDataNascimento());
        admin.setCargo(SistemaView.cadastrarCargo());
        
        adminDAO.adicionar(admin);
        SistemaView.msgCadastroSucesso(adminDAO.listar(0).get(adminDAO.listar(0).size()).getId());
    }
    
    public static void recuperarSenhaAdministrador() {
        AdministradorDAO adminDAO = new AdministradorDAO();
        Administrador admin = new Administrador();
        admin.setId(SistemaView.pegarId());
        try {
            admin = adminDAO.listar(admin.getId()).get(0);
            admin.setSenha(SistemaController.gerarNovaSenha());
            JavaMailDAO.enviarEmail(admin.getEmail(), admin.getSenha());
            admin.setSenha(SistemaController.encriptografar(admin.getSenha()));
            adminDAO.atualizar(admin, 3); // opcao para atualizar a senha
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Administrador atualizarAdministrador(Administrador admin, AdministradorDAO adminDAO) {
        int escolha = AdministradorView.atualizarAdministrador();
        switch (escolha) {
            case 1:
                admin.setNome(SistemaView.atualizarNome());
                adminDAO.atualizar(admin, escolha);
                break;
            case 2:
                admin.setEmail(SistemaView.atualizarEmail());
                adminDAO.atualizar(admin, escolha);
                break;
            case 3:
                admin.setSenha(SistemaController.encriptografar(SistemaView.atualizarSenha()));
                adminDAO.atualizar(admin, escolha);
                break;
            case 4:
                admin.setDataNascimento(SistemaView.atualizarDataNascimento());
                adminDAO.atualizar(admin, escolha);
                break;
            case 5:
                admin.setCargo(SistemaView.atualizarCargo());
                adminDAO.atualizar(admin, escolha);
                break;
            case 6:
                admin.setNome(SistemaView.atualizarNome());
                admin.setEmail(SistemaView.atualizarEmail());
                admin.setSenha(SistemaController.encriptografar(SistemaView.atualizarSenha()));
                admin.setDataNascimento(SistemaView.atualizarDataNascimento());
                admin.setCargo(SistemaView.atualizarCargo());
                adminDAO.atualizar(admin, escolha);
                break;
            default:
                SistemaView.msgOpcaoInvalida();
                break;
        }     
        return admin;
    }
}
