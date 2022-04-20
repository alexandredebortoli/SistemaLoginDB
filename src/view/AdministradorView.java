package view;

import dao.AdministradorDAO;
import java.util.Scanner;
import model.Administrador;

public class AdministradorView {
    public static void exibir(AdministradorDAO administradorDAO, int id) {
        if(id == 0){
            dao.UsuarioDAO usuarioDAO = new dao.UsuarioDAO();
            System.out.println("- Usuários");
            UsuarioView.exibir(usuarioDAO, 0);
            System.out.println("- Administradores");
        }
        for(Administrador a : administradorDAO.listar(id)) {          
            System.out.print("Id: " + a.getId());
            System.out.print(", Usuário: " + a.getNome());
            System.out.print(", Email: " + a.getEmail());
            System.out.print(", Data de Nascimento: " + a.getDataNascimento());
            System.out.print(", Cargo: " + a.getCargo());
            if(a.getStatus() == 1) {
                System.out.println(", Status: ativo");
            } else if(a.getStatus() == 2) {
                System.out.println(", Status: inativo");
            }               
        }
    }
    public static Administrador loginAdministrador(AdministradorDAO administradorDAO) {
        int id;
        String senha;
        Scanner scan = new Scanner(System.in);
        System.out.println("\n- Login Administrador -");
        System.out.print("Id: "); 
        id = Integer.parseInt(scan.nextLine());
        System.out.print("Senha: ");
        senha= criptografia.Criptografar.encriptografar(scan.nextLine());
        
        return administradorDAO.verificarLogin(id, senha);
        
    }
    public static void menuAdministrador(AdministradorDAO administradorDAO, Administrador administrador) {
        int opcao;
        Scanner scan = new Scanner(System.in);
        do {
            opcao = 0;
            System.out.println("\n- Menu Administrador -");           
            System.out.print("0. Deslogar\n1. Visualizar informações pessoais\n2. Visualizar informações de todos");
            System.out.println("\n3. Alterar informações\n4. Cadastrar outro administrador");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(scan.nextLine());

            switch (opcao) {
                case 0 -> System.out.println("\nDeslogado com sucesso!");
                case 1 -> {
                    System.out.println("- Informações pessoais do administrador");
                    exibir(administradorDAO, administrador.getId());
                }
                case 2 -> {
                    System.out.println("- Informações de todos");
                    exibir(administradorDAO, 0);
                }
                case 3 -> {
                    int escolha, id;
                    System.out.println("- Atualizar");
                    System.out.println("1. Usuário\n2. Administrador");
                    System.out.print("Escolha: ");
                    escolha = Integer.parseInt(scan.nextLine());
                    System.out.print("Id para alteração: ");
                    id = Integer.parseInt(scan.nextLine());
                    if(escolha == 1) {
                        dao.UsuarioDAO usuarioDAO = new dao.UsuarioDAO();
                        UsuarioView.exibir(usuarioDAO, id);
                        usuarioDAO.atualizar(id);
                    } else if(escolha == 2) {
                        exibir(administradorDAO, id);
                        administradorDAO.atualizar(id);
                    } else {
                        System.out.println("\nOpção Inválida!\n");
                    }
                }
                case 4 -> {
                    cadastrarAdministrador();
                }
                default -> System.out.println("Opcão inválida!\n");
            }
        } while(opcao != 0);
        
    }
    public static void cadastrarAdministrador() {
        Scanner scan = new Scanner(System.in);
        AdministradorDAO administradorDAO = new AdministradorDAO();
        
        System.out.println("- Cadastrar Administrador -");
        Administrador administrador = new Administrador();
        System.out.print("Nome: ");
        administrador.setNome(scan.nextLine());
        System.out.print("Email: ");
        administrador.setEmail(scan.nextLine());
        System.out.print("Senha: ");
        administrador.setSenha(criptografia.Criptografar.encriptografar(scan.nextLine()));
        System.out.print("Data de Nascimento (dd/mm/aaaa): ");
        administrador.setDataNascimento(scan.nextLine());
        System.out.print("Cargo: ");
        administrador.setCargo(scan.nextLine());
        administradorDAO.adicionar(administrador);
        System.out.println("Cadastrado com sucesso!");
        System.out.println("Seu ID é " + SistemaView.acharId(2, administrador.getEmail()));
    }   
}
