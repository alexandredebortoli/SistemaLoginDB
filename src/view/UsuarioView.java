package view;

import dao.UsuarioDAO;
import java.util.Scanner;
import model.Usuario;


public class UsuarioView {
    public static void exibir(UsuarioDAO usuarioDAO, int id) {
        for(Usuario u : usuarioDAO.listar(id)) {
            System.out.print("Id: " + u.getId());
            System.out.print(", Usuário: " + u.getNome());
            System.out.print(", Email: " + u.getEmail());
            System.out.print(", Data de Nascimento: " + u.getDataNascimento());
            System.out.print(", Cargo: " + u.getCargo());
            if(u.getStatus() == 1) {
                System.out.println(", Status: ativo");
            } else if(u.getStatus() == 2) {
                System.out.println(", Status: inativo");
            }
        }
    }
    public static Usuario loginUsuario(UsuarioDAO usuarioDAO) {
        int id;
        String senha;
        Scanner scan = new Scanner(System.in);
        System.out.println("\n- Login Usuário -");
        System.out.print("Id: "); 
        id = Integer.parseInt(scan.nextLine());
        System.out.print("Senha: ");
        senha = criptografia.Criptografar.encriptografar(scan.nextLine());
        
        return usuarioDAO.verificarLogin(id, senha);
        
    }
    public static void menuUsuario(UsuarioDAO usuarioDAO, Usuario usuario) {
        int opcao;
        Scanner scan = new Scanner(System.in);
        do {
            opcao = 0;
            System.out.println("\n- Menu Usuário -");           
            System.out.println("0. Deslogar\n1. Visualizar informações\n2. Alterar informações");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(scan.nextLine());

            switch (opcao) {
                case 0 -> System.out.println("\nDeslogado com sucesso!");
                case 1 -> {
                    System.out.println("- Informações do usuário");
                    exibir(usuarioDAO, usuario.getId());
                }
                case 2 -> {
                    System.out.println("- Atualizar usuário");
                    exibir(usuarioDAO, usuario.getId());
                    usuarioDAO.atualizar(usuario.getId());
                }
                default -> System.out.println("Opcão inválida!\n");
            }
        } while(opcao != 0);
        
    }
    public static void cadastrarUsuario() {
        Scanner scan = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        
        System.out.println("- Cadastrar Usuário -");
        Usuario usuario = new Usuario();
        System.out.print("Nome: ");
        usuario.setNome(scan.nextLine());
        System.out.print("Email: ");
        usuario.setEmail(scan.nextLine());
        System.out.print("Senha: ");
        usuario.setSenha(criptografia.Criptografar.encriptografar(scan.nextLine()));
        System.out.print("Data de Nascimento (dd/mm/aaaa): ");
        usuario.setDataNascimento(scan.nextLine());
        System.out.print("Cargo: ");
        usuario.setCargo(scan.nextLine());
        usuarioDAO.adicionar(usuario);
        System.out.println("Cadastrado com sucesso!");
        System.out.println("Seu ID é " + SistemaView.acharId(1, usuario.getEmail()));
    }
}
