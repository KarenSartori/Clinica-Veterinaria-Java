/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class ConsultaDAO extends DAO{
    
    private static ConsultaDAO instance;
    
    private ConsultaDAO() {
        getConnection();
        createTable();
    }
    
    //singleton
    public static ConsultaDAO getInstance(){
        return (instance == null ? (instance = new ConsultaDAO()) : instance);
    }
    
    //CRUD
    public Consulta create(Calendar data, Calendar horario, String comentario, int id_animal, int id_veterinario, int id_tratamento, int id_pagamento, boolean terminou) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO consulta (data, horario, comentario, id_animal, id_veterinario, id_tratamento, id_pagamento, terminou) VALUES (?, ?, ?,?,?,?,?, ?)");
            stmt.setString(1, dateFormat.format(data.getTime()));
            stmt.setTime(2, new java.sql.Time(horario.getTimeInMillis()));
            stmt.setString(3, comentario);
            stmt.setInt(4, id_animal);
            stmt.setInt(5, id_veterinario);
            stmt.setInt(6, id_tratamento);
            stmt.setInt(7, id_pagamento);
            stmt.setInt(8, (terminou?1:0));
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("consulta","id"));
    }
    

    
    private Consulta buildObject(ResultSet rs) throws SQLException {
        Calendar data = Calendar.getInstance();
        data.setTime(rs.getDate("data"));
        
        Calendar horario = Calendar.getInstance();
        horario.setTime(rs.getTime("horario"));

        return new Consulta(
            rs.getInt("id"),
            data,
            horario,
            rs.getString("comentario"),
            rs.getInt("id_animal"),
            rs.getInt("id_veterinario"),
            rs.getInt("id_tratamento"),
            rs.getInt("id_pagamento"),
            rs.getInt("terminou") == 1
        );
    }

    //generico Retriever
    public List retrieve(String query){
        List<Consulta> consultas = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                consultas.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return consultas;
    }
    
    public List<Consulta> retrieveAll() {
    List<Consulta> consultas = this.retrieve("SELECT * FROM consulta");
    if (consultas.isEmpty()) {
        System.out.println("Nenhuma consulta encontrada ao carregar todas as consultas.");
    }
    return consultas;
}
    
    //retrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM consulta WHERE id = " + lastId("consulta", "id"));
    }

    public Consulta retrieveById(int id) {
    Consulta consulta = null;
    try {
        PreparedStatement stmt = getConnection().prepareStatement("SELECT * FROM consulta WHERE id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            consulta = buildObject(rs); // Constrói o objeto Consulta a partir do ResultSet
        }
        rs.close();
        stmt.close();
    } catch (SQLException e) {
        System.err.println("Erro ao buscar consulta por ID: " + e.getMessage());
    }
    return consulta;
}
    
    public List<Consulta> retrieveByClienteId(int clienteId) {
    List<Consulta> consultas = new ArrayList<>();
    String query = "SELECT * FROM consulta WHERE id_animal IN (SELECT id FROM animal WHERE id_cliente = " + clienteId + ")";
    ResultSet rs = getResultSet(query);
    try {
        if (!rs.isBeforeFirst()) { // Verifica se o ResultSet está vazio
            System.out.println("Nenhuma consulta encontrada para o cliente ID: " + clienteId);
        }
        while (rs.next()) {
            consultas.add(buildObject(rs));
        }
    } catch (SQLException e) {
        System.err.println("Exception: " + e.getMessage());
    }
    return consultas;
}


    
    //update
    public void update(Consulta consulta) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE consulta SET data=?, horario=?, comentario=?, id_animal=?, id_veterinario=?, id_tratamento=?, id_pagamento =?, terminou=? WHERE id=?");
            stmt.setDate(1, new Date(consulta.getData().getTimeInMillis()));
            stmt.setTime(2, new java.sql.Time(consulta.getHorario().getTimeInMillis()));
            stmt.setString(3, consulta.getComentario());
            stmt.setInt(4, consulta.getIdAnimal());
            stmt.setInt(5, consulta.getIdVeterinario());
            stmt.setInt(6, consulta.getIdTratamento());
            stmt.setInt(7, consulta.getIdPagamento());
            stmt.setInt(8, (consulta.isTerminou()?1:0));
            stmt.setInt(9, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException ex){
            System.err.println("Exception " + ex.getMessage());
        }
    }
    
    
    
   //delete
    public void delete(Consulta consulta) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM consulta WHERE id = ?");
            stmt.setInt(1, consulta.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
    
    public List<Consulta> retrieveByAnimalId(int animalId) {
    List<Consulta> consultas = new ArrayList<>();
    String query = "SELECT * FROM consulta WHERE id_animal = ?";

    try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
        stmt.setInt(1, animalId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Consulta consulta = buildObject(rs);
            consultas.add(consulta);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao buscar consultas por animal: " + e.getMessage());
    }
    return consultas;
}
    
    public List<Consulta> retrieveByAnimalIForPagament(int animalId) {
    List<Consulta> consultas = new ArrayList<>();
    // Filtra apenas as consultas que não possuem um pagamento associado
    String query = "SELECT * FROM consulta WHERE id_animal = ? AND (id_pagamento = 0 OR id_pagamento IS NULL)";

    try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
        stmt.setInt(1, animalId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Consulta consulta = buildObject(rs);
            consultas.add(consulta);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao buscar consultas por animal sem pagamento: " + e.getMessage());
    }
    return consultas;
}
    
    public List<Consulta> retrieveByAnimalIdForTreatment(int animalId) {
    List<Consulta> consultas = new ArrayList<>();
    
    String query = "SELECT * FROM consulta c WHERE c.id_animal = ? AND NOT EXISTS (" +
                   "SELECT 1 FROM tratamento e WHERE e.id_animal = c.id_animal AND e.dtInicio = c.data)";

    try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
        stmt.setInt(1, animalId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Consulta consulta = buildObject(rs);
            consultas.add(consulta);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao buscar consultas sem tratamento por animal: " + e.getMessage());
    }
    return consultas;
}


public List<Consulta> retrieveByAnimalIdForExam(int animalId) {
    List<Consulta> consultas = new ArrayList<>();
    String query = "SELECT * FROM consulta c WHERE c.id_animal = ? AND NOT EXISTS (" +
                   "SELECT 1 FROM exame e WHERE e.id_consulta = c.id AND e.nome IS NOT NULL)";

    try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
        stmt.setInt(1, animalId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Consulta consulta = buildObject(rs);
            consultas.add(consulta);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao buscar consultas para exame por animal: " + e.getMessage());
    }
    return consultas;
}

    public List<Consulta> retrieveByVeterinarioId(int veterinarioId) {
    List<Consulta> consultas = new ArrayList<>();
    String query = "SELECT * FROM consulta WHERE id_veterinario = ?";

    try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
        stmt.setInt(1, veterinarioId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Consulta consulta = buildObject(rs);
            consultas.add(consulta);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao buscar consultas por veterinário: " + e.getMessage());
    }
    return consultas;
}
    
    public List<Consulta> retrieveByDataEVeterinario(Calendar data, int veterinarioId) {
    List<Consulta> consultas = new ArrayList<>();
    String query = "SELECT * FROM consulta WHERE data = ? AND id_veterinario = ?";

    try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
        stmt.setDate(1, new java.sql.Date(data.getTimeInMillis()));
        stmt.setInt(2, veterinarioId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            consultas.add(buildObject(rs));
        }
    } catch (SQLException e) {
        System.err.println("Erro ao buscar consultas por data e veterinário: " + e.getMessage());
    }

    return consultas;
}


}