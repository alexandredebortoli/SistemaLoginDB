package model;

public class Administrador extends Usuario{
    public Administrador() {}
    public Administrador(int id, String nome, String email, String senha, String dataNascimento, String cargo) {
        super(id, nome, email, senha, dataNascimento, cargo);
    }    
}
