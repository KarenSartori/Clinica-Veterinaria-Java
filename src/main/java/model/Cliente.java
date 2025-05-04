/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Karen
 */
public class Cliente {
    //atributos
    private int id;
    private String nome;
    private String endereco;
    private String cep;
    private String email;
        private String telefone;
    

    //construtor 
    public Cliente(int id, String nome, String endereco, String cep, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.cep = cep;
        this.email = email;
        this.telefone = telefone;
    }

    //metodos getters
    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCep() {
        return cep;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }


    //metodos setters (para alterar atributos)
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setEmail(String email) {
        if(!email.equals("")) {  //email nao pode ser vazio
            this.email = email;
            }
    }

    
    @Override
    public String toString() {        
        String desc = nome;
        return desc;
    }   

}
