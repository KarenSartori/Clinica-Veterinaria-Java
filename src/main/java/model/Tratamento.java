/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 *
 * @author Karen
 */
public class Tratamento {
    
    private int id;
    private String nome;  
    private Calendar dtInicio;
    private Calendar dtFim;
    private int id_animal;
    private boolean terminou;
    private String tratamentoIndicado;
    
    
    public Tratamento(int id,  int id_animal, String nome, Calendar dtInicio, Calendar dtFim, boolean terminou, String tratamentoIndicado){
        this.id = id;
        this.id_animal = id_animal;
        this.nome = nome;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
        this.terminou = terminou;
        this.tratamentoIndicado = tratamentoIndicado;
    }

    //getters
    
    public String getTratamentoIndicado(){
        return tratamentoIndicado;
    }
    
    public int getId(){
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public int getIdAnimal() {
        return id_animal;
    }

    public Calendar getDataInicio() {
        return dtInicio;
    }

    public Calendar getDataFim() {
        return dtFim;
    }

    public boolean isTerminou() {
        return terminou;
    }


    //setters
    public void setTratamentoIndicado(String tratamentoIndicado){
        this.tratamentoIndicado = tratamentoIndicado;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setNome(String nome) {
        this.nome =nome; 
    }

    public void setDataInicio(Calendar dataInicio) {
        this.dtInicio = dataInicio;
    }
    
    public void setIdAnimal(int id_animal){
        this.id_animal = id_animal;
    }

    public void setDataFim(Calendar dataFim) {
        this.dtFim = dataFim;
    }

    public void setTerminou(boolean terminou){
        this.terminou = terminou;
    }
    
@Override
public String toString() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    LocalDateTime inicio = LocalDateTime.ofInstant(dtInicio.toInstant(), ZoneId.systemDefault());
    LocalDateTime fim = LocalDateTime.ofInstant(dtFim.toInstant(), ZoneId.systemDefault());

    return "Tratamento{" +
            "nome='" + nome + '\'' +
            ", dataInicio=" + dtf.format(inicio) +
            ", dataFim=" + dtf.format(fim) +
            ", id_animal=" + id_animal +
            ", terminou=" + terminou +
            '}';
}

    
}

