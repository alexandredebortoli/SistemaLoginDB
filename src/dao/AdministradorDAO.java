package dao;

import conexao.ConexaoDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
    
    public void atualizar(Administrador admin, int opcao) {
        String QUERY = "";
        do {
            switch (opcao) {
                case 1 -> {
                    QUERY = "UPDATE administrador SET nome = '" + admin.getNome() + "' WHERE id = " + admin.getId();
                }
                case 2 -> {
                    QUERY = "UPDATE administrador SET email = '" + admin.getEmail() + "' WHERE id = " + admin.getId();
                }
                case 3 -> {
                    QUERY = "UPDATE administrador SET senha = '" + admin.getSenha() + "' WHERE id = " + admin.getId();
                }
                case 4 -> {
                    QUERY = "UPDATE administrador SET dataNascimento = '" + admin.getDataNascimento() + "' WHERE id = " + admin.getId();
                }
                case 5 -> {
                    QUERY = "UPDATE administrador SET cargo = '" + admin.getCargo() + "' WHERE id = " + admin.getId();
                }
                case 6 -> {
                    QUERY = "UPDATE administrador SET nome = '" + admin.getNome()
                            + "', email = '" + admin.getEmail()
                            + "', senha = '" + admin.getSenha()
                            + "', dataNascimento = '" + admin.getDataNascimento() 
                            + "', cargo = '" + admin.getCargo()
                            + "' WHERE id = " + admin.getId();
                } default -> {}
            } 
        } while(QUERY.equals(""));

        Connection connection = null;
        try {
            connection = ConexaoDB.createConnectionMySQL();
            Statement statement = connection.createStatement();
            statement.executeUpdate(QUERY);
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
        Connection connection = null;
        try {
            connection = ConexaoDB.createConnectionMySQL();
            Statement statement = connection.createStatement();
            statement.executeUpdate(QUERY);
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

    /*public void atualiarSenhaAdministrador(int id, String senha) {
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
    }*/
}
