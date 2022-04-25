package controller;

import dao.AdministradorDAO;
import model.Administrador;
import dao.JavaMailDAO;
import frame.AdministradorFrame;
import frame.TableAdministradores;
import java.util.List;

public class AdministradorController {   
    public static void recuperarSenha(int id) {
        AdministradorDAO adminDAO = new AdministradorDAO();
        Administrador admin = new Administrador();
        admin.setId(id);
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

    public static void administradorFrame() {
        AdministradorFrame administradorFrame = new AdministradorFrame();
        administradorFrame.show(true);
    }

    public static Administrador verificarLogin(int id, String senha) {
        AdministradorDAO adminDAO = new AdministradorDAO();
        Administrador admin = new Administrador();
        admin.setId(id);
        admin.setSenha(SistemaController.encriptografar(senha));
        
        if(admin.getSenha().equals(adminDAO.listar(admin.getId()).get(0).getSenha())) {
            admin = adminDAO.listar(admin.getId()).get(0);
            return admin;
        }  else {
            admin = null;
            return admin;
        }
    }

    public static void atualizar(int id, String nome, String email, String senha, String dataNascimento, String cargo) {
        AdministradorDAO adminDAO = new AdministradorDAO();
        Administrador novoAdmin = new Administrador();
        novoAdmin = adminDAO.listar(id).get(0);
        if(!nome.equals("")) {
            novoAdmin.setNome(nome);
        }
        if(!email.equals("")) {
            novoAdmin.setEmail(email);
        }
        if(!senha.equals("")) {
            novoAdmin.setSenha(SistemaController.encriptografar(senha));
        }
        if(!dataNascimento.equals("dd/mm/aaaa")) {
            novoAdmin.setDataNascimento(dataNascimento);
        }
        if(!cargo.equals("")) {
            novoAdmin.setCargo(cargo);
        }
        adminDAO.atualizar(novoAdmin, 6);
    }

    public static List<Administrador> listaAdministradores() { 
        AdministradorDAO adminDAO = new AdministradorDAO();
        return adminDAO.listar(0);
    }

    public static void tableAdministradoresFrame() {
        TableAdministradores tableAdministradores = new TableAdministradores();
        tableAdministradores.show(true);
    }

    public static Administrador infoPessoais(int id) {
        AdministradorDAO adminDAO = new AdministradorDAO();
        return adminDAO.listar(id).get(0);
    }

    public static void cadastrar(String nome, String email, String senha, String dataNascimento, String cargo) {
        AdministradorDAO adminDAO = new AdministradorDAO();
        Administrador novoAdmin = new Administrador();
        novoAdmin.setNome(nome);
        novoAdmin.setEmail(email);
        novoAdmin.setSenha(SistemaController.encriptografar(senha));
        novoAdmin.setDataNascimento(dataNascimento);
        novoAdmin.setCargo(cargo);
        adminDAO.adicionar(novoAdmin);
    }
}
