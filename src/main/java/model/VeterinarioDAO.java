/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.DAO.getConnection;

/**
 *
 * @author karen
 */
public class VeterinarioDAO extends DAO{
    
    private static VeterinarioDAO instance;
    
    private VeterinarioDAO() {
        getConnection();
        createTable();
    }
    
    //singleton
    public static VeterinarioDAO getInstance(){
        return (instance == null ? (instance = new VeterinarioDAO()) : instance);
    }
    
    //CRUD
    public Veterinario create(String nome, String email, String telefone, String especialidade, String crmv) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO veterinario (nome, email, telefone, especialidade, crmv) VALUES (?,?,?,?,?)");
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, telefone);
            stmt.setString(4, especialidade);
            stmt.setString(5, crmv);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("veterinario","id"));
    }
    
    private Veterinario buildObject(ResultSet rs) {
        Veterinario veterinario = null;
        try {
            veterinario = new Veterinario(rs.getInt("id"), rs.getString("nome"), rs.getString("email"), rs.getString("telefone"), rs.getString("especialidade"), rs.getString("crmv"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return veterinario;
    }

    //generico Retriever
    public List retrieve(String query) {
        List<Veterinario> veterinarios = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                veterinarios.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return veterinarios;
    }
    
    //retrieveAll
    public List retrieveAll(){
        return this.retrieve("SELECT * FROM veterinario"); //retorna uma lista
    }
    
    //retrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM veterinario WHERE id = " + lastId("veterinario", "id"));
    }
    
    //retrieveById (retorna um unico cliente)
    public Veterinario retrieveById(int id) { 
        List <Veterinario> veterinarios = this.retrieve("SELECT * FROM veterinario WHERE id = " + id);
        return (veterinarios.isEmpty() ? null : veterinarios.get(0)); //retorna um unico cliente ou nulo
    }
    
    
    //update
    public void update(Veterinario veterinario) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE veterinario SET nome=?, email=?, telefone=?, especialidade=?, crmv=? WHERE id=?");
            stmt.setString(1, veterinario.getNome());
            stmt.setString(2, veterinario.getEmail());
            stmt.setString(3, veterinario.getTelefone());
            stmt.setString(4, veterinario.getEspecialidade());
            stmt.setString(5, veterinario.getCrmv());
            stmt.setInt(6, veterinario.getId());
            executeUpdate(stmt);
        } catch (SQLException ex){
            System.err.println("Exception " + ex.getMessage());
        }
    }
    
    //retrieve by similar name
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM veterinario WHERE nome LIKE '%" + nome + "%'");
    }    
    
   //delete
    public void delete(Veterinario veterinario) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM veterinario WHERE id = ?");
            stmt.setInt(1, veterinario.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    
    
    public Veterinario retrieveByName(String name) {
    Veterinario veterinario = null;
    ResultSet rs = getResultSet("SELECT * FROM veterinario WHERE nome = '" + name + "'");
    try {
        if (rs.next()) {
            veterinario = buildObject(rs);
        }
    } catch (SQLException e) {
        System.err.println("Exception: " + e.getMessage());
    }
    return veterinario;
}

    
    
}
