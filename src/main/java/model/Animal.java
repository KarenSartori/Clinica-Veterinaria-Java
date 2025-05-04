/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Karen
 */
public class Animal {
    
    private int id;
    private String nome;
    private int anoNasc;
    private String sexo; 
    private int id_especie;
    private int id_cliente;
    private Double peso;
    private String alergia;

    //construtor
    public Animal(int id, String nome, int anoNasc, String sexo, int id_especie, int id_cliente, Double peso, String alergia) {
        this.id = id;
        this.nome = nome;
        this.anoNasc = anoNasc;
        this.sexo = sexo;
        this.id_especie = id_especie;
        this.id_cliente = id_cliente;
        this.peso = peso;
        this.alergia = alergia;
    }


    //metodos getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getAnoNasc() {
        return anoNasc;
    }

    public String getSexo() {
        return sexo;
    }
    
    public int getIdEspecie(){
        return id_especie;
    }
    
    public int getIdCliente(){
        return id_cliente;
    }
    
    public Double getPeso(){
        return peso;
    }
    
    public String getAlergia(){
        return alergia;
    }


    //metodos setters
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setAnoNasc(int anoNasc) {
        this.anoNasc = anoNasc;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setEspecie(int id_especie){
        this.id_especie = id_especie;
    }
   
    public void setIdCliente(int id_cliente){
        this.id_cliente = id_cliente;
    }
    
    public void setPeso(Double peso){
        this.peso = peso;
    }
    
    public void setAlergia(String alergia){
        this.alergia = alergia;
    }


    @Override
    public String toString() {
        return nome;
}

}