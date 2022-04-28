package controller;

import dao.AdministradorDAO;
import model.Administrador;
import dao.JavaMailDAO;
import frame.AdministradorFrame;
import frame.TableAdministradores;
import java.util.List;

public class AdministradorController {   
    public static void recuperarSenha(Administrador admin) {
        AdministradorDAO adminDAO = new AdministradorDAO();
        try {         
            admin.setSenha(SistemaController.gerarNovaSenha());
            JavaMailDAO.enviarEmail(admin.getEmail(), admin.getSenha());
            admin.setSenha(SistemaController.encriptografar(admin.getSenha()));
            adminDAO.atualizar(admin, 3); // opcao para atualizar a senha
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Administrador verificarCpf(String cpf) {
        AdministradorDAO adminDAO = new AdministradorDAO();
        Administrador admin = new Administrador();
        for(int i = 0; i < adminDAO.listar(0).size(); i++) {
            if(cpf.equals(adminDAO.listar(0).get(i).getCpf())) {
                admin = adminDAO.listar(adminDAO.listar(0).get(i).getId()).get(0);
                return admin;
            }
        }
        admin = null;
        return admin;
    }

    public static void administradorFrame() {
        AdministradorFrame administradorFrame = new AdministradorFrame();
        administradorFrame.show(true);
    }

    public static Administrador verificarLogin(String cpf, String senha) {
        AdministradorDAO adminDAO = new AdministradorDAO();
        Administrador admin = new Administrador();
        admin.setSenha(SistemaController.encriptografar(senha));
        
        for(int i = 0; i < adminDAO.listar(0).size(); i++) {
            if(cpf.equals(adminDAO.listar(0).get(i).getCpf())) {
                admin.setId(adminDAO.listar(0).get(i).getId());
                if(admin.getSenha().equals(adminDAO.listar(admin.getId()).get(0).getSenha())) {
                    admin = adminDAO.listar(admin.getId()).get(0);
                    return admin;
                }
            }
        }
        admin = null;
        return admin;
    }
    /*public static void atualizar(int id, String nome, String email, String senha, String dataNascimento, String cargo, String cpf) {
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
        if(!cpf.equals("")) {
            novoAdmin.setCpf(Integer.parseInt(cpf));
        }
        adminDAO.atualizar(novoAdmin, 7);
    }*/

    public static void atualizar(int id, String nome, String email, String senha, String dataNascimento, String cargo, int status, String cpf) {
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
        novoAdmin.setStatus(status);
        if(!cpf.equals("")) {
            novoAdmin.setCpf(cpf);
        }
        adminDAO.atualizar(novoAdmin, 7);
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

    public static void cadastrar(String nome, String email, String senha, String dataNascimento, String cargo, int status, String cpf) {
        AdministradorDAO adminDAO = new AdministradorDAO();
        Administrador novoAdmin = new Administrador();
        novoAdmin.setNome(nome);
        novoAdmin.setEmail(email);
        novoAdmin.setSenha(SistemaController.encriptografar(senha));
        novoAdmin.setDataNascimento(dataNascimento);
        novoAdmin.setCargo(cargo);
        novoAdmin.setStatus(status);
        novoAdmin.setCpf(cpf);
        adminDAO.adicionar(novoAdmin);
    }

    public static int getLastAddedId() {
        AdministradorDAO adminDAO = new AdministradorDAO();
        return adminDAO.listar(0).get(adminDAO.listar(0).size() - 1).getId();
    }
}
