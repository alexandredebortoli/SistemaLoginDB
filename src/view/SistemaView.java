package view;


import java.util.Scanner;

public class SistemaView {
    public static int recuperarSenha() {
        Scanner scan = new Scanner(System.in);
        System.out.println("- Recuperar Senha -");
        System.out.println("1. Usuário\n2. Administrador");
        System.out.print("Escolha: ");
        return Integer.parseInt(scan.nextLine());
    }
    
    public static int menuLogin() {        
        Scanner scan = new Scanner(System.in);
        System.out.println("\n- Login -");
        System.out.println("1. Login Usuario\n2. Login Administrador\n3. Cadastrar-se\n4. Recuperar conta\n0. Fechar o programa");
        System.out.print("Escolha: ");
        return Integer.parseInt(scan.nextLine());
    }
    
    public static String atualizarNome() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Novo nome: ");    
        return scan.nextLine();
    }
    public static String atualizarEmail() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Novo email: ");    
        return scan.nextLine();
    }
    public static String atualizarSenha() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Nova senha: ");    
        return scan.nextLine();
    }
    public static String atualizarDataNascimento() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Nova data de nascimento(dd/mm/aaaa): ");    
        return scan.nextLine();
    }
    public static String atualizarCargo() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Novo cargo: ");    
        return scan.nextLine();
    }
    
    
    public static String cadastrarNome() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Nome: ");
        return scan.nextLine();
    }
    public static String cadastrarEmail() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Email: ");
        return scan.nextLine();
    }
    public static String cadastrarSenha() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Senha: ");
        return scan.nextLine();
    }
    public static String cadastrarDataNascimento() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Data de Nascimento (dd/mm/aaaa): ");
        return scan.nextLine();
    }
    public static String cadastrarCargo() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Cargo: ");
        return scan.nextLine();
    }
    
    public static int pegarId() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Informe o ID: ");
        return Integer.parseInt(scan.nextLine());
    }
    
    
    public static void msgFecharPrograma() {
        System.out.println("\nFechando o programa...");
    }
    public static void msgDeslogar() {
        System.out.println("\nDeslogado com sucesso!");
    }
    public static void msgOpcaoInvalida() {
        System.out.println("\nOpção Inválida!");
    }
    public static void msgCadastroSucesso(int id) {
        System.out.println("Cadastrado com sucesso!");
        System.out.println("Seu ID é " + id);
    }
    public static void msgConexaoSucesso() {
        System.out.println("Conexão realizada com sucesso!");
    }
    public static void msgEmailEnviado() {
        System.out.println("Um email com sua nova senha de recuperação foi enviado para você");
    }
    public static void msgLogin(boolean sucesso, String nome) {
        if(sucesso) {
            System.out.println("\nLogin feito com sucesso!");
            System.out.println("Bem vindo, " + nome);
        } else {
             System.out.println("\nSenha e/ou ID incorretos.\n");
        }
    }
}
