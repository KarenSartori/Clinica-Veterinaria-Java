/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.util.List;
import model.*;

/**
 *
 * @author karen
 */
public class AnimalTableModel extends GenericTableModel {
    
    
    public AnimalTableModel(List vDados){
        super(vDados, new String[]{"Nome","Ano de Nascimento", "Sexo", "Espécie", "Peso", "Alergia"});
    }

    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return Double.class;  
            case 5:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Animal animal = (Animal) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return animal.getNome();
            case 1:
                return animal.getAnoNasc();
            case 2:
                return animal.getSexo();
            case 3:
                Especie especie = EspecieDAO.getInstance().retrieveById(animal.getIdEspecie());
                if(especie != null){
                    return especie.getNome();
                }
                return ("");
            case 4:
                return animal.getPeso();   
            case 5:
                return animal.getAlergia();
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }    
    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Animal animal = (Animal) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                animal.setNome((String)aValue);
                break;
            case 1:
                animal.setAnoNasc((Integer)aValue);
                break;
            case 2:
                animal.setSexo((String)aValue);    
                break;
            case 3:
                animal.setEspecie((Integer)aValue);
                break;
            case 4:
                animal.setPeso((Double) aValue);
                break;
            case 5:
                animal.setAlergia((String)aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        AnimalDAO.getInstance().update(animal);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //if (columnIndex == 0) return false;
        return true;
    }      
    
    
}
