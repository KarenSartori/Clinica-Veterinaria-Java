/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.DAO.getConnection;

/**
 *
 * @author karen
 */
public class TratamentoDAO extends DAO{
    
    private static TratamentoDAO instance;
    
    private TratamentoDAO() {
        getConnection();
        createTable();
    }
    
    //singleton
    public static TratamentoDAO getInstance(){
        return (instance == null ? (instance = new TratamentoDAO()) : instance);
    }
    
    //CRUD
    public Tratamento create(int id_animal, String nome, Calendar dtInicio, Calendar dtFim, boolean terminou, String tratamentoIndicado) {
    try {
        PreparedStatement stmt;
        stmt = DAO.getConnection().prepareStatement(
            "INSERT INTO tratamento (id_animal, nome, dtInicio, dtFim, terminou, tratamentoIndicado) VALUES (?, ?, ?, ?, ?, ?)"
        );
        stmt.setInt(1, id_animal);
        stmt.setString(2, nome);
        stmt.setString(3, dateFormat.format(dtInicio.getTime()));

        if (dtFim != null) {
            stmt.setString(4, dateFormat.format(dtFim.getTime()));
        } else {
            stmt.setNull(4, java.sql.Types.DATE);
        }

        stmt.setInt(5, (terminou ? 1 : 0));
        stmt.setString(6, tratamentoIndicado);  // Corrigido para garantir o armazenamento

        executeUpdate(stmt);
    } catch (SQLException ex) {
        Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
    }

    return this.retrieveById(lastId("tratamento", "id"));
}

    
private Tratamento buildObject(ResultSet rs) {
    Tratamento tratamento = null;
    try {
        Calendar dtIni = null;
        Calendar dtFim = null;

        String dtInicioStr = rs.getString("dtInicio");
        if (dtInicioStr != null && !dtInicioStr.isEmpty()) {
            dtIni = Calendar.getInstance();
            dtIni.setTime(dateFormat.parse(dtInicioStr));
        }

        String dtFimStr = rs.getString("dtFim");
        if (dtFimStr != null && !dtFimStr.isEmpty()) {
            dtFim = Calendar.getInstance();
            dtFim.setTime(dateFormat.parse(dtFimStr));
        }

        tratamento = new Tratamento(
            rs.getInt("id"), 
            rs.getInt("id_animal"), 
            rs.getString("nome"), 
            dtIni, 
            dtFim, 
            rs.getBoolean("terminou"), 
            rs.getString("tratamentoIndicado")
        );
    } catch (SQLException | ParseException e) {
        Logger.getLogger(TratamentoDAO.class.getName()).log(Level.SEVERE, null, e);
    }
    return tratamento;
}


    //generico Retriever
    public List retrieve(String query) {
        List<Tratamento> tratamentos = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                tratamentos.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return tratamentos;
    }
    
    //retrieveAll
    public List retrieveAll(){
        return this.retrieve("SELECT * FROM tratamento"); //retorna uma lista
    }
    
    //retrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM tratamento WHERE id = " + lastId("tratamento", "id"));
    }
    
    //retrieveById (retorna um unico cliente)
    public Tratamento retrieveById(int id) { 
        List <Tratamento> tratamentos = this.retrieve("SELECT * FROM tratamento WHERE id = " + id);
        return (tratamentos.isEmpty() ? null : tratamentos.get(0)); //retorna um unico cliente ou nulo
    }
    
    
    //update
    public void update(Tratamento tratamento) {
    try {
        PreparedStatement stmt;
        stmt = DAO.getConnection().prepareStatement(
            "UPDATE tratamento SET id_animal=?, nome=?, dtInicio=?, dtFim=?, terminou=?, tratamentoIndicado=? WHERE id=?"
        );
        stmt.setInt(1, tratamento.getIdAnimal());
        stmt.setString(2, tratamento.getNome());
        stmt.setString(3, dateFormat.format(tratamento.getDataInicio().getTime()));

        if (tratamento.getDataFim() != null) {
            stmt.setString(4, dateFormat.format(tratamento.getDataFim().getTime()));
        } else {
            stmt.setNull(4, java.sql.Types.DATE);
        }

        stmt.setInt(5, (tratamento.isTerminou() ? 1 : 0));
        stmt.setString(6, tratamento.getTratamentoIndicado());  // Corrigido
        stmt.setInt(7, tratamento.getId());

        executeUpdate(stmt);
    } catch (SQLException ex) {
        System.err.println("Exception " + ex.getMessage());
    }
}

    
    
   //delete
    public void delete(Tratamento tratamento) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM tratamento WHERE id = ?");
            stmt.setInt(1, tratamento.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    
public List<Tratamento> retrieveByAnimalId(int idAnimal) {
    return this.retrieve("SELECT * FROM tratamento WHERE id_animal = " + idAnimal);
}

    public List<Tratamento> retrieveByConsultaId(int idAnimal) {
    return this.retrieve("SELECT * FROM tratamento WHERE id_animal = " + idAnimal);
}
}
