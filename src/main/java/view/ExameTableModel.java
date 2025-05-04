/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.text.SimpleDateFormat;
import java.util.List;
import model.Animal;
import model.AnimalDAO;
import model.Cliente;
import model.ClienteDAO;
import model.Consulta;
import model.ConsultaDAO;
import model.Exame;
import model.ExameDAO;

/**
 *
 * @author karen
 */
public class ExameTableModel extends GenericTableModel{
    
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ExameTableModel(List<Exame> exames) {
        super(exames, new String[]{"Data da Consulta","Cliente", "Nome do Exame", "Feito", "Resultado", "Diagnóstico"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return String.class; // Data da Consulta como String
            case 1: return String.class;
            case 2: return String.class; // Nome do Exame
            case 3: return Boolean.class; // Status
            case 4: return String.class; // Resultado
            case 5: return String.class; // Diagnóstico
            default: throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Exame exame = (Exame) vDados.get(rowIndex);
        Consulta consulta = ConsultaDAO.getInstance().retrieveById(exame.getIdConsulta());

        switch (columnIndex) {
            case 0: 
                // Verifica se a data da consulta é null antes de formatá-la
                return (consulta != null && consulta.getData() != null) 
                        ? dateFormat.format(consulta.getData().getTime()) 
                        : "Data não disponível";
            case 1:
                if (consulta != null) {
                int idAnimal = consulta.getIdAnimal();
                Animal animal = AnimalDAO.getInstance().retrieveById(idAnimal);
                if (animal != null) {
                    Cliente cliente = ClienteDAO.getInstance().retrieveById(animal.getIdCliente());
                    return cliente != null ? cliente.getNome() : "Cliente não encontrado";
                }
            }
            return "Informação não disponível";
            case 2: 
                return exame.getNome();
            case 3: 
                return exame.getStatus();
            case 4: 
                return exame.getResultado();
            case 5: 
                return exame.getDiagnostico();
            default: 
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Exame exame = (Exame) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                break;
            case 1: 
                break;
            case 2:
                exame.setNome((String) aValue);
                break;
            case 3:
                exame.setStatus((Boolean)aValue);
                break;
            case 4:
                exame.setResultado((String) aValue);
                break;
            case 5:
                exame.setDiagnostico((String) aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }

        ExameDAO.getInstance().update(exame);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Permite edição nos campos Nome do Exame, Status, Resultado e Diagnóstico, mas não na Data da Consulta
        return columnIndex != 0;
    }
    
}
