/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexao.ConexaoDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Administrador;

public class AdministradorDAO {
    public void adicionar(Administrador administrador) {
        String QUERY = "INSERT INTO administrador (nome, email, senha, dataNascimento, cargo, status)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = conexao.ConexaoDB.createConnectionMySQL();
            preparedStatement = (PreparedStatement) connection.prepareStatement(QUERY);
            preparedStatement.setString(1, administrador.getNome());
            preparedStatement.setString(2, administrador.getEmail());
            preparedStatement.setString(3, administrador.getSenha());
            preparedStatement.setString(4, administrador.getDataNascimento());
            preparedStatement.setString(5, administrador.getCargo());
            preparedStatement.setInt(6, administrador.getStatus());

            

            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public List<Administrador> listar(int id) {
        List<Administrador> administradores = new ArrayList<Administrador>();
        String QUERY;
        if(id == 0) {
            QUERY = "SELECT * FROM administrador WHERE status != 0";
        }
        else {
            QUERY = "SELECT * FROM administrador WHERE id = " + id + " AND status != 0";
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConexaoDB.createConnectionMySQL();
            preparedStatement = (PreparedStatement) connection.prepareStatement(QUERY);

            resultSet = preparedStatement.executeQuery(QUERY);

            while (resultSet.next()) {
                Administrador administrador = new Administrador();

                administrador.setId(resultSet.getInt("id"));
                administrador.setNome(resultSet.getString("nome"));
                administrador.setEmail(resultSet.getString("email"));
                administrador.setSenha(resultSet.getString("senha"));
                administrador.setDataNascimento(resultSet.getString("dataNascimento"));
                administrador.setCargo(resultSet.getString("cargo"));
                administrador.setStatus(resultSet.getInt("status"));
                
                administradores.add(administrador);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(preparedStatement != null) {
                    preparedStatement.close();
                }
                if(connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return administradores;
    }
    
        public void atualizar(int id) {
        Scanner scan = new Scanner(System.in);
        System.out.println("1. Nome\n2. Email\n3. Senha\n4. Data de Nascimento\n5. Cargo\n6. Tudo");
        System.out.print("Alterar: ");
        String QUERY = "";
        do {
            Administrador administrador = new Administrador();
            switch (Integer.parseInt(scan.nextLine())) {
                case 1 -> {
                    System.out.print("Novo nome: ");
                    administrador.setNome(scan.nextLine());
                    QUERY = "UPDATE administrador SET nome = '" + administrador.getNome() + "' WHERE id = " + id;
                }
                case 2 -> {
                    System.out.print("Novo Email: ");
                    administrador.setEmail(scan.nextLine());
                    QUERY = "UPDATE administrador SET email = '" + administrador.getEmail() + "' WHERE id = " + id;
                }
                case 3 -> {
                    System.out.print("Nova Senha: ");
                    administrador.setSenha(criptografia.Criptografar.encriptografar(scan.nextLine()));
                    QUERY = "UPDATE administrador SET senha = '" + administrador.getSenha() + "' WHERE id = " + id;
                }
                case 4 -> {
                    System.out.print("Nova Data de Nascimento: ");
                    administrador.setDataNascimento(scan.nextLine());
                    QUERY = "UPDATE administrador SET dataNascimento = '" + administrador.getDataNascimento() + "' WHERE id = " + id;
                }
                case 5 -> {
                    System.out.print("Novo Cargo: ");
                    administrador.setCargo(scan.nextLine());
                    QUERY = "UPDATE administrador SET cargo = '" + administrador.getCargo() + "' WHERE id = " + id;
                }
                case 6 -> {
                    System.out.print("Novo nome: ");
                    administrador.setNome(scan.nextLine());
                    System.out.print("Novo email: ");
                    administrador.setEmail(scan.nextLine());
                    System.out.print("Nova senha: ");
                    administrador.setSenha(criptografia.Criptografar.encriptografar(scan.nextLine()));
                    System.out.print("Nova data de nascimento: ");
                    administrador.setDataNascimento(scan.nextLine());
                    System.out.print("Novo cargo: ");
                    administrador.setCargo(scan.nextLine());
                    QUERY = "UPDATE administrador SET nome = '" + administrador.getNome()
                            + "', email = '" + administrador.getEmail()
                            + "', senha = '" + administrador.getSenha()
                            + "', dataNascimento = '" + administrador.getDataNascimento() 
                            + "', cargo = '" + administrador.getCargo()
                            + "' WHERE id = " + id;
                }
                default -> System.out.println("Opcão inválida!");
            }
        } while(QUERY.equals(""));

        Connection connection = null;
        try {
            connection = ConexaoDB.createConnectionMySQL();
            Statement statement = connection.createStatement();
            statement.executeUpdate(QUERY);

            System.out.println("Alterado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void status(int id, int status) {
        String QUERY = "UPDATE administrador SET status = " + status + " WHERE id = " + id;
        if(status == 1) {
            System.out.println("Ativando...");
        } else if(status == 2) {
            System.out.println("Inativando...");
        } else {
            System.out.println("Deletando...");
        }
        Connection connection = null;
        try {
            connection = ConexaoDB.createConnectionMySQL();
            Statement statement = connection.createStatement();
            statement.executeUpdate(QUERY);

            if(status == 1) {
                System.out.println("\nAtivado com sucesso!");
            } else if(status == 2) {
                System.out.println("\nInativado com sucesso!");
            } else {
                System.out.println("\nDeletado com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public Administrador verificarLogin(int id, String senha) {
        Administrador administrador = null;
        if(senha.equals(listar(id).get(0).getSenha())) {
            administrador = listar(id).get(0);
        }
        return administrador;
    }

    public void atualiarSenhaAdministrador(int id, String senha) {
        String QUERY = "UPDATE administrador SET senha = '" + criptografia.Criptografar.encriptografar(senha) + "' WHERE id = " + id;
        Connection connection = null;
        try {
            connection = ConexaoDB.createConnectionMySQL();
            Statement statement = connection.createStatement();
            statement.executeUpdate(QUERY);

            System.out.println("Alterado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
