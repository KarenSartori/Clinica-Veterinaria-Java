/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import model.Animal;
import model.AnimalDAO;
import model.Cliente;
import model.ClienteDAO;
import model.Consulta;
import model.ConsultaDAO;
import model.Pagamento;
import model.PagamentoDAO;

/**
 *
 * @author karen
 */
public class PagamentoTableModel extends GenericTableModel{
    
        public PagamentoTableModel(List vDados){
        super(vDados, new String[]{"Data Consulta", "Cliente","Animal", "Valor", "Forma de pagamento", "Pago"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class; // Data como String
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Double.class;   
            case 4:
                return String.class;
            case 5:
                return Boolean.class;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

   
    
    @Override
public Object getValueAt(int rowIndex, int columnIndex) {
    Pagamento pagamento = (Pagamento) vDados.get(rowIndex);
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    Consulta consulta = ConsultaDAO.getInstance().retrieveById(pagamento.getIdConsulta());
    String dataConsulta = (consulta != null && consulta.getData() != null) 
                          ? dateFormat.format(consulta.getData().getTime()) 
                          : "Data não encontrada";

    Cliente cliente = null;
    Animal animal = null;
    if (consulta != null) {
        animal = AnimalDAO.getInstance().retrieveById(consulta.getIdAnimal());
        if (animal != null) {
            cliente = ClienteDAO.getInstance().retrieveById(animal.getIdCliente());
        }
    }

    switch (columnIndex) {
        case 0:
            return dataConsulta;
        case 1:
            return (cliente != null) ? cliente.getNome() : "Cliente não encontrado";
        case 2:
            return (animal != null) ? animal.getNome() : "Animal não encontrado";
        case 3:
            return pagamento.getValorConsulta();
        case 4:
            return pagamento.getFormaPagamento();
        case 5:
            return pagamento.getConsultaPaga();
        default:
            throw new IndexOutOfBoundsException("columnIndex out of bounds");
    }
}

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Pagamento pagamento = (Pagamento) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                pagamento.setValorConsulta((Double)aValue);  
                break;
        case 3: // Coluna 'valor'
            try {
                double valor = (aValue instanceof Double) ? (Double) aValue : Double.parseDouble(aValue.toString());
                pagamento.setValorConsulta(valor);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Valor inválido. Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            break;
        case 4: // Coluna 'forma de pagamento'
            pagamento.setFormaPagamento(aValue.toString());
            break;
        case 5: // Coluna 'pago'
            pagamento.setConsultaPaga((Boolean) aValue);
            break;
        default:
            throw new IndexOutOfBoundsException("columnIndex out of bounds");
    }
        
            PagamentoDAO.getInstance().update(pagamento);
            fireTableCellUpdated(rowIndex, columnIndex);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //if (columnIndex == 0) return false;
        return true;
    }      
    
}
