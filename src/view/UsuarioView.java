package view;

import controller.UsuarioController;
import dao.UsuarioDAO;
import java.util.Scanner;
import model.Usuario;


public class UsuarioView {
    public static void exibirUsuario(Usuario usuario) {
        System.out.print("Id: " + usuario.getId());
        System.out.print(", Usuário: " + usuario.getNome());
        System.out.print(", Email: " + usuario.getEmail());
        System.out.print(", Data de Nascimento: " + usuario.getDataNascimento());
        System.out.print(", Cargo: " + usuario.getCargo());
        if(usuario.getStatus() == 1) {
            System.out.println(", Status: ativo");
        } else if(usuario.getStatus() == 2) {
            System.out.println(", Status: inativo");
        }
    }
    public static int atualizarUsuario() {
        Scanner scan = new Scanner(System.in);
        System.out.println("- Atualizar Usuário -");
        System.out.println("1. Nome\n2. Email\n3. Senha\n4. Data de Nascimento\n5. Cargo\n6. Tudo");
        System.out.print("Alterar: ");
        return Integer.parseInt(scan.nextLine());
    }
    
    public static Usuario loginUsuario(Usuario usuario) {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n- Login Usuário -");
        System.out.print("Id: ");
        usuario.setId(Integer.parseInt(scan.nextLine()));
        System.out.print("Senha: ");
        usuario.setSenha(scan.nextLine());
        return usuario;
    }
    public static int menuUsuario() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n- Menu Usuário -");           
        System.out.println("0. Deslogar\n1. Visualizar informações\n2. Alterar informações");
        System.out.print("Escolha: ");
        return Integer.parseInt(scan.nextLine());
        
    }
    
    public static void cadastrarUsuario() {
        System.out.println("- Cadastrar Usuário -");
    }
}
