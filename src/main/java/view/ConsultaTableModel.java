/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Animal;
import model.AnimalDAO;
import model.Cliente;
import model.ClienteDAO;
import model.Consulta;
import model.ConsultaDAO;
import model.Exame;
import model.ExameDAO;
import model.Pagamento;
import model.PagamentoDAO;
import model.Veterinario;
import model.VeterinarioDAO;

/**
 *
 * @author karen
 */
public class ConsultaTableModel extends GenericTableModel {
    
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    public ConsultaTableModel(List vDados){
        super(vDados, new String[]{"Data", "Horário", "Cliente", "Animal", "Veterinário", "Obs", "Fim"});
    }

    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return String.class; // Data como String
            case 1: return String.class; // Horário numérico
            case 2: return String.class; // Cliente como String
            case 3: return String.class; // Animal como String
            case 4: return String.class; // Veterinário como String
            case 5: return String.class; // Observação como String
            case 6: return Boolean.class; // Fim como String
            default: throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }


   @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Consulta consulta = (Consulta) vDados.get(rowIndex);
    
        if (consulta == null) {
            return ""; // Valor padrão para evitar erro
        }
            
    Animal animal = AnimalDAO.getInstance().retrieveById(consulta.getIdAnimal());
    Cliente cliente = animal != null ? ClienteDAO.getInstance().retrieveById(animal.getIdCliente()) : null;
    Veterinario veterinario = VeterinarioDAO.getInstance().retrieveById(consulta.getIdVeterinario());
    
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
       
    switch (columnIndex) {
        case 0: 
            // Exibe vazio quando a data é marcada como inativa
            return (consulta.getData() != null && !consulta.isDataInativa()) 
                    ? dateFormat.format(consulta.getData().getTime()) 
                    : "";
            
        case 1: 
            return (consulta.getHorario() != null) ? timeFormat.format(consulta.getHorario().getTime()) : "00:00";
            
        case 2: 
            return cliente != null ? cliente.getNome() : "Cliente não encontrado";
            
        case 3: 
            return animal != null ? animal.getNome() : "Animal não encontrado";
            
        case 4: 
            return veterinario != null ? veterinario.getNome() : "Veterinário não encontrado";
            
        case 5: 
            return consulta.getComentario();
            
        case 6: 
            return consulta.isTerminou();
            
        default: 
            throw new IndexOutOfBoundsException("columnIndex out of bounds");
    }
}

    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Consulta consulta = (Consulta) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                Calendar c = Calendar.getInstance();
                try {
                   c.setTime(dateFormat.parse((String)aValue));
                } catch (ParseException ex) {
                    Logger.getLogger(ConsultaTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                consulta.setData(c);
                break;
        case 1:
    try {
        String horarioStr = (String) aValue;
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Date parsedTime = new java.sql.Time(timeFormat.parse(horarioStr).getTime());

        Calendar horario = Calendar.getInstance();
        horario.setTime(parsedTime);

        consulta.setHorario(horario);
    } catch (Exception ex) {
        Logger.getLogger(ConsultaTableModel.class.getName()).log(Level.SEVERE, "Erro ao definir o horário", ex);
    }
    break;
            case 2:   
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                consulta.setComentario((String)aValue);
                break;
            case 6:
            // Converte "Finalizada" para true e "Em andamento" para false
            consulta.setTerminou((Boolean)aValue);
            break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        ConsultaDAO.getInstance().update(consulta);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if((columnIndex <= 2) || (columnIndex > 4)) {
            return true;
        } else {
            return false;
        }
    }      

    
    
}
