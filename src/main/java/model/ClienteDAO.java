/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/

package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Karen
 */
public class ClienteDAO extends DAO{
    
    private static ClienteDAO instance;
    
    private ClienteDAO() {
        getConnection();
        createTable();
    }
    
    //singleton
    public static ClienteDAO getInstance(){
        return (instance == null ? (instance = new ClienteDAO()) : instance);
    }
    
    //CRUD
    public Cliente create(String nome, String endereco, String cep, String email, String telefone) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO cliente (nome, endereco, cep, email, telefone) VALUES (?,?,?,?,?)");
            stmt.setString(1, nome);
            stmt.setString(2, endereco);
            stmt.setString(3, cep);
            stmt.setString(4, email);
            stmt.setString(5, telefone);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("cliente","id"));
    }
    
    private Cliente buildObject(ResultSet rs) {
        Cliente cliente = null;
        try {
            cliente = new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("endereco"), rs.getString("cep"), rs.getString("email"), rs.getString("telefone"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return cliente;
    }

    //generico Retriever
    public List retrieve(String query) {
        List<Cliente> clientes = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                clientes.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return clientes;
    }
    
    //retrieveAll
    public List retrieveAll(){
        return this.retrieve("SELECT * FROM cliente"); //retorna uma lista
    }
    
    //retrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM cliente WHERE id = " + lastId("cliente", "id"));
    }
    
    //retrieveById (retorna um unico cliente)
    public Cliente retrieveById(int id) { 
        List <Cliente> clientes = this.retrieve("SELECT * FROM cliente WHERE id = " + id);
        return (clientes.isEmpty() ? null : clientes.get(0)); //retorna um unico cliente ou nulo
    }
    
    
    //update
    public void update(Cliente cliente) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE cliente SET nome=?, endereco=?, cep=?, email=?, telefone=? WHERE id=?");
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getCep());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getTelefone());
            stmt.setInt(6, cliente.getId());
            executeUpdate(stmt);
        } catch (SQLException ex){
            System.err.println("Exception " + ex.getMessage());
        }
    }
    
    //retrieve by similar name
    public List retrieveBySimilarName(String nome) {
        return this.retrieve("SELECT * FROM cliente WHERE nome LIKE '%" + nome + "%'");
    }    
    
   //delete
    public void delete(Cliente cliente) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM cliente WHERE id = ?");
            stmt.setInt(1, cliente.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
}