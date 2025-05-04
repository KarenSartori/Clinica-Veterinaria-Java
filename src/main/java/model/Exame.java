/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Karen
 */
public class Exame {
    
    private int id;
    private String nome;
    private int id_consulta;
    private Boolean status;
    private String resultado;
    private String diagnostico;
                                                      
    //construtor
    public Exame(int id, String nome, int id_consulta, Boolean status, String resultado, String diagnostico) {
        this.id = id;
        this.nome = nome;
        this.id_consulta = id_consulta;
        this.status = status;
        this.resultado = resultado;
        this.diagnostico = diagnostico;
    }


    //getters
    public int getId(){
        return id;
    }
    
    public String getNome() {
        return nome;
    }

    public int getIdConsulta() {
        return id_consulta;
    }
    
    public Boolean getStatus(){
        return status;
    }
    
    public String getResultado(){
        return resultado;
    }
    
    public String getDiagnostico(){
        return diagnostico;
    }

    
    //setters
    public void setId(int id){
        this.id = id;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdConsulta(int id_consulta) {
        this.id_consulta = id_consulta;
    }
    
    public void setStatus(Boolean status){
        this.status = status;
    }
    
    public void setResultado(String resultado){
        this.resultado = resultado;
    }
    
    public void setDiagnostico(String diagnostico){
        this.diagnostico = diagnostico;
    }
    
    @Override
    public String toString() {
        return "Exame{" +
            "nome='" + nome + '\'' +
            ", idConsulta=" + id_consulta +
            ", status=" + status + 
            ", resultado=" + resultado +   
            ", diagnostico=" + diagnostico +                
            '}';
}


    
}