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
public class PagamentoDAO extends DAO{
    
    private static PagamentoDAO instance;
    
    private PagamentoDAO() {
        getConnection();
        createTable();
    }
    
    //singleton
    public static PagamentoDAO getInstance(){
        return (instance == null ? (instance = new PagamentoDAO()) : instance);
    }
    
    //CRUD
    public Pagamento create(Double valorConsulta, String formaPagamento, boolean consultaPaga, int id_consulta) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("INSERT INTO pagamento (valorConsulta, formaPagamento, consultaPaga, id_consulta) VALUES (?,?,?, ?)");
            stmt.setDouble(1, valorConsulta);
            stmt.setString(2, formaPagamento);
            stmt.setInt(3, (consultaPaga?1:0));
            stmt.setInt(4, id_consulta);
            executeUpdate(stmt);
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return this.retrieveById(lastId("pagamento","id"));
    }
    
    private Pagamento buildObject(ResultSet rs) {
        Pagamento pagamento = null;
        try {
            pagamento = new Pagamento(rs.getInt("id"), rs.getDouble("valorConsulta"), rs.getString("formaPagamento"), rs.getBoolean("consultaPaga"), rs.getInt("id_consulta"));
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return pagamento;
    }

    //generico Retriever
    public List retrieve(String query) {
        List<Pagamento> pagamentos = new ArrayList();
        ResultSet rs = getResultSet(query);
        try {
            while (rs.next()) {
                pagamentos.add(buildObject(rs));
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return pagamentos;
    }
    
    //retrieveAll
    public List retrieveAll(){
        return this.retrieve("SELECT * FROM pagamento"); //retorna uma lista
    }
    
    //retrieveLast
    public List retrieveLast(){
        return this.retrieve("SELECT * FROM pagamento WHERE id = " + lastId("pagamento", "id"));
    }
    
    
    //retrieveById (retorna um unico cliente)
    public Pagamento retrieveById(int id) { 
        List <Pagamento> pagamentos = this.retrieve("SELECT * FROM pagamento WHERE id = " + id);
        return (pagamentos.isEmpty() ? null : pagamentos.get(0)); //retorna um unico cliente ou nulo
    }
    
/*public List<Pagamento> retrieveByClienteId(int clienteId) {
    String query = "SELECT * FROM pagamento WHERE id_consulta IN (SELECT id FROM consulta WHERE id_cliente = ?)";
    List<Pagamento> pagamentos = new ArrayList<>();
    
    try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
        stmt.setInt(1, clienteId);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            pagamentos.add(buildObject(rs));
        }
    } catch (SQLException e) {
        System.err.println("Exception: " + e.getMessage());
    }
    
    return pagamentos;
}*/
    
public List<Pagamento> retrieveByClienteId(int clienteId) {
    String query = "SELECT * FROM pagamento WHERE id_consulta IN (SELECT id FROM consulta WHERE id_animal IN (SELECT id FROM animal WHERE id_cliente = ?))";
    List<Pagamento> pagamentos = new ArrayList<>();
    
    try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
        stmt.setInt(1, clienteId);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            pagamentos.add(buildObject(rs));
        }
    } catch (SQLException e) {
        System.err.println("Exception: " + e.getMessage());
    }
    
    return pagamentos;
}



    
    //update
    public void update(Pagamento pagamento) {
        try {
            PreparedStatement stmt;
            stmt = DAO.getConnection().prepareStatement("UPDATE pagamento SET valorConsulta=?, formaPagamento=?, consultaPaga=?, id_consulta=? WHERE id=?");
            stmt.setDouble(1, pagamento.getValorConsulta());
            stmt.setString(2, pagamento.getFormaPagamento());
            stmt.setInt(3, (pagamento.getConsultaPaga()?1:0));
            stmt.setInt(4, pagamento.getIdConsulta());
            stmt.setInt(5, pagamento.getId());
            executeUpdate(stmt);
        } catch (SQLException ex){
            System.err.println("Exception " + ex.getMessage());
        }
    }

    
   //delete
    public void delete(Pagamento pagamento) {
        PreparedStatement stmt;
        try {
            stmt = DAO.getConnection().prepareStatement("DELETE FROM pagamento WHERE id = ?");
            stmt.setInt(1, pagamento.getId());
            executeUpdate(stmt);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
