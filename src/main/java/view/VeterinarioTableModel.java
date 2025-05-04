/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.List;
import model.Veterinario;
import model.VeterinarioDAO;

/**
 *
 * @author karen
 */
public class VeterinarioTableModel extends GenericTableModel {
    
    public VeterinarioTableModel(List vDados){
        super(vDados, new String[]{"Nome","Email", "Telefone", "Especialidade", "CRMV"});
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return String.class;   
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Veterinario veterinario = (Veterinario) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return veterinario.getNome();
            case 1:
                return veterinario.getEmail();
            case 2:
                return veterinario.getTelefone();
            case 3:
                return veterinario.getEspecialidade();
            case 4:
                return veterinario.getCrmv();                
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }    
    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Veterinario veterinario = (Veterinario) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                veterinario.setNome((String)aValue);
                break;
            case 1:
                veterinario.setEmail((String)aValue);
                break;
            case 2:
                veterinario.setTelefone((String)aValue);    
                break;
            case 3:
                veterinario.setEspecialidade((String)aValue);
                break;
            case 4:
                veterinario.setCrmv((String)aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        VeterinarioDAO.getInstance().update(veterinario);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //if (columnIndex == 0) return false;
        return true;
    }      
}
