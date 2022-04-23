package dao;

import conexao.ConexaoDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
    
    public void atualizar(Usuario usuario, int opcao) {
        String QUERY = "";
        do {
            switch (opcao) {
                case 1 -> {
                    QUERY = "UPDATE usuario SET nome = '" + usuario.getNome() + "' WHERE id = " + usuario.getId();
                }
                case 2 -> {
                    QUERY = "UPDATE usuario SET email = '" + usuario.getEmail() + "' WHERE id = " + usuario.getId();
                }
                case 3 -> {
                    QUERY = "UPDATE usuario SET senha = '" + usuario.getSenha() + "' WHERE id = " + usuario.getId();
                }
                case 4 -> {
                    QUERY = "UPDATE usuario SET dataNascimento = '" + usuario.getDataNascimento() + "' WHERE id = " + usuario.getId();
                }
                case 5 -> {
                    QUERY = "UPDATE usuario SET cargo = '" + usuario.getCargo() + "' WHERE id = " + usuario.getId();
                }
                case 6 -> {
                    QUERY = "UPDATE usuario SET nome = '" + usuario.getNome()
                            + "', email = '" + usuario.getEmail()
                            + "', senha = '" + usuario.getSenha()
                            + "', dataNascimento = '" + usuario.getDataNascimento() 
                            + "', cargo = '" + usuario.getCargo()
                            + "' WHERE id = " + usuario.getId();
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
        String QUERY = "UPDATE usuario SET status = " + status + " WHERE id = " + id;
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
    
    /*public static void atualizarSenhaUsuario(int id, String senha) {
        String QUERY = "UPDATE usuario SET senha = '" + criptografia.Criptografar.encriptografar(senha) + "' WHERE id = " + id;
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
    }*/
}
