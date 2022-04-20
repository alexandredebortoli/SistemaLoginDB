package view;

import dao.AdministradorDAO;
import dao.UsuarioDAO;
import java.util.Random;
import model.Usuario;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Administrador;

public class SistemaView {
    public static int acharId(int perfil, String recipiente) {
        if(perfil == 1) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            for(int i = 0; i < usuarioDAO.listar(0).size(); i++) {
                if(recipiente.equals(usuarioDAO.listar(0).get(i).getEmail())) {
                    return usuarioDAO.listar(0).get(i).getId();
                }
            }
        } else {
            AdministradorDAO administradorDAO = new AdministradorDAO();
            for(int i = 0; i < administradorDAO.listar(0).size(); i++) {
                if(recipiente.equals(administradorDAO.listar(0).get(i).getEmail())) {
                    return administradorDAO.listar(0).get(i).getId();
                }
            }
        }
        return 0;
    }
    public static void recuperarSenha() throws Exception {
        Scanner scan = new Scanner(System.in);
        int perfil;
        String emailUser;
        System.out.println("- Recuperar Conta -");
        System.out.println("1. Usuário\n2. Administrador");
        System.out.print("Escolha: ");
        perfil = Integer.parseInt(scan.nextLine());
        if(perfil == 1 || perfil == 2) {
            System.out.print("Email cadastrado: ");
            emailUser = scan.nextLine();
            int id = acharId(perfil, emailUser);
            if(id != 0) {
                Random random = new Random();
                int randSenha = random.nextInt(9000)+1000; //Gerar senha entre 1000-9999
                String senha = Integer.toString(randSenha);
                if(perfil == 1) {
                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    usuarioDAO.atualizarSenhaUsuario(id, senha);
                } else {
                    AdministradorDAO administradorDAO = new AdministradorDAO();
                    administradorDAO.atualiarSenhaAdministrador(id, senha);
                }
                dao.JavaMailDAO.enviarEmail(emailUser, perfil, id, senha);
                System.out.println("Um email com seu id e nova senha de recuperação foi enviado para você");
            } else {
                System.out.println("Email não cadastrado.");
            }
        } else {
            System.out.println("\nOpção Inválida!\n");
        }
        
    }
    public static void menu() {        
        Scanner scan = new Scanner(System.in);
        int opcao;
        do {
            System.out.println("\n- Login -");
            System.out.println("1. Login Usuario\n2. Login Administrador\n3. Cadastrar-se\n4. Recuperar conta\n0. Fechar o programa");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(scan.nextLine());
            switch (opcao) {
                case 0 -> System.out.println("\nFechando o programa...");
                case 1 -> {
                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    Usuario usuario = UsuarioView.loginUsuario(usuarioDAO);
                    if(usuario != null) {
                        System.out.println("\nLogin feito com sucesso!");
                        System.out.println("Bem vindo, " + usuario.getNome());
                        UsuarioView.menuUsuario(usuarioDAO, usuario);
                    } else {
                        System.out.println("\nSenha e/ou ID incorretos.\n");
                    }
                }
                case 2 -> {
                    AdministradorDAO administradorDAO = new AdministradorDAO();
                    Administrador admin = AdministradorView.loginAdministrador(administradorDAO);
                    if(admin != null) {
                        System.out.println("\nLogin feito com sucesso!");
                        System.out.println("Bem vindo, " + admin.getNome());
                        AdministradorView.menuAdministrador(administradorDAO, admin);
                    } else {
                        System.out.println("\nSenha e/ou ID incorretos.\n");
                    }
                }
                case 3 -> {
                    UsuarioView.cadastrarUsuario();
                }
                case 4 -> {
                try {
                    recuperarSenha();
                } catch (Exception ex) {
                    Logger.getLogger(SistemaView.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                default -> throw new AssertionError();
            }
        } while (opcao != 0);      
    }
}
