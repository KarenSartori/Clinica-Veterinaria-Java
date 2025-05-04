/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
/**
 *
 * @author Karen
 */

public class Consulta {
    
    private int id;
    private Calendar data;
    private Calendar horario;
    private String comentario;
    private int id_animal;
    private int id_veterinario;
    private int idTratamento;
    private int id_pagamento;
    private boolean terminou;
    private boolean isDataInativa = false;
    private boolean exameConcluido;

    //construtor
    public Consulta(int id, Calendar data, Calendar horario, String comentario, int id_animal, int id_veterinario, int idTratamento, int id_pagamento, boolean terminou) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.comentario = comentario;
        this.id_animal = id_animal;
        this.id_veterinario = id_veterinario;
        this.idTratamento = idTratamento;
        this.id_pagamento = id_pagamento;
        this.terminou = terminou;
    }

    //getters
    
    public int getId(){
        return id;
    }
    
    public Calendar getData() {
        return data;
    }

    public Calendar getHorario() {
        return horario;
    }

    public String getComentario() {
        return comentario;
    }

    public int getIdAnimal() {
        return id_animal;
    }

    public int getIdVeterinario() {
        return id_veterinario;
    }

    public int getIdTratamento() {
        return idTratamento;
    }
    
    public int getIdPagamento(){
        return id_pagamento;
    }

    public boolean isTerminou() {
        return terminou;
    }

    public boolean isDataInativa() {
        return isDataInativa;
    }
    
    public boolean isExameConcluido() {
        return exameConcluido;
    }

    public void setExameConcluido(boolean exameConcluido) {
        this.exameConcluido = exameConcluido;
    }


    //setters
    public void setId(int id){
        this.id = id;
    }
    
    public void setData(Calendar data) {
        this.data = data;
    }

    public void setHorario(Calendar horario) {
        this.horario = horario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public void setIdAnimal(int id_animal) {
        this.id_animal = id_animal;
    }

    public void setIdVeterinario(int id_veterinario) {
        this.id_veterinario = id_veterinario;
    }

    public void setIdTratamento(int idTratamento) {
        this.idTratamento = idTratamento;
    }
    
    public void setIdPagamento(int id_pagamento){
        this.id_pagamento = id_pagamento;
    }

    public void setTerminou(boolean terminou) {
        this.terminou = terminou;
    }
    
    public void setDataInativa(boolean isDataInativa) {
        this.isDataInativa = isDataInativa;
    }
    
    
@Override
public String toString() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    LocalDateTime dataConsulta = LocalDateTime.ofInstant(data.toInstant(), ZoneId.systemDefault());

    return dtf.format(dataConsulta);

}

}
