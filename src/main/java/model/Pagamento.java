/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author karen
 */
public class Pagamento {
    
    private int id;
    private Double valorConsulta;
    private String formaPagamento;
    private boolean consultaPaga;
    private int id_consulta;
    
    public Pagamento(int id, Double valorConsulta, String formaPagamento, boolean consultaPaga, int id_consulta) {
        this.id = id;
        this.valorConsulta = valorConsulta;
        this.formaPagamento = formaPagamento;
        this.consultaPaga = consultaPaga;
        this.id_consulta = id_consulta;
    }
    
    //getters
    public int getId(){
        return id;
    }
    
    public Double getValorConsulta(){
        return valorConsulta;
    }
    
    public String getFormaPagamento(){
        return formaPagamento;
    }
    
    public boolean getConsultaPaga(){
        return consultaPaga;
    }
    
    public int getIdConsulta(){
        return id_consulta;
    }
    
    
    //setters
    public void setId(int id){
        this.id = id;
    }
    
    public void setValorConsulta(Double valorConsulta){
        this.valorConsulta = valorConsulta;
    }
    
    public void setFormaPagamento(String formaPagamento){
        this.formaPagamento = formaPagamento;
    }
    
    public void setConsultaPaga(boolean consultaPaga){
        this.consultaPaga = consultaPaga;
    }
    
    @Override
    public String toString() {
    return "Pagamento{" +
            "valorConsulta=" + valorConsulta +
            ", formaPagamento='" + formaPagamento + '\'' +
            ", consultaPaga=" + consultaPaga +
            ", id_consulta=" + id_consulta +            
            '}';
    }

}
