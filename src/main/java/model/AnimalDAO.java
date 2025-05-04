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

/**
 *
 * @author karen
 */
public class AnimalDAO extends DAO {
    
    private static AnimalDAO instance;
    
    private AnimalDAO(){
        getConnection();
        createTable();
    }
    
    public static AnimalDAO getInstance(){
        return (instance == null ? (instance = new AnimalDAO()) : instance);
    }
    
    public Animal create(String nome, int anoNasc, String sexo, int idEspecie, Cliente cliente, Double peso, String alergia){
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO animal (nome, anoNasc, sexo, id_especie, id_cliente, peso, alergia) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, nome);
            stmt.setInt(2, anoNasc);
            stmt.setString(3, sexo);
            stmt.setInt(4, idEspecie);
            stmt.setInt(5, cliente.getId());
            stmt.setDouble(6, peso);
            stmt.setString(7, alergia);
            executeUpdate(stmt);
            } catch(SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            return this.retrieveById(lastId("animal", "id"));
    }
    
    private Animal buildObject(ResultSet rs){
        Animal animal = null;
        
        try{
            animal = new Animal(rs.getInt("id"), rs.getString("nome"), rs.getInt("anoNasc"), rs.getString("sexo"), rs.getInt("id_especie"), rs.getInt("id_cliente"), rs.getDouble("peso"), rs.getString("alergia"));
        } catch (SQLException e){
            System.err.println("Exception: " + e.getMessage());
        }
        return animal;
    }
    
    
    public List retrieve(String query){
        List<Animal> animais = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while(rs.next()) {
                animais.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return animais;
    }
    
     // RetrieveAll
    public List<Animal> retrieveAll() {
        return this.retrieve("SELECT * FROM animal");
    }

    // RetrieveById
    public Animal retrieveById(int id) {
        List<Animal> animais = this.retrieve("SELECT * FROM animal WHERE id = " + id);
        return (animais.isEmpty() ? null : animais.get(0));
    }
    
    // RetrieveByClienteId
    public List retrieveByClienteId(int clienteId) { //tinha <Anomal>
        return this.retrieve("SELECT * FROM animal WHERE id_cliente = " + clienteId);
    }
    
    // RetrieveByEspecieId
    public List retrieveByEspecieId(int especieId) {
        return this.retrieve("SELECT * FROM animal WHERE id_especie = " + especieId);
    }
    
    public List retrieveBySimilarName(int id, String nome){
        return this.retrieve("SELECT * FROM animal WHERE id_cliente = " + id + " AND nome LIKE '%" + nome + "%'");
    }

    // Update
    public void update(Animal animal) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE animal SET nome=?, anoNasc=?, sexo=?, id_especie=?, id_cliente=?, peso=?, alergia=? WHERE id=?");
            stmt.setString(1, animal.getNome());
            stmt.setInt(2, animal.getAnoNasc());
            stmt.setString(3, animal.getSexo());
            stmt.setInt(4, animal.getIdEspecie());
            stmt.setInt(5, animal.getIdCliente());
            stmt.setDouble(6, animal.getPeso());
            stmt.setString(7, animal.getAlergia());
            stmt.setInt(8, animal.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Delete
    public void delete(Animal animal) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM animal WHERE id = ?");
            stmt.setInt(1, animal.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    
    
    public Animal retrieveByName(String name) {
    Animal animal = null;
    ResultSet rs = getResultSet("SELECT * FROM animal WHERE nome = '" + name + "'");
    try {
        if (rs.next()) {
            animal = buildObject(rs);
        }
    } catch (SQLException e) {
        System.err.println("Exception: " + e.getMessage());
    }
    return animal;
}

    
    
}
    
