/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Karen
 */
public class Veterinario {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String especialidade;
    private String crmv;
    

    //construtor
    public Veterinario(int id, String nome, String email, String telefone, String especialidade, String crmv) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.especialidade = especialidade;
        this.crmv = crmv;
    }

    //getters
    
    public int getId(){
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }
    
    public String getEspecialidade(){
        return especialidade;
    }
    
    public String getCrmv(){
        return crmv;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public void setEspecialidade(String especialidade){
        this.especialidade = especialidade;
    }
    
    public void setCrmv(String crmv){
        this.crmv = crmv;
    }

    @Override
    public String toString() {
        return nome;
}

}