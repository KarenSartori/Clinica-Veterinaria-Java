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
public class ExameDAO extends DAO{
    
    private static ExameDAO instance;
    
    private ExameDAO() {
        getConnection();
        createTable();
    }
    
    //singleton
    public static ExameDAO getInstance(){
        return (instance == null ? (instance = new ExameDAO()) : instance);
    }
    
    //CRUD
    public Exame create(String nome, int id_consulta, Boolean status, String resultado, String diagnostico) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO exame (nome, id_consulta, status, resultado, diagnostico) VALUES (?,?, ?, ?, ?)");
            stmt.setString(1, nome);
            stmt.setInt(2, id_consulta);
            stmt.setBoolean(3, status); 
            stmt.setString(4, resultado);            
            stmt.setString(5, diagnostico);            
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("exame","id"));
    }
    
    private Exame buildObject(ResultSet rs) {
        Exame exame = null;
        try {
            exame = new Exame(rs.getInt("id"), rs.getString("nome"), rs.getInt("id_consulta"), rs.getBoolean("status"), rs.getString("resultado"), rs.getString("diagnostico"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return exame;
    }

    //generico Retriever
    public List retrieve(String query) {
        List<Exame> exames = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                exames.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return exames;
    }
    
    //retrieveAll
    public List retrieveAll(){
        return this.retrieve("SELECT * FROM exame"); //retorna uma lista
    }
    
    //retrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM exame WHERE id = " + lastId("exame", "id"));
    }
    
    //retrieveById (retorna um unico cliente)
    public Exame retrieveById(int id) { 
        List <Exame> exames = this.retrieve("SELECT * FROM exame WHERE id = " + id);
        return (exames.isEmpty() ? null : exames.get(0)); //retorna um unico cliente ou nulo
    }
    
    
    //update
    public void update(Exame exame) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE exame SET nome=?, id_consulta=?, status=?, resultado=?, diagnostico=? WHERE id=?");
            stmt.setString(1, exame.getNome());
            stmt.setInt(2, exame.getIdConsulta());
            stmt.setBoolean(3, exame.getStatus());
            stmt.setString(4, exame.getResultado());
            stmt.setString(5, exame.getDiagnostico());
            stmt.setInt(6, exame.getId());
            executeUpdate(stmt);
        } catch (SQLException ex){
            System.err.println("Exception " + ex.getMessage());
        }
    }

    
   //delete
    public void delete(Exame exame) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM exame WHERE id = ?");
            stmt.setInt(1, exame.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    public List<Exame> retrieveByIdConsulta(int idConsulta) {
    List<Exame> exames = new ArrayList<>();
    String query = "SELECT * FROM exame WHERE id_consulta = ?";

    try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
        stmt.setInt(1, idConsulta);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Exame exame = buildObject(rs); // Método buildObject para criar o objeto Exame a partir do ResultSet
            exames.add(exame);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao buscar exames por ID da consulta: " + e.getMessage());
    }
    return exames;
}


}
