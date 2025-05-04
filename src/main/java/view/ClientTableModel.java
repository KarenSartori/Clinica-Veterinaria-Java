package view;

import java.util.List;
import model.Cliente;
import model.ClienteDAO;

/**
 *
 * @author Prof. Dr. Plinio Vilela - prvilela@unicamp.br
 */
public class ClientTableModel extends GenericTableModel {

    public ClientTableModel(List vDados){
        super(vDados, new String[]{"Nome","Endereço", "Cep", "Email", "Telefone"});
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
        Cliente cliente = (Cliente) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return cliente.getNome();
            case 1:
                return cliente.getEndereco();
            case 2:
                return cliente.getCep();
            case 3:
                return cliente.getEmail();
            case 4:
                return cliente.getTelefone();                
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }    
    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Cliente cliente = (Cliente) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                cliente.setNome((String)aValue);
                break;
            case 1:
                cliente.setEndereco((String)aValue);
                break;
            case 2:
                cliente.setCep((String)aValue);    
                break;
            case 3:
                cliente.setEmail((String)aValue);
                break;
            case 4:
                cliente.setTelefone((String)aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        ClienteDAO.getInstance().update(cliente);
    }    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //if (columnIndex == 0) return false;
        return true;
    }      
    
}
