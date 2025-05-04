/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DAO {

    //public static final String DBURL = "jdbc:h2:C:/Users/Karen/Documents/NetBeansProjects/Clinica-Java/data/dados";
    //public static final String DBURL = "Macintosh HD/Users/karensartori/Downloads/Clinica-Java (Cópia)";
    public static final String DBURL = "jdbc:h2:/Users/karensartori/Downloads/Clinica-Java (Cópia)/meu_banco.db";
    private static Connection connection;
    protected static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    // Connect to SQLite
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DBURL);
                if (connection != null) {
                    DatabaseMetaData meta = connection.getMetaData();
                }
            } catch (SQLException e) {
                System.err.println("Exception: " + e.getMessage());
            }
        }
        return connection;
    }

    protected ResultSet getResultSet(String query) {
        Statement s;
        ResultSet rs = null;
        try {
            s = (Statement) connection.createStatement();
            rs = s.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return rs;
    }

    protected int executeUpdate(PreparedStatement queryStatement) throws SQLException {
        int update;
        update = queryStatement.executeUpdate();
        return update;
    }

    protected int lastId(String tableName, String primaryKey) {
        Statement s;
        int lastId = -1;
        try {
            s = (Statement) connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT MAX(" + primaryKey + ") AS id FROM " + tableName);
            if (rs.next()) {
                lastId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return lastId;
    }

    public static void terminar() {
        try {
            (DAO.getConnection()).close();
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Create table SQLite
    protected final boolean createTable() {
        try {
            PreparedStatement stmt;
            
            // Table cliente:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS cliente( 
                                                        id INTEGER PRIMARY KEY AUTO_INCREMENT, 
                                                        nome VARCHAR, 
                                                        endereco VARCHAR, 
                                                        cep VARCHAR, 
                                                        email VARCHAR, 
                                                        telefone VARCHAR); 
                                                        """);
            executeUpdate(stmt);
            
            // Table animal:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS animal( 
                                                        id INTEGER PRIMARY KEY AUTO_INCREMENT, 
                                                        nome VARCHAR, 
                                                        anoNasc INTEGER, 
                                                        sexo VARCHAR, 
                                                        id_especie INTEGER, 
                                                        id_cliente INTEGER,
                                                        peso DECIMAL(10, 2),
                                                        alergia VARCHAR); 
                                                        """);
            executeUpdate(stmt);
            
            // Table especie:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS especie( 
                                                        id INTEGER PRIMARY KEY AUTO_INCREMENT, 
                                                        nome VARCHAR); 
                                                        """);
            executeUpdate(stmt);
            
            // Table veterinario:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS veterinario( 
                                                        id INTEGER PRIMARY KEY AUTO_INCREMENT, 
                                                        nome VARCHAR, 
                                                        email VARCHAR, 
                                                        telefone VARCHAR,
                                                        especialidade VARCHAR,
                                                        crmv VARCHAR); 
                                                        """);
            executeUpdate(stmt);        
            
            // Table consulta:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS consulta( 
                                                        id INTEGER PRIMARY KEY AUTO_INCREMENT, 
                                                        data DATE, 
                                                        horario VARCHAR, 
                                                        comentario VARCHAR, 
                                                        id_animal INTEGER, 
                                                        id_veterinario INTEGER, 
                                                        id_tratamento INTEGER,
                                                        id_pagamento INTEGER,
                                                        terminou INTEGER); 
                                                        """);
            executeUpdate(stmt);            
            
            // Table exame:
            stmt = DAO.getConnection().prepareStatement("""
                                                        CREATE TABLE IF NOT EXISTS exame( 
                                                        id INTEGER PRIMARY KEY AUTO_INCREMENT, 
                                                        nome VARCHAR, 
                                                        id_consulta INTEGER,
                                                        status INTEGER, 
                                                        resultado VARCHAR,
                                                        diagnostico VARCHAR); 
                                                        """);
            executeUpdate(stmt);    
            
             // Table pagamento:
            stmt = DAO.getConnection().prepareStatement ("""
                                                         CREATE TABLE IF NOT EXISTS pagamento(
                                                         id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                                         valorConsulta DECIMAL(10, 2),
                                                         formaPagamento VARCHAR,
                                                         consultaPaga INTEGER,
                                                         id_consulta INTEGER);
                                                         """);
            executeUpdate(stmt);
            
            stmt = DAO.getConnection().prepareStatement("""                     
                                                        CREATE TABLE IF NOT EXISTS tratamento(
                                                        id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                                        nome VARCHAR,
                                                        id_animal INTEGER,
                                                        dtInicio DATE,
                                                        dtFim DATE,
                                                        terminou INTEGER,
                                                        tratamentoIndicado VARCHAR);
                                                        """);
            executeUpdate(stmt);
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}