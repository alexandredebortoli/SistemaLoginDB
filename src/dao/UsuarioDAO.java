package dao;

import conexao.ConexaoDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;

public class UsuarioDAO {
    public void adicionar(Usuario usuario) {
        String QUERY = "INSERT INTO usuario (nome, email, senha, dataNascimento, cargo, status)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
            connection = conexao.ConexaoDB.createConnectionMySQL();
            preparedStatement = (PreparedStatement) connection.prepareStatement(QUERY);
            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getEmail());
            preparedStatement.setString(3, usuario.getSenha());
            preparedStatement.setString(4, usuario.getDataNascimento());
            preparedStatement.setString(5, usuario.getCargo());
            preparedStatement.setInt(6, usuario.getStatus());

            

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
    
    public List<Usuario> listar(int id) {
        List<Usuario> usuarios = new ArrayList<Usuario>();
        String QUERY;
        if(id == 0) {
            QUERY = "SELECT * FROM usuario WHERE status != 0";
        }
        else {
            QUERY = "SELECT * FROM usuario WHERE id = " + id + " AND status != 0";
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConexaoDB.createConnectionMySQL();
            preparedStatement = (PreparedStatement) connection.prepareStatement(QUERY);

            resultSet = preparedStatement.executeQuery(QUERY);

            while (resultSet.next()) {
                Usuario usuario = new Usuario();

                usuario.setId(resultSet.getInt("id"));
                usuario.setNome(resultSet.getString("nome"));
                usuario.setEmail(resultSet.getString("email"));
                usuario.setSenha(resultSet.getString("senha"));
                usuario.setDataNascimento(resultSet.getString("dataNascimento"));
                usuario.setCargo(resultSet.getString("cargo"));
                usuario.setStatus(resultSet.getInt("status"));
                
                usuarios.add(usuario);
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
        return usuarios;
    }
    
    public void atualizar(int id) {
        Scanner scan = new Scanner(System.in);
        System.out.println("1. Nome\n2. Email\n3. Senha\n4. Data de Nascimento\n5. Cargo\n6. Tudo");
        System.out.print("Alterar: ");
        String QUERY = "";
        do {
            Usuario usuario = new Usuario();
            switch (Integer.parseInt(scan.nextLine())) {
                case 1 -> {
                    System.out.print("Novo nome: ");
                    usuario.setNome(scan.nextLine());
                    QUERY = "UPDATE usuario SET nome = '" + usuario.getNome() + "' WHERE id = " + id;
                }
                case 2 -> {
                    System.out.print("Novo Email: ");
                    usuario.setEmail(scan.nextLine());
                    QUERY = "UPDATE usuario SET email = '" + usuario.getEmail() + "' WHERE id = " + id;
                }
                case 3 -> {
                    System.out.print("Nova Senha: ");
                    usuario.setSenha(criptografia.Criptografar.encriptografar(scan.nextLine()));
                    QUERY = "UPDATE usuario SET senha = '" + usuario.getSenha() + "' WHERE id = " + id;
                }
                case 4 -> {
                    System.out.print("Nova Data de Nascimento: ");
                    usuario.setDataNascimento(scan.nextLine());
                    QUERY = "UPDATE usuario SET dataNascimento = '" + usuario.getDataNascimento() + "' WHERE id = " + id;
                }
                case 5 -> {
                    System.out.print("Novo Cargo: ");
                    usuario.setCargo(scan.nextLine());
                    QUERY = "UPDATE usuario SET cargo = '" + usuario.getCargo() + "' WHERE id = " + id;
                }
                case 6 -> {
                    System.out.print("Novo nome: ");
                    usuario.setNome(scan.nextLine());
                    System.out.print("Novo email: ");
                    usuario.setEmail(scan.nextLine());
                    System.out.print("Nova senha: ");
                    usuario.setSenha(criptografia.Criptografar.encriptografar(scan.nextLine()));
                    System.out.print("Nova data de nascimento: ");
                    usuario.setDataNascimento(scan.nextLine());
                    System.out.print("Novo cargo: ");
                    usuario.setCargo(scan.nextLine());
                    QUERY = "UPDATE usuario SET nome = '" + usuario.getNome()
                            + "', email = '" + usuario.getEmail()
                            + "', senha = '" + usuario.getSenha()
                            + "', dataNascimento = '" + usuario.getDataNascimento() 
                            + "', cargo = '" + usuario.getCargo()
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

            System.out.println("Atualizado com sucesso!");
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
        String QUERY = "UPDATE usuario SET status = " + status + " WHERE id = " + id;
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
    
    public Usuario verificarLogin(int id, String senha) {
        Usuario usuario = null;
        if(senha.equals(listar(id).get(0).getSenha())) {
            usuario = listar(id).get(0);
        }
        return usuario;
    }
    
    public static void atualizarSenhaUsuario(int id, String senha) {
        String QUERY = "UPDATE usuario SET senha = '" + criptografia.Criptografar.encriptografar(senha) + "' WHERE id = " + id;
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
