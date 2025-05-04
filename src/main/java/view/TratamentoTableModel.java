package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import model.Animal;
import model.AnimalDAO;
import model.Cliente;
import model.ClienteDAO;
import model.Consulta;
import model.ConsultaDAO;
import model.Tratamento;
import model.TratamentoDAO;
import model.Veterinario;
import model.VeterinarioDAO;

public class TratamentoTableModel extends GenericTableModel {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public TratamentoTableModel(List<Tratamento> tratamentos) {
        super(tratamentos, new String[]{"Data Início", "Data Fim", "Cliente", "Animal", "Tratamento Indicado", "Finalizado"});
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return String.class;
            case 1: return String.class; // Datas como String
            case 2: return String.class;
            case 3: return String.class; // Animal
            case 4: return String.class; // Tratamento Indicado
            case 5: return Boolean.class; // Finalizado
            default: throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tratamento tratamento = (Tratamento) vDados.get(rowIndex);
        Animal animal = AnimalDAO.getInstance().retrieveById(tratamento.getIdAnimal());
        Cliente cliente = animal != null ? ClienteDAO.getInstance().retrieveById(animal.getIdCliente()) : null;
  
    if (tratamento == null) {
        return ""; // Ou qualquer valor padrão
    }

        switch (columnIndex) {
            case 0: return dateFormat.format(tratamento.getDataInicio().getTime());
            case 1: return (tratamento.getDataFim() != null) ? dateFormat.format(tratamento.getDataFim().getTime()) : "Indefinido";
            case 2: return cliente != null ? cliente.getNome() : "Cliente não encontrado";
            case 3: return animal != null ? animal.getNome() : "Animal não encontrado";
            case 4: return tratamento.getTratamentoIndicado();
            case 5: return tratamento.isTerminou();
            default: throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Tratamento tratamento = (Tratamento) vDados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                try {
                    Calendar dataInicio = Calendar.getInstance();
                    dataInicio.setTime(dateFormat.parse((String) aValue));
                    tratamento.setDataInicio(dataInicio);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    Calendar dataFim = Calendar.getInstance();
                    dataFim.setTime(dateFormat.parse((String) aValue));
                    tratamento.setDataFim(dataFim);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                break;
            case 3: 
                break;
            case 4:
                tratamento.setTratamentoIndicado((String) aValue);
                break;
            case 5:
                tratamento.setTerminou((Boolean) aValue);
                break;
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
        
        TratamentoDAO.getInstance().update(tratamento);
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 2 && columnIndex != 3;
    }
}

