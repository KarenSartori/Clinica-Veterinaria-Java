/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import model.Animal;
import model.AnimalDAO;
import model.Cliente;
import model.ClienteDAO;
import model.Consulta;
import model.ConsultaDAO;
import model.Especie;
import model.EspecieDAO;
import model.Exame;
import model.ExameDAO;
import model.Pagamento;
import model.PagamentoDAO;
import model.Tratamento;
import model.TratamentoDAO;
import model.Veterinario;
import model.VeterinarioDAO;
import view.AnimalTableModel;
import view.ClientTableModel;
import view.ConsultaTableModel;
import view.EspecieTableModel;
import view.ExameTableModel;
import view.GenericTableModel;
import view.PagamentoTableModel;
import view.TratamentoTableModel;
import view.VeterinarioTableModel;

/**
 *
 * @author karen
 */
public class Controller {
    
    private static Cliente clienteSelecionado = null;
    private static Animal animalSelecionado = null;
    private static Veterinario veterinarioSelecionado = null;
    private static JTextField clienteSelecionadoTextField = null;
    private static JTextField animalSelecionadoTextField = null;
    private static JTextField veterinarioSelecionadoTextField = null;
    
    public static void setTextFields(JTextField cliente, JTextField animal){
        clienteSelecionadoTextField = cliente;
        animalSelecionadoTextField = animal;
    }
            
    public static void setTableModel(JTable table, GenericTableModel tableModel) {
        table.setModel(tableModel);
    }
    
    public static Cliente getClienteSelecionado(){
        return clienteSelecionado;
    }
    
    public static Animal getAnimalSelecionado(){
        return animalSelecionado;
    }
    
    public static Veterinario getVeterinarioSelecionado(){
        return veterinarioSelecionado;
    }
    
    public static List<Animal> getAnimaisDoClienteSelecionado() {
        if (clienteSelecionado != null) {
            return AnimalDAO.getInstance().retrieveByClienteId(clienteSelecionado.getId());
        }
        return new ArrayList<>();
    }

    
    public static void setSelected(Object selected) {
    if (selected instanceof Cliente) {
        clienteSelecionado = (Cliente) selected;
        if (clienteSelecionadoTextField != null) {
            clienteSelecionadoTextField.setText(clienteSelecionado.getNome());
        }
    } else if (selected instanceof Animal) {
        animalSelecionado = (Animal) selected;
        if (animalSelecionadoTextField != null) {
            animalSelecionadoTextField.setText(animalSelecionado.getNome());
        }
    } else if (selected instanceof Veterinario) {
        veterinarioSelecionado = (Veterinario) selected;
        if (veterinarioSelecionadoTextField != null) {
            veterinarioSelecionadoTextField.setText(veterinarioSelecionado.getNome());
        }
    }
}

    public static List getTodosClientes(){
        return ClienteDAO.getInstance().retrieveAll();
    }
    
    public static List getTodosAnimais(){
        return AnimalDAO.getInstance().retrieveAll();
    }
    
    public static List getTodosVeterinarios(){
        return VeterinarioDAO.getInstance().retrieveAll();
    }
    
    public static List getTodasConsultas(){
        return ConsultaDAO.getInstance().retrieveAll();
    }
    
    public static List getTodosPagamentos(){
        return PagamentoDAO.getInstance().retrieveAll();
    }
    
    public static List getTodosTratamentos(){
        return TratamentoDAO.getInstance().retrieveAll();
    }

    public static void jRadioButtonClientesSelecionado(JTable table){
        setTableModel(table, new ClientTableModel(ClienteDAO.getInstance().retrieveAll()));
    }

    
    public static boolean jRadioButtonAnimaisSelecionado(JTable table){
        if(getClienteSelecionado() != null){
            setTableModel(table, new AnimalTableModel(AnimalDAO.getInstance().retrieveByClienteId(Controller.getClienteSelecionado().getId())));
            return true;
        } else {
            setTableModel(table, new AnimalTableModel(new ArrayList()));
            return false;
        } 
    }
    
    public static void jRadioButtonEspeciesSelecionado(JTable table){
        setTableModel(table, new EspecieTableModel(EspecieDAO.getInstance().retrieveAll()));
    }
    
    public static void jRadioButtonVeterinariosSelecionado(JTable table){
        setTableModel(table, new VeterinarioTableModel(VeterinarioDAO.getInstance().retrieveAll()));
    }
    
    //atualiza uma lista cm nomes parecidos
    public static void atualizaNomeParecido(JTable table, String nomeParecido){
        if(table.getModel() instanceof ClientTableModel) {
            ((GenericTableModel)table.getModel()).addListOfItems(ClienteDAO.getInstance().retrieveBySimilarName(nomeParecido));
        } else if (table.getModel() instanceof VeterinarioTableModel){
            ((GenericTableModel)table.getModel()).addListOfItems(VeterinarioDAO.getInstance().retrieveBySimilarName(nomeParecido));
        } else if (table.getModel() instanceof AnimalTableModel) {
            if(getClienteSelecionado() != null){
                ((GenericTableModel)table.getModel()).addListOfItems(AnimalDAO.getInstance().retrieveBySimilarName(getClienteSelecionado().getId(), nomeParecido));
            }
        } else if (table.getModel() instanceof EspecieTableModel) {
            ((GenericTableModel)table.getModel()).addListOfItems(EspecieDAO.getInstance().retrieveBySimilarName(nomeParecido));
        }
    }
    
    //botao todos, reseta a lista e limpa o campo de busca
    public static void atualizaBotaoTodos(JTable table, JTextField textField){
        if(table.getModel() instanceof ClientTableModel) {
            ((GenericTableModel)table.getModel()).addListOfItems(Controller.getTodosClientes());
        } else if (table.getModel() instanceof VeterinarioTableModel) {
            ((GenericTableModel)table.getModel()).addListOfItems(VeterinarioDAO.getInstance().retrieveAll());
        } else if (table.getModel() instanceof AnimalTableModel){
            if(getClienteSelecionado() != null){
                ((GenericTableModel)table.getModel()).addListOfItems(AnimalDAO.getInstance().retrieveByClienteId(getClienteSelecionado().getId()));
            }
        } else if (table.getModel() instanceof EspecieTableModel){
            ((GenericTableModel)table.getModel()).addListOfItems(EspecieDAO.getInstance().retrieveAll());
        }
        textField.setText("");
    }
   
    
    public static void atualizaBotaoApaga(JTable table){
        if(table.getSelectedRow() >= 0) {
            Object item = ((GenericTableModel)table.getModel()).getItem(table.getSelectedRow());
            ((GenericTableModel)table.getModel()).removeItem(table.getSelectedRow());
            if(table.getModel() instanceof ClientTableModel) {
                apagaCliente(getClienteSelecionado());
            } else if (table.getModel() instanceof EspecieTableModel) {
                apagaAnimal(getAnimalSelecionado());
            } else if (table.getModel() instanceof EspecieTableModel) {
                EspecieDAO.getInstance().delete((Especie) item);
            } else if (table.getModel() instanceof VeterinarioTableModel) {
                VeterinarioDAO.getInstance().delete((Veterinario)item);
            }
        }
    }
    
public static boolean apagaConsulta(JTable table) {
    if(table.getSelectedRow() >= 0) {
        Object item = ((GenericTableModel) table.getModel()).getItem(table.getSelectedRow());
        ((GenericTableModel)table.getModel()).removeItem(table.getSelectedRow());
        ConsultaDAO.getInstance().delete((Consulta)item);
        return true;
    }
    return false;
}
                      
public static Cliente adicionaCliente(){
    return ClienteDAO.getInstance().create("", "", "", "", "");
}
    
public static Veterinario adicionaVeterinario(){
    return VeterinarioDAO.getInstance().create("", "", "", "", "");
}
    
public static Especie adicionaEspecie(){
    return EspecieDAO.getInstance().create("");
}
    
public static Consulta adicionaConsulta(){
    return ConsultaDAO.getInstance().create(Calendar.getInstance(), Calendar.getInstance(), "", animalSelecionado.getId(), veterinarioSelecionado.getId(), 0, 0, false);
}
    
public static Pagamento adicionaPagamento(){
    return PagamentoDAO.getInstance().create(0.00,"", false, animalSelecionado.getId());
}
    
public static void filtraConsultas(JTable table, boolean todas, boolean hoje, boolean veterinario) {
    String where = "";

    if (todas) {
        where = ""; 
    } else if (hoje) {
        // Filtra consultas para o dia atual usando CURRENT_DATE
        where = "WHERE data = CURRENT_DATE";
    } else if (veterinario && veterinarioSelecionado != null) {
        where = "WHERE idVeterinario = " + veterinarioSelecionado.getId();
    }

    String query = "SELECT * FROM consulta " + where + " ORDER BY data, horario";
    ((GenericTableModel) table.getModel()).addListOfItems(ConsultaDAO.getInstance().retrieve(query));
}
 
/*public static void apagaCliente(Cliente cliente) {
    if (cliente == null) {
        JOptionPane.showMessageDialog(null, "Nenhum cliente selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    List<Animal> animais = AnimalDAO.getInstance().retrieveByClienteId(cliente.getId());

    for (Animal animal : animais) {
        apagaAnimal(animal);
    }
    
    ClienteDAO.getInstance().delete(cliente);

    JOptionPane.showMessageDialog(null, "Cliente e suas dependências apagados com sucesso.");
}
 
public static void apagaAnimal(Animal animal) {
    if (animal == null) {
        JOptionPane.showMessageDialog(null, "Nenhum animal selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Apagar consultas do animal
    List<Consulta> consultas = ConsultaDAO.getInstance().retrieveByAnimalId(animal.getId());
    for (Consulta consulta : consultas) {
        // Apagar exames associados à consulta
        List<Exame> exames = ExameDAO.getInstance().retrieveByIdConsulta(consulta.getId());
        for (Exame exame : exames) {
            ExameDAO.getInstance().delete(exame);
        }

        // Apagar pagamentos associados à consulta
        if (consulta.getIdPagamento() != 0) {
            Pagamento pagamento = PagamentoDAO.getInstance().retrieveById(consulta.getIdPagamento());
            if (pagamento != null) {
                PagamentoDAO.getInstance().delete(pagamento);
            }
        }

        // Apagar a consulta
        ConsultaDAO.getInstance().delete(consulta);
    }

    // Apagar tratamentos do animal
    List<Tratamento> tratamentos = TratamentoDAO.getInstance().retrieveByAnimalId(animal.getId());
    for (Tratamento tratamento : tratamentos) {
        TratamentoDAO.getInstance().delete(tratamento);
    }

    // Por fim, apague o animal
    AnimalDAO.getInstance().delete(animal);

    JOptionPane.showMessageDialog(null, "Animal e suas dependências apagados com sucesso.");
}*/

public static void apagaCliente(Cliente cliente) {
    if (cliente == null) {
        JOptionPane.showMessageDialog(null, "Nenhum cliente selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    List<Animal> animais = AnimalDAO.getInstance().retrieveByClienteId(cliente.getId());

    if (!animais.isEmpty()) {
        for (Animal animal : animais) {
            apagaAnimal(animal);
        }
        JOptionPane.showMessageDialog(null, "Cliente e suas dependências apagados com sucesso.");
    } else {
        JOptionPane.showMessageDialog(null, "Cliente apagado com sucesso.");
    }
    
    ClienteDAO.getInstance().delete(cliente);
}

public static void apagaAnimal(Animal animal) {
    if (animal == null) {
        JOptionPane.showMessageDialog(null, "Nenhum animal selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        // Apagar todas as consultas associadas ao animal
        List<Consulta> consultas = ConsultaDAO.getInstance().retrieveByAnimalId(animal.getId());
        for (Consulta consulta : consultas) {
            // Apagar todos os exames da consulta
            List<Exame> exames = ExameDAO.getInstance().retrieveByIdConsulta(consulta.getId());
            for (Exame exame : exames) {
                ExameDAO.getInstance().delete(exame);
            }

            // Apagar o pagamento associado, se existir
            if (consulta.getIdPagamento() != 0) {
                Pagamento pagamento = PagamentoDAO.getInstance().retrieveById(consulta.getIdPagamento());
                if (pagamento != null) {
                    PagamentoDAO.getInstance().delete(pagamento);
                }
            }

            // Apagar a consulta
            ConsultaDAO.getInstance().delete(consulta);
        }

        // Apagar todos os tratamentos associados ao animal
        List<Tratamento> tratamentos = TratamentoDAO.getInstance().retrieveByAnimalId(animal.getId());
        for (Tratamento tratamento : tratamentos) {
            TratamentoDAO.getInstance().delete(tratamento);
        }

        // Apagar o animal
        AnimalDAO.getInstance().delete(animal);

        JOptionPane.showMessageDialog(null, "Animal e todas as suas dependências apagados com sucesso.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, 
            "Erro ao apagar o animal e suas dependências: " + e.getMessage(), 
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
}

 
public static void apagaVeterinario(Veterinario veterinario){
    VeterinarioDAO.getInstance().delete(veterinario);
}
    
public static void apagaEspecieSelecionada(JTable table) {
    int selectedRow = table.getSelectedRow();
    if (selectedRow >= 0) {
        Especie especie = (Especie) ((EspecieTableModel) table.getModel()).getItem(selectedRow);
        
        int confirm = JOptionPane.showOptionDialog(null, 
                "Tem certeza de que deseja apagar esta espécie?", 
                "Confirmação de Exclusão", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, new Object[]{"Sim", "Não"}, "Sim");

        
        if (confirm == JOptionPane.YES_OPTION) {
            EspecieDAO.getInstance().delete(especie);
            ((EspecieTableModel) table.getModel()).removeItem(selectedRow);
            JOptionPane.showMessageDialog(null, "Espécie apagada com sucesso.");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Selecione uma espécie para apagar.");
    }
}
  
public static Animal adicionaAnimalAoClienteSelecionado() {
        return AnimalDAO.getInstance().create("", 0, "", 2, clienteSelecionado, 0.00, "");
}   
   
public static boolean atualizaBotaoNovo(JTable table){
    if(table.getModel() instanceof ClientTableModel){
        ((GenericTableModel)table.getModel()).addItem(adicionaCliente());
    } else if (table.getModel() instanceof AnimalTableModel) {
        ((GenericTableModel)table.getModel()).addItem(adicionaAnimalAoClienteSelecionado());
    } else if (table.getModel() instanceof EspecieTableModel) {
        ((GenericTableModel)table.getModel()).addItem(adicionaEspecie());
    } else if (table.getModel() instanceof VeterinarioTableModel) {
        ((GenericTableModel)table.getModel()).addItem(adicionaVeterinario());
    } else if (table.getModel() instanceof ConsultaTableModel ){
        if((clienteSelecionado != null ) && (animalSelecionado != null) && (veterinarioSelecionado != null)){
            ((GenericTableModel)table.getModel()).addItem(adicionaConsulta());
        } else {
            return false;
        }
    } 
    return true;
}

public static void atualizarConsultasDoClienteSelecionado(JTable consultaTable) {
    if (clienteSelecionado != null) {
        List<Animal> animaisDoCliente = AnimalDAO.getInstance().retrieveByClienteId(clienteSelecionado.getId());
        List<Consulta> consultas = new ArrayList<>();

        for (Animal animal : animaisDoCliente) {
            // Adiciona todas as consultas do animal
            consultas.addAll(ConsultaDAO.getInstance().retrieveByAnimalId(animal.getId()));
        }

        ConsultaTableModel consultaModel = new ConsultaTableModel(consultas);
        setTableModel(consultaTable, consultaModel);
        consultaModel.fireTableDataChanged();
    } else {
        // Limpa a tabela de consultas se nenhum cliente está selecionado
        setTableModel(consultaTable, new ConsultaTableModel(new ArrayList<>()));
    }
}
      
public static void atualizarPagamentosDoClienteSelecionado(JTable pagamentosTable) {
    List<Pagamento> pagamentos;

    if (clienteSelecionado != null) {
        pagamentos = PagamentoDAO.getInstance().retrieveByClienteId(clienteSelecionado.getId());
    } else {
        pagamentos = PagamentoDAO.getInstance().retrieveAll();
    }

    // Atualizar o modelo da tabela com os pagamentos apropriados
    PagamentoTableModel modeloPagamento = new PagamentoTableModel(pagamentos);
    setTableModel(pagamentosTable, modeloPagamento);
    modeloPagamento.fireTableDataChanged();
}

public static void atualizarDataConsulta(Consulta consulta, Calendar novaData, JTable pagamentosTable) {
    consulta.setData(novaData);
    ConsultaDAO.getInstance().update(consulta);
    
    // Atualiza a tabela de pagamentos para refletir a nova data
    atualizarPagamentosDoClienteSelecionado(pagamentosTable);
}

    
public static void apagarConsultaSelecionada(JTable table, JTable pagamentosTable, JTable tratamentosTable, JTable examesTable) {
    int selectedRow = table.getSelectedRow();
    if (selectedRow >= 0) {
        // Obter a consulta selecionada
        Consulta consulta = (Consulta) ((ConsultaTableModel) table.getModel()).getItem(selectedRow);

        Object[] options = {"Sim", "Não"};

        // Confirmar exclusão
        int confirm = JOptionPane.showOptionDialog(null,
                "Tem certeza de que deseja apagar esta consulta, incluindo o pagamento, tratamento e exame associados?",
                "Confirmação de Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]); 

        if (confirm == JOptionPane.YES_OPTION) {
            // Apagar o pagamento associado, se existir
            if (consulta.getIdPagamento() != 0) {
                Pagamento pagamento = PagamentoDAO.getInstance().retrieveById(consulta.getIdPagamento());
                if (pagamento != null) {
                    PagamentoDAO.getInstance().delete(pagamento);
                }
            }

            // Apagar o tratamento associados ao animal da consulta
            List<Tratamento> tratamentos = TratamentoDAO.getInstance().retrieveByAnimalId(consulta.getIdAnimal());
            for (Tratamento tratamento : tratamentos) {
                TratamentoDAO.getInstance().delete(tratamento);
            }

            // Apagar o exame associado à consulta
            List<Exame> exames = ExameDAO.getInstance().retrieveByIdConsulta(consulta.getId());
            for (Exame exame : exames) {
                ExameDAO.getInstance().delete(exame);
            }

            // Apagar a consulta do banco de dados
            ConsultaDAO.getInstance().delete(consulta);

            // Remover a linha do modelo da tabela
            ((ConsultaTableModel) table.getModel()).removeItem(selectedRow);

            // Atualizar as tabelas de pagamentos e tratamentos
            atualizarPagamentosDoClienteSelecionado(pagamentosTable);
            atualizarTratamentosDoClienteSelecionado(tratamentosTable);
            atualizarExamesDoClienteSelecionado(examesTable);

            JOptionPane.showMessageDialog(null, "Consulta, pagamento, tratamento(s) e exame(s) associados apagados com sucesso.");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Selecione uma consulta para apagar.");
    }
}

public static void apagarPagamentoSelecionado(JTable pagamentosTable) {
    if (!(pagamentosTable.getModel() instanceof PagamentoTableModel)) {
        JOptionPane.showMessageDialog(null, "Erro: A tabela selecionada não é de pagamentos.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int selectedRow = pagamentosTable.getSelectedRow();
    if (selectedRow < 0) { 
        JOptionPane.showMessageDialog(null, "Selecione um pagamento para apagar.", "Erro", JOptionPane.WARNING_MESSAGE);
        return;
    }

    PagamentoTableModel model = (PagamentoTableModel) pagamentosTable.getModel();
    Pagamento pagamento = (Pagamento) model.getItem(selectedRow);

    int confirm = JOptionPane.showConfirmDialog(null, 
            "Tem certeza de que deseja apagar este pagamento?", 
            "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        PagamentoDAO.getInstance().delete(pagamento);
        model.removeItem(selectedRow);
        JOptionPane.showMessageDialog(null, "Pagamento apagado com sucesso.");
    }
}
    

public static Especie adicionaEspecieComDialogo(JTable table) {
    String nomeEspecie = JOptionPane.showInputDialog(null, "Digite o nome da nova espécie:", "Adicionar Espécie", JOptionPane.PLAIN_MESSAGE);
    
    if (nomeEspecie != null && !nomeEspecie.trim().isEmpty()) {
        // Cria e salva a nova espécie no banco de dados
        Especie novaEspecie = EspecieDAO.getInstance().create(nomeEspecie.trim());
        
        // Atualiza a tabela com a nova espécie
        ((EspecieTableModel) table.getModel()).addItem(novaEspecie);
        return novaEspecie;
    } else if (nomeEspecie != null) {
        JOptionPane.showMessageDialog(null, "Nome da espécie não pode estar vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
    }
    return null;
}

public static void adicionaClienteDialogo() {
    // Variáveis para armazenar os valores persistentes
    String nome = "";
    String endereco = "";
    String cep = "";
    String email = "";
    String telefone = "";

    boolean validInput = false;

    while (!validInput) {
        // Campos de entrada preenchidos com os valores atuais
        JTextField nomeField = new JTextField(nome);
        JTextField enderecoField = new JTextField(endereco);
        JTextField cepField = new JTextField(cep);
        JTextField emailField = new JTextField(email);
        JTextField telefoneField = new JTextField(telefone);

        Object[] message = {
            "Nome:", nomeField,
            "Endereço:", enderecoField,
            "Cep:", cepField,
            "Email:", emailField,
            "Telefone:", telefoneField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Adicionar Novo Cliente", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
            return;
        }

        // Recuperar os valores inseridos
        nome = nomeField.getText().trim();
        endereco = enderecoField.getText().trim();
        cep = cepField.getText().trim();
        email = emailField.getText().trim();
        telefone = telefoneField.getText().trim();

        // Validações dos campos
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo 'Nome' é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            continue; 
        }

        if (!telefone.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(null, "Telefone inválido! Insira um número com 11 dígitos.", "Erro", JOptionPane.ERROR_MESSAGE);
            continue; 
        }

        if (!cep.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(null, "CEP inválido! Insira 8 dígitos.", "Erro", JOptionPane.ERROR_MESSAGE);
            continue; 
        }

        // Se tudo estiver válido, cria o cliente
        Cliente novoCliente = ClienteDAO.getInstance().create(nome, endereco, cep, email, telefone);
        JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");
        validInput = true;
    }
}


/*public static void adicionaClienteDialogo() {
    JTextField nomeField = new JTextField();
    JTextField telefoneField = new JTextField();
    JTextField enderecoField = new JTextField();
    JTextField cepField = new JTextField();
    JTextField emailField = new JTextField();
    

    Object[] message = {
        "Nome:", nomeField,
        "Endereço:", enderecoField,
        "Cep:", cepField,
        "Email:", emailField,
        "Telefone:", telefoneField
        
    };

    int option = JOptionPane.showConfirmDialog(null, message, "Adicionar Novo Cliente", JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
        String nome = nomeField.getText().trim();
        String endereco = enderecoField.getText().trim();
        String cep = cepField.getText().trim();
        String email = emailField.getText().trim();
        String telefone = telefoneField.getText().trim();

        if (!nome.isEmpty()) {
            Cliente novoCliente = ClienteDAO.getInstance().create(nome, endereco, cep, email,telefone);
            JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Nome do cliente é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}*/

public static Animal adicionaAnimalComDialogo() {
    if (clienteSelecionado == null) {
        JOptionPane.showMessageDialog(null, "Selecione um cliente antes de adicionar um animal.", "Erro", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    boolean validInput = false;

    // Variáveis para armazenar valores persistentes
    String nome = "";
    String anoNasc = "";
    String peso = "";
    String alergia = "";
    int especieIndex = -1;

    while (!validInput) {
        JTextField nomeField = new JTextField(nome);
        JTextField anoNascField = new JTextField(anoNasc);
        JComboBox<String> sexoComboBox = new JComboBox<>(new String[] { "Fêmea", "Macho" }); // Opções de sexo
        JTextField pesoField = new JTextField(peso);
        JTextField alergiaField = new JTextField(alergia);

        // Cria um comboBox para selecionar a espécie entre as existentes
        List<Especie> especies = EspecieDAO.getInstance().retrieveAll();
        String[] especiesNomes = especies.stream().map(Especie::getNome).toArray(String[]::new);
        JComboBox<String> especieComboBox = new JComboBox<>(especiesNomes);

        if (especieIndex >= 0) {
            especieComboBox.setSelectedIndex(especieIndex);
        }

        Object[] message = {
            "Nome:", nomeField,
            "Ano de Nascimento:", anoNascField,
            "Sexo:", sexoComboBox,
            "Peso:", pesoField,
            "Alergia:", alergiaField,
            "Espécie:", especieComboBox
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Adicionar Novo Animal", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
            return null; 
        }

        try {
            nome = nomeField.getText().trim();
            anoNasc = anoNascField.getText().trim();
            String sexo = (String) sexoComboBox.getSelectedItem(); 
            peso = pesoField.getText().trim();
            alergia = alergiaField.getText().trim();
            especieIndex = especieComboBox.getSelectedIndex();

            if (!peso.matches("\\d+(\\.\\d+)?")) {
                throw new NumberFormatException("Peso inválido. Insira apenas números.");
            }

            double pesoNumerico = Double.parseDouble(peso);

            if (!nome.isEmpty() && especieIndex >= 0) {
                Especie especieSelecionada = especies.get(especieIndex);
                Animal novoAnimal = AnimalDAO.getInstance().create(
                    nome, Integer.parseInt(anoNasc), sexo, especieSelecionada.getId(), clienteSelecionado, pesoNumerico, alergia
                );
                JOptionPane.showMessageDialog(null, "Animal adicionado com sucesso!");
                validInput = true; 
                return novoAnimal;
            } else {
                JOptionPane.showMessageDialog(null, "Nome e espécie do animal são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao processar os dados. Verifique as entradas.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    return null;
}

public static Veterinario adicionaVeterinarioComDialogo() {
    // Variáveis para armazenar os valores persistentes
    String nome = "";
    String email = "";
    String telefone = "";
    String especialidade = "";
    String crmv = "";

    boolean validInput = false;

    while (!validInput) {
        // Campos de entrada preenchidos com os valores atuais
        JTextField nomeField = new JTextField(nome);
        JTextField emailField = new JTextField(email);
        JTextField telefoneField = new JTextField(telefone);
        JTextField especialidadeField = new JTextField(especialidade);
        JTextField crmvField = new JTextField(crmv);

        Object[] message = {
            "Nome:", nomeField,
            "Email:", emailField,
            "Telefone (somente números):", telefoneField,
            "Especialidade:", especialidadeField,
            "CRMV:", crmvField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Adicionar Novo Veterinário", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
            return null;
        }

        // Recuperar os valores inseridos
        nome = nomeField.getText().trim();
        email = emailField.getText().trim();
        telefone = telefoneField.getText().trim();
        especialidade = especialidadeField.getText().trim();
        crmv = crmvField.getText().trim();

        // Validações dos campos
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo 'Nome' é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            continue; // Mantém o loop e deixa o formulário aberto
        }

        if (!telefone.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(null, "Telefone inválido! Insira um número com 11 dígitos.", "Erro", JOptionPane.ERROR_MESSAGE);
            continue; 
        }

        if (especialidade.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo 'Especialidade' é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            continue; 
        }

        if (crmv.isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo 'CRMV' é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            continue;
        }

        // Se tudo estiver válido, cria o veterinário
        Veterinario novoVeterinario = VeterinarioDAO.getInstance().create(nome, email, telefone, especialidade, crmv);
        JOptionPane.showMessageDialog(null, "Veterinário adicionado com sucesso!");
        validInput = true; // Sai do loop
        return novoVeterinario;
    }
    return null;
}

    
/*public static void atualizarTratamentosDoClienteSelecionado(JTable tratamentoTable) {
    if (clienteSelecionado != null) {
        List<Animal> animaisDoCliente = AnimalDAO.getInstance().retrieveByClienteId(clienteSelecionado.getId());
        List<Tratamento> tratamentos = new ArrayList<>();

        for (Animal animal : animaisDoCliente) {
            tratamentos.addAll(TratamentoDAO.getInstance().retrieveByAnimalId(animal.getId()));
        }

        TratamentoTableModel tratamentoModel = new TratamentoTableModel(tratamentos);
        setTableModel(tratamentoTable, tratamentoModel);
        tratamentoModel.fireTableDataChanged();
    } else {
        setTableModel(tratamentoTable, new TratamentoTableModel(new ArrayList<>()));
    }
}*/

public static void atualizarTratamentosDoClienteSelecionado(JTable tratamentosTable) {
    List<Tratamento> tratamentos;

    if (clienteSelecionado != null) {
        // Filtrar tratamentos pelo cliente selecionado
        List<Animal> animaisDoCliente = AnimalDAO.getInstance().retrieveByClienteId(clienteSelecionado.getId());
        tratamentos = new ArrayList<>();
        for (Animal animal : animaisDoCliente) {
            tratamentos.addAll(TratamentoDAO.getInstance().retrieveByAnimalId(animal.getId()));
        }
    } else {
        // Mostrar todos os tratamentos
        tratamentos = TratamentoDAO.getInstance().retrieveAll();
    }

    // Atualiza o modelo da tabela com os tratamentos apropriados
    TratamentoTableModel modeloTratamento = new TratamentoTableModel(tratamentos);
    setTableModel(tratamentosTable, modeloTratamento);
    modeloTratamento.fireTableDataChanged();
}


public static void atualizarConsultasParaTratamento(JTable consultaTable, int animalId) {
    // Recupera consultas para o tratamento do animal selecionado
    List<Consulta> consultasParaTratamento = ConsultaDAO.getInstance().retrieveByAnimalIdForTreatment(animalId);
    
    // Atualiza o modelo da tabela de consultas para tratamento
    ConsultaTableModel consultaModel = new ConsultaTableModel(consultasParaTratamento);
    setTableModel(consultaTable, consultaModel);
    consultaModel.fireTableDataChanged(); // Atualiza a tabela na interface
}

public static void atualizarExamesDoClienteSelecionado(JTable examesTable) {
    if (clienteSelecionado != null) {
        // Lista de animais pertencentes ao cliente selecionado
        List<Animal> animaisDoCliente = AnimalDAO.getInstance().retrieveByClienteId(clienteSelecionado.getId());
        List<Exame> examesFiltrados = new ArrayList<>();

        for (Animal animal : animaisDoCliente) {
            // Para cada animal, buscar consultas associadas
            List<Consulta> consultasDoAnimal = ConsultaDAO.getInstance().retrieveByAnimalId(animal.getId());
            for (Consulta consulta : consultasDoAnimal) {
                // Para cada consulta, buscar exames associados
                examesFiltrados.addAll(ExameDAO.getInstance().retrieveByIdConsulta(consulta.getId()));
            }
        }

        // Atualizar o modelo da tabela com os exames do cliente selecionado
        ExameTableModel exameModel = new ExameTableModel(examesFiltrados);
        setTableModel(examesTable, exameModel);
        exameModel.fireTableDataChanged();
    } else {
        // Limpar a tabela se nenhum cliente estiver selecionado
        setTableModel(examesTable, new ExameTableModel(new ArrayList<>()));
    }
}

public static List<String> gerarHorariosDisponiveis(Calendar data, int veterinarioId) {
    List<String> horariosDisponiveis = new ArrayList<>();
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    // Horário de início e fim do expediente 
    Calendar inicioExpediente = (Calendar) data.clone();
    inicioExpediente.set(Calendar.HOUR_OF_DAY, 8);
    inicioExpediente.set(Calendar.MINUTE, 0);

    Calendar fimExpediente = (Calendar) data.clone();
    fimExpediente.set(Calendar.HOUR_OF_DAY, 18);
    fimExpediente.set(Calendar.MINUTE, 0);

    // Intervalo entre consultas em minutos
    int intervaloMinutos = 30;

    // Obter as consultas já agendadas para o veterinário nesta data
    List<Consulta> consultasAgendadas = ConsultaDAO.getInstance().retrieveByDataEVeterinario(data, veterinarioId);

    while (inicioExpediente.before(fimExpediente)) {
        boolean ocupado = false;

        // Verificar se o horário está ocupado
        for (Consulta consulta : consultasAgendadas) {
            Calendar horarioConsulta = consulta.getHorario();
            if (inicioExpediente.getTime().equals(horarioConsulta.getTime())) {
                ocupado = true;
                break;
            }
        }

        if (!ocupado) {
            horariosDisponiveis.add(timeFormat.format(inicioExpediente.getTime()));
        }

        inicioExpediente.add(Calendar.MINUTE, intervaloMinutos);
    }

    return horariosDisponiveis;
}

public static List<String> filtrarHorariosDisponiveis(Calendar data, int idVeterinario) {
    List<String> todosHorarios = gerarHorariosDisponiveis(data, idVeterinario);
    List<Consulta> consultasDoDia = ConsultaDAO.getInstance().retrieveByDataEVeterinario(data, idVeterinario);

    for (Consulta consulta : consultasDoDia) {
        Calendar horarioConsulta = consulta.getHorario();
        String horarioFormatado = new SimpleDateFormat("HH:mm").format(horarioConsulta.getTime());
        todosHorarios.remove(horarioFormatado);
    }

    return todosHorarios;
}

public static Consulta adicionaConsultaComDialogo(JTable consultaTable) {
    if (clienteSelecionado == null) {
        JOptionPane.showMessageDialog(null, "Por favor, filtre um cliente antes de adicionar uma consulta.", "Erro", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    // Campos para entrada de dados
    JFormattedTextField dataField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
    //dataField.setValue(new Date()); // Preenche com a data atual
    JTextField obsField = new JTextField();

    // Recuperar a lista de animais do cliente selecionado
    List<Animal> animaisDoCliente = getAnimaisDoClienteSelecionado();
    if (animaisDoCliente.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Este cliente não possui animais cadastrados.", "Erro", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    JComboBox<Animal> animalComboBox = new JComboBox<>(animaisDoCliente.toArray(new Animal[0]));

    // Recuperar a lista de veterinários
    List<Veterinario> veterinarios = getTodosVeterinarios();
    if (veterinarios.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhum veterinário disponível.", "Erro", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    JComboBox<Veterinario> veterinarioComboBox = new JComboBox<>(veterinarios.toArray(new Veterinario[0]));

    Object[] message = {
        "Data (dd/MM/yyyy):", dataField,
        "Observações:", obsField,
        "Animal:", animalComboBox,
        "Veterinário:", veterinarioComboBox
    };

    int option = JOptionPane.showConfirmDialog(null, message, "Adicionar Nova Consulta", JOptionPane.OK_CANCEL_OPTION);

    if (option == JOptionPane.OK_OPTION) {
        try {
            Date data = new SimpleDateFormat("dd/MM/yyyy").parse(dataField.getText().trim());
            Animal animalSelecionadoDialogo = (Animal) animalComboBox.getSelectedItem();
            Veterinario veterinarioSelecionado = (Veterinario) veterinarioComboBox.getSelectedItem();

            if (animalSelecionadoDialogo != null && veterinarioSelecionado != null) {
                // Gera e filtra os horários disponíveis
                Calendar dataConsulta = Calendar.getInstance();
                dataConsulta.setTime(data);
                List<String> horariosDisponiveis = filtrarHorariosDisponiveis(dataConsulta, veterinarioSelecionado.getId());

                if (horariosDisponiveis.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nenhum horário disponível para esta data e veterinário.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return null;
                }

                // Exibe os horários disponíveis para seleção
                JComboBox<String> horarioComboBox = new JComboBox<>(horariosDisponiveis.toArray(new String[0]));
                int horarioOption = JOptionPane.showConfirmDialog(null, horarioComboBox, "Selecione o Horário", JOptionPane.OK_CANCEL_OPTION);

                if (horarioOption == JOptionPane.OK_OPTION) {
                    String horarioSelecionado = (String) horarioComboBox.getSelectedItem();

                    // Configura o horário da consulta
                    Calendar horarioConsulta = Calendar.getInstance();
                    String[] partesHorario = horarioSelecionado.split(":");
                    horarioConsulta.setTime(data);
                    horarioConsulta.set(Calendar.HOUR_OF_DAY, Integer.parseInt(partesHorario[0]));
                    horarioConsulta.set(Calendar.MINUTE, Integer.parseInt(partesHorario[1]));
                    horarioConsulta.set(Calendar.SECOND, 0);
                    horarioConsulta.set(Calendar.MILLISECOND, 0);

                    String observacoes = obsField.getText().trim();

                    // Criação da nova consulta
                    Consulta novaConsulta = ConsultaDAO.getInstance().create(
                        dataConsulta, horarioConsulta, observacoes,
                        animalSelecionadoDialogo.getId(), veterinarioSelecionado.getId(), 0, 0, false
                    );

                    // Atualiza a tabela de consultas
                    atualizarConsultasDoClienteSelecionado(consultaTable);
                    JOptionPane.showMessageDialog(null, "Consulta adicionada com sucesso.");
                    return novaConsulta;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Erro no formato da data.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    return null;
}

public static void adicionarPagamentoParaConsultaSelecionada(JTable pagamentosTable) {
    if (clienteSelecionado == null) {
        JOptionPane.showMessageDialog(null, "Selecione um cliente primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    List<Animal> animaisDoCliente = AnimalDAO.getInstance().retrieveByClienteId(clienteSelecionado.getId());
    if (animaisDoCliente.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhum animal disponível para este cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JComboBox<Animal> animalComboBox = new JComboBox<>(animaisDoCliente.toArray(new Animal[0]));
    int animalOption = JOptionPane.showConfirmDialog(null, animalComboBox, "Selecione o Animal", JOptionPane.OK_CANCEL_OPTION);
    
    if (animalOption != JOptionPane.OK_OPTION) {
        return;
    }

    Animal animalSelecionado = (Animal) animalComboBox.getSelectedItem();

    List<Consulta> consultasDoAnimal = ConsultaDAO.getInstance().retrieveByAnimalIForPagament(animalSelecionado.getId());
    if (consultasDoAnimal.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhuma consulta disponível para este animal.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JComboBox<Consulta> consultaComboBox = new JComboBox<>(consultasDoAnimal.toArray(new Consulta[0]));
    JTextField valorField = new JTextField();
    JTextField descricaoField = new JTextField();

    Object[] message = {
        "Selecione a Consulta:", consultaComboBox,
        "Valor do Pagamento:", valorField,
        "Forma de Pagamento:", descricaoField
    };

    int option = JOptionPane.showConfirmDialog(null, message, "Adicionar Novo Pagamento", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
        try {
            Consulta consultaSelecionada = (Consulta) consultaComboBox.getSelectedItem();
            double valorPagamento = Double.parseDouble(valorField.getText().trim());
            String descricao = descricaoField.getText().trim();

            if (consultaSelecionada != null) {
                // Cria novo pagamento
                Pagamento novoPagamento = PagamentoDAO.getInstance().create(valorPagamento, descricao, false, consultaSelecionada.getId());

                // Associa o pagamento à consulta e atualiza a consulta no banco
                consultaSelecionada.setIdPagamento(novoPagamento.getId());
                ConsultaDAO.getInstance().update(consultaSelecionada);

                // Atualiza a tabela de pagamentos 
                atualizarPagamentosDoClienteSelecionado(pagamentosTable);
                JOptionPane.showMessageDialog(null, "Pagamento adicionado com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, insira um valor válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor de pagamento inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

public static void adicionarTratamentoParaConsultaSelecionada(JTable tratamentoTable, JTable consultaTable) {
    if (clienteSelecionado == null) {
        JOptionPane.showMessageDialog(null, "Selecione um cliente primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    List<Animal> animaisDoCliente = AnimalDAO.getInstance().retrieveByClienteId(clienteSelecionado.getId());
    if (animaisDoCliente.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhum animal disponível para este cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JComboBox<Animal> animalComboBox = new JComboBox<>(animaisDoCliente.toArray(new Animal[0]));
    int animalOption = JOptionPane.showConfirmDialog(null, animalComboBox, "Selecione o Animal", JOptionPane.OK_CANCEL_OPTION);

    if (animalOption != JOptionPane.OK_OPTION) {
        return;
    }

    Animal animalSelecionado = (Animal) animalComboBox.getSelectedItem();

    List<Consulta> consultasDoAnimal = ConsultaDAO.getInstance().retrieveByAnimalIdForTreatment(animalSelecionado.getId());
    if (consultasDoAnimal.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhuma consulta disponível para este animal.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JComboBox<Consulta> consultaComboBox = new JComboBox<>(consultasDoAnimal.toArray(new Consulta[0]));
    int consultaOption = JOptionPane.showConfirmDialog(null, consultaComboBox, "Selecione a Consulta", JOptionPane.OK_CANCEL_OPTION);

    if (consultaOption != JOptionPane.OK_OPTION) {
        return;
    }

    Consulta consultaSelecionada = (Consulta) consultaComboBox.getSelectedItem();

    do {
        JTextField tratamentoIndicadoField = new JTextField();
        JFormattedTextField dataFinalizacaoField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        dataFinalizacaoField.setValue(null); // Permite valor nulo para a data

        Object[] message = {
            "Tratamento Indicado:", tratamentoIndicadoField,
            "Data de Finalização (opcional):", dataFinalizacaoField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Adicionar Novo Tratamento", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String tratamentoIndicado = tratamentoIndicadoField.getText().trim();
                String dataFinalizacaoStr = dataFinalizacaoField.getText().trim();
                Calendar dataFim = null; // Data de finalização inicial como nula

                if (!dataFinalizacaoStr.isEmpty()) {
                    Date dataFinalizacao = new SimpleDateFormat("dd/MM/yyyy").parse(dataFinalizacaoStr);
                    dataFim = Calendar.getInstance();
                    dataFim.setTime(dataFinalizacao);
                }

                if (!tratamentoIndicado.isEmpty()) {
                    Tratamento novoTratamento = TratamentoDAO.getInstance().create(
                        consultaSelecionada.getIdAnimal(),
                        tratamentoIndicado,
                        consultaSelecionada.getData(),
                        dataFim,
                        false,
                        tratamentoIndicado
                    );

                    atualizarTratamentosDoClienteSelecionado(tratamentoTable);
                    JOptionPane.showMessageDialog(null, "Tratamento adicionado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(null, "O campo de tratamento é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Erro no formato da data de finalização.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            break;
        }

        // Personalizando opções "Sim" e "Não"
        Object[] options = {"Sim", "Não"};
        int continueOption = JOptionPane.showOptionDialog(
            null,
            "Deseja adicionar mais um tratamento para esta consulta?",
            "Adicionar Outro Tratamento",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (continueOption != JOptionPane.YES_OPTION) {
            break;
        }
    } while (true);
}


/*public static void adicionarTratamentoParaConsultaSelecionada(JTable tratamentoTable, JTable consultaTable) {
    if (clienteSelecionado == null) {
        JOptionPane.showMessageDialog(null, "Selecione um cliente primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    List<Animal> animaisDoCliente = AnimalDAO.getInstance().retrieveByClienteId(clienteSelecionado.getId());
    if (animaisDoCliente.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhum animal disponível para este cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JComboBox<Animal> animalComboBox = new JComboBox<>(animaisDoCliente.toArray(new Animal[0]));
    int animalOption = JOptionPane.showConfirmDialog(null, animalComboBox, "Selecione o Animal", JOptionPane.OK_CANCEL_OPTION);

    if (animalOption != JOptionPane.OK_OPTION) {
        return;
    }

    Animal animalSelecionado = (Animal) animalComboBox.getSelectedItem();

    List<Consulta> consultasDoAnimal = ConsultaDAO.getInstance().retrieveByAnimalIdForTreatment(animalSelecionado.getId());
    if (consultasDoAnimal.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhuma consulta disponível para este animal.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JComboBox<Consulta> consultaComboBox = new JComboBox<>(consultasDoAnimal.toArray(new Consulta[0]));
    int consultaOption = JOptionPane.showConfirmDialog(null, consultaComboBox, "Selecione a Consulta", JOptionPane.OK_CANCEL_OPTION);

    if (consultaOption != JOptionPane.OK_OPTION) {
        return;
    }

    Consulta consultaSelecionada = (Consulta) consultaComboBox.getSelectedItem();

    do {
        JTextField tratamentoIndicadoField = new JTextField();
        JFormattedTextField dataFinalizacaoField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
        dataFinalizacaoField.setValue(null); // Permite valor nulo para a data

        Object[] message = {
            "Tratamento Indicado:", tratamentoIndicadoField,
            "Data de Finalização (opcional):", dataFinalizacaoField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Adicionar Novo Tratamento", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String tratamentoIndicado = tratamentoIndicadoField.getText().trim();
                String dataFinalizacaoStr = dataFinalizacaoField.getText().trim();
                Calendar dataFim = null; // Data de finalização inicial como nula

                if (!dataFinalizacaoStr.isEmpty()) {
                    Date dataFinalizacao = new SimpleDateFormat("dd/MM/yyyy").parse(dataFinalizacaoStr);
                    dataFim = Calendar.getInstance();
                    dataFim.setTime(dataFinalizacao);
                }

                if (!tratamentoIndicado.isEmpty()) {
                    Tratamento novoTratamento = TratamentoDAO.getInstance().create(
                        consultaSelecionada.getIdAnimal(),
                        tratamentoIndicado,
                        consultaSelecionada.getData(),
                        dataFim,
                        false,
                        tratamentoIndicado
                    );

                    atualizarTratamentosDoClienteSelecionado(tratamentoTable);
                    JOptionPane.showMessageDialog(null, "Tratamento adicionado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(null, "O campo de tratamento é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Erro no formato da data de finalização.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            break;
        }
    } while (JOptionPane.showConfirmDialog(null, "Deseja adicionar mais um tratamento para esta consulta?", 
            "Adicionar Outro Tratamento", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
  
}*/


public static void apagarTratamentoSelecionado(JTable tratamentoTable) {
        int selectedRow = tratamentoTable.getSelectedRow();
        if (selectedRow >= 0) {
            Tratamento tratamento = (Tratamento) ((TratamentoTableModel) tratamentoTable.getModel()).getItem(selectedRow);

            int confirm = JOptionPane.showOptionDialog(null, 
                "Tem certeza de que deseja apagar este tratamento?", 
                "Confirmação de Exclusão", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, new Object[]{"Sim", "Não"}, "Sim");


            if (confirm == JOptionPane.YES_OPTION) {
                TratamentoDAO.getInstance().delete(tratamento);

                ((TratamentoTableModel) tratamentoTable.getModel()).removeItem(selectedRow);
                JOptionPane.showMessageDialog(null, "Tratamento apagado com sucesso.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um tratamento para apagar.");
        }
    }
    
public static List<Exame> getTodosExames() {
    return ExameDAO.getInstance().retrieveAll();
}
   
/*public static void adicionarExameParaConsultaSelecionada(JTable examesTable) {
    if (clienteSelecionado == null) {
        JOptionPane.showMessageDialog(null, "Selecione um cliente primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    List<Animal> animaisDoCliente = AnimalDAO.getInstance().retrieveByClienteId(clienteSelecionado.getId());
    if (animaisDoCliente.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhum animal disponível para este cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JComboBox<Animal> animalComboBox = new JComboBox<>(animaisDoCliente.toArray(new Animal[0]));
    int animalOption = JOptionPane.showConfirmDialog(null, animalComboBox, "Selecione o Animal", JOptionPane.OK_CANCEL_OPTION);

    if (animalOption != JOptionPane.OK_OPTION) {
        return;
    }

    Animal animalSelecionado = (Animal) animalComboBox.getSelectedItem();

    // Busca apenas consultas que ainda não tem exames associados
    List<Consulta> consultasDoAnimal = ConsultaDAO.getInstance().retrieveByAnimalIdForExam(animalSelecionado.getId());
    if (consultasDoAnimal.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhuma consulta disponível para este animal.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JComboBox<Consulta> consultaComboBox = new JComboBox<>(consultasDoAnimal.toArray(new Consulta[0]));
    JTextField nomeExameField = new JTextField();

    Object[] message = {
        "Selecione a Consulta:", consultaComboBox,
        "Nome do Exame:", nomeExameField
    };

    int option = JOptionPane.showConfirmDialog(null, message, "Adicionar Novo Exame", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
        Consulta consultaSelecionada = (Consulta) consultaComboBox.getSelectedItem();
        String nomeExame = nomeExameField.getText().trim();

        if (!nomeExame.isEmpty() && consultaSelecionada != null) {
            // Cria o exame e adiciona-o à tabela
            Exame novoExame = ExameDAO.getInstance().create(
                nomeExame,
                consultaSelecionada.getId(),
                false,
                null,
                null
            );

            atualizarExamesDoClienteSelecionado(examesTable);

            JOptionPane.showMessageDialog(null, "Exame adicionado com sucesso e está marcado como pendente.");

            // Marca a consulta como concluída para exames
            consultaSelecionada.setExameConcluido(true);
            ConsultaDAO.getInstance().update(consultaSelecionada);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, insira o nome do exame.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}*/

public static void adicionarExameParaConsultaSelecionada(JTable examesTable) {
    if (clienteSelecionado == null) {
        JOptionPane.showMessageDialog(null, "Selecione um cliente primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    List<Animal> animaisDoCliente = AnimalDAO.getInstance().retrieveByClienteId(clienteSelecionado.getId());
    if (animaisDoCliente.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhum animal disponível para este cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JComboBox<Animal> animalComboBox = new JComboBox<>(animaisDoCliente.toArray(new Animal[0]));
    int animalOption = JOptionPane.showConfirmDialog(null, animalComboBox, "Selecione o Animal", JOptionPane.OK_CANCEL_OPTION);

    if (animalOption != JOptionPane.OK_OPTION) {
        return;
    }

    Animal animalSelecionado = (Animal) animalComboBox.getSelectedItem();

    // Busca apenas consultas que ainda não têm exames associados
    List<Consulta> consultasDoAnimal = ConsultaDAO.getInstance().retrieveByAnimalIdForExam(animalSelecionado.getId());
    if (consultasDoAnimal.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Nenhuma consulta disponível para este animal.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }

    JComboBox<Consulta> consultaComboBox = new JComboBox<>(consultasDoAnimal.toArray(new Consulta[0]));
    int consultaOption = JOptionPane.showConfirmDialog(null, consultaComboBox, "Selecione a Consulta", JOptionPane.OK_CANCEL_OPTION);

    if (consultaOption != JOptionPane.OK_OPTION) {
        return;
    }

    Consulta consultaSelecionada = (Consulta) consultaComboBox.getSelectedItem();

    do {
        JTextField nomeExameField = new JTextField();

        Object[] message = {
            "Nome do Exame:", nomeExameField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Adicionar Novo Exame", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String nomeExame = nomeExameField.getText().trim();

            if (!nomeExame.isEmpty() && consultaSelecionada != null) {
                // Cria o exame e adiciona-o à tabela
                Exame novoExame = ExameDAO.getInstance().create(
                    nomeExame,
                    consultaSelecionada.getId(),
                    false,
                    null,
                    null
                );

                atualizarExamesDoClienteSelecionado(examesTable);

                JOptionPane.showMessageDialog(null, "Exame adicionado com sucesso e está marcado como pendente.");

                // Marca a consulta como concluída para exames
                consultaSelecionada.setExameConcluido(true);
                ConsultaDAO.getInstance().update(consultaSelecionada);
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, insira o nome do exame.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            break;
        }
        
        // Personalizando opções "Sim" e "Não"
        Object[] options = {"Sim", "Não"};
        int continueOption = JOptionPane.showOptionDialog(
            null,
            "Deseja adicionar mais um exame para esta consulta?",
            "Adicionar Outro Exame",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (continueOption != JOptionPane.YES_OPTION) {
            break;
        }
    } while (true);
}


public static void apagarExameSelecionado(JTable examesTable) {
    int selectedRow = examesTable.getSelectedRow();
    if (selectedRow >= 0) {
        Exame exame = (Exame) ((ExameTableModel) examesTable.getModel()).getItem(selectedRow);

        int confirm = JOptionPane.showConfirmDialog(null, 
            "Tem certeza de que deseja apagar este exame?", 
            "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Apaga o exame do banco de dados
            ExameDAO.getInstance().delete(exame);

            // Remove o exame da tabela
            ((ExameTableModel) examesTable.getModel()).removeItem(selectedRow);
            JOptionPane.showMessageDialog(null, "Exame apagado com sucesso.");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Selecione um exame para apagar.");
    }
}
  
public static void finalizarTratamentoSelecionado(JTable tratamentoTable) {
    int selectedRow = tratamentoTable.getSelectedRow();
        if (selectedRow >= 0) {
            Tratamento tratamento = (Tratamento) ((TratamentoTableModel) tratamentoTable.getModel()).getItem(selectedRow);

        int confirm = JOptionPane.showConfirmDialog(null, 
            "Tem certeza de que deseja finalizar este tratamento?", 
            "Confirmação de Finalização", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                JFormattedTextField dataField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
                dataField.setPreferredSize(new Dimension(100, 20));
                Object[] message = {"Data de Finalização (dd/MM/yyyy):", dataField};

            int dateOption = JOptionPane.showConfirmDialog(null, message, "Inserir Data de Finalização", JOptionPane.OK_CANCEL_OPTION);
            if (dateOption == JOptionPane.OK_OPTION) {
                try {
                    Date dataFinalizacao = new SimpleDateFormat("dd/MM/yyyy").parse(dataField.getText().trim());
                    Calendar dataFim = Calendar.getInstance();
                    dataFim.setTime(dataFinalizacao);

                    // Define o tratamento como finalizado e armazena a data de término
                    tratamento.setTerminou(true);
                    tratamento.setDataFim(dataFim); 

                    // Atualiza o tratamento no banco de dados
                    TratamentoDAO.getInstance().update(tratamento);

                    // Atualiza o modelo da tabela para refletir as alterações
                    ((TratamentoTableModel) tratamentoTable.getModel()).fireTableDataChanged();
                    
                    JOptionPane.showMessageDialog(null, "Tratamento finalizado com sucesso.");
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null, "Formato de data inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Selecione um tratamento para finalizar.");
    }
}

public static void filtrarConsultasPorVeterinario(JTable table) {
    List<Veterinario> veterinarios = VeterinarioDAO.getInstance().retrieveAll(); 
    
    Veterinario selecionado = (Veterinario) JOptionPane.showInputDialog(
        null, "Selecione o Veterinário", "Filtrar Consultas",
        JOptionPane.QUESTION_MESSAGE, null, veterinarios.toArray(), veterinarios.get(0));

    if (selecionado != null) {
        // Busca as consultas do veterinário selecionado
        List<Consulta> consultas = ConsultaDAO.getInstance().retrieveByVeterinarioId(selecionado.getId());
        
        // Define o modelo da tabela com as consultas encontradas
        ConsultaTableModel consultaModel = new ConsultaTableModel(consultas);
        table.setModel(consultaModel);
        consultaModel.fireTableDataChanged();
    } else {
        JOptionPane.showMessageDialog(null, "Nenhum veterinário foi selecionado.");
    }
}

public static void mostrarTodosPagamentos(JTable pagamentosTable) {
    // Recupera todos os pagamentos do banco de dados
    List<Pagamento> pagamentos = PagamentoDAO.getInstance().retrieveAll();

    // Atualiza o modelo da tabela com todos os pagamentos
    PagamentoTableModel modeloPagamento = new PagamentoTableModel(pagamentos);
    setTableModel(pagamentosTable, modeloPagamento);
    modeloPagamento.fireTableDataChanged();
}
    
public static void mostrarTodosTratamentos(JTable tratamentosTable) {
    // Recupera todos os tratamentos do banco de dados
    List<Tratamento> tratamentos = TratamentoDAO.getInstance().retrieveAll();

    // Atualiza o modelo da tabela com todos os tratamentos
    TratamentoTableModel modeloTratamento = new TratamentoTableModel(tratamentos);
    setTableModel(tratamentosTable, modeloTratamento);
    modeloTratamento.fireTableDataChanged();
}

public static void mostrarTodosExames(JTable examesTable) {
    // Recupera todos os exames do banco de dados
    List<Exame> exames = ExameDAO.getInstance().retrieveAll();

    // Atualiza o modelo da tabela com todos os exames
    ExameTableModel modeloExame = new ExameTableModel(exames);
    setTableModel(examesTable, modeloExame);
    modeloExame.fireTableDataChanged();
}

public static void filtrarClienteEAtualizarTabelas(JTable pagamentosTable, JTable consultasTable, JTable tratamentosTable, JTable examesTable) {
    // Obtém todos os clientes para exibir no JComboBox
    List<Cliente> clientes = ClienteDAO.getInstance().retrieveAll();

    // Mostra um diálogo para o usuário selecionar o cliente
    Cliente clienteSelecionadoFiltro = (Cliente) JOptionPane.showInputDialog(
        null,
        "Selecione o Cliente",
        "Filtrar Cliente",
        JOptionPane.QUESTION_MESSAGE,
        null,
        clientes.toArray(),
        clientes.isEmpty() ? null : clientes.get(0)
    );

    if (clienteSelecionadoFiltro != null) {
        // Atualiza o cliente selecionado globalmente
        clienteSelecionado = clienteSelecionadoFiltro;

        // Atualiza o campo de texto com o nome do cliente
        if (clienteSelecionadoTextField != null) {
            clienteSelecionadoTextField.setText(clienteSelecionado.getNome());
        }

        // Atualiza as tabelas associadas ao cliente selecionado
        atualizarPagamentosDoClienteSelecionado(pagamentosTable);
        atualizarConsultasDoClienteSelecionado(consultasTable);
        atualizarTratamentosDoClienteSelecionado(tratamentosTable);
        atualizarExamesDoClienteSelecionado(examesTable);
    } else {
        JOptionPane.showMessageDialog(null, "Nenhum cliente foi selecionado.");
    }
}

public static void filtrarTratamentosPorCliente(JTable consultasTable, JTable pagamentosTable, JTable tratamentosTable, JTable examesTable) {
    // Obtém todos os clientes para exibir no JComboBox
    List<Cliente> clientes = ClienteDAO.getInstance().retrieveAll();

    // Mostra um diálogo para o usuário selecionar o cliente
    Cliente clienteSelecionado = (Cliente) JOptionPane.showInputDialog(
            null, 
            "Selecione o Cliente", 
            "Filtrar Tratamentos",
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            clientes.toArray(), 
            clientes.isEmpty() ? null : clientes.get(0) 
    );

    if (clienteSelecionado != null) {
        
        // Atualiza o cliente selecionado globalmente
        clienteSelecionado = clienteSelecionado;

        // Atualiza o campo de texto na interface
        if (clienteSelecionadoTextField != null) {
            clienteSelecionadoTextField.setText(clienteSelecionado.getNome());
        }
        // Obtém os animais do cliente selecionado
        List<Animal> animaisDoCliente = AnimalDAO.getInstance().retrieveByClienteId(clienteSelecionado.getId());

        // Lista para armazenar os tratamentos associados aos animais
        List<Tratamento> tratamentos = new ArrayList<>();
        for (Animal animal : animaisDoCliente) {
            tratamentos.addAll(TratamentoDAO.getInstance().retrieveByAnimalId(animal.getId()));
        }

        // Atualiza o modelo da tabela com os tratamentos encontrados
        TratamentoTableModel modeloTratamento = new TratamentoTableModel(tratamentos);
        setTableModel(tratamentosTable, modeloTratamento);
        modeloTratamento.fireTableDataChanged();
        
        atualizarConsultasDoClienteSelecionado(consultasTable);
        atualizarExamesDoClienteSelecionado(examesTable);
        atualizarPagamentosDoClienteSelecionado(pagamentosTable);
    } else {
        JOptionPane.showMessageDialog(null, "Nenhum cliente foi selecionado.");
    }
}
       
public static void filtrarConsultasPorCliente(JTable consultasTable, JTable pagamentosTable, JTable tratamentosTable, JTable examesTable) {
    // Obtém todos os clientes para exibir no JComboBox
    List<Cliente> clientes = ClienteDAO.getInstance().retrieveAll();

    // Mostra um diálogo para o usuário selecionar o cliente
    Cliente clienteSelecionadoFiltro = (Cliente) JOptionPane.showInputDialog(
            null, 
            "Selecione o Cliente", 
            "Filtrar Consultas",
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            clientes.toArray(), 
            clientes.isEmpty() ? null : clientes.get(0) // Seleciona o primeiro cliente, se existir
    );

    if (clienteSelecionadoFiltro != null) {
        // Atualiza o cliente selecionado globalmente
        clienteSelecionado = clienteSelecionadoFiltro;

        // Atualiza o campo de texto na interface
        if (clienteSelecionadoTextField != null) {
            clienteSelecionadoTextField.setText(clienteSelecionado.getNome());
        }

        // Atualiza a tabela de consultas
        List<Consulta> consultas = ConsultaDAO.getInstance().retrieveByClienteId(clienteSelecionadoFiltro.getId());
        ConsultaTableModel modeloConsulta = new ConsultaTableModel(consultas);
        setTableModel(consultasTable, modeloConsulta);
        modeloConsulta.fireTableDataChanged();

        // Atualiza as outras tabelas
        atualizarTratamentosDoClienteSelecionado(tratamentosTable);
        atualizarExamesDoClienteSelecionado(examesTable);
        atualizarPagamentosDoClienteSelecionado(pagamentosTable);
    } else {
        JOptionPane.showMessageDialog(null, "Nenhum cliente foi selecionado.");
    }
}

public static void filtrarExamesPorCliente(JTable examesTable) {
    // Obtém todos os clientes para exibir no JComboBox
    List<Cliente> clientes = ClienteDAO.getInstance().retrieveAll();

    // Mostra um diálogo para o usuário selecionar o cliente
    Cliente clienteSelecionado = (Cliente) JOptionPane.showInputDialog(
            null, 
            "Selecione o Cliente", 
            "Filtrar Exames",
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            clientes.toArray(), 
            clientes.isEmpty() ? null : clientes.get(0) // Seleciona o primeiro cliente, se existir
    );

    if (clienteSelecionado != null) {
        
        // Atualiza o cliente selecionado 
        clienteSelecionado = clienteSelecionado;

        // Atualiza o campo de texto no textField
        if (clienteSelecionadoTextField != null) {
            clienteSelecionadoTextField.setText(clienteSelecionado.getNome());
        }
        // Pega os animais do cliente selecionado
        List<Animal> animaisDoCliente = AnimalDAO.getInstance().retrieveByClienteId(clienteSelecionado.getId());

        // Lista para armazenar os exames associados às consultas dos animais
        List<Exame> exames = new ArrayList<>();
        for (Animal animal : animaisDoCliente) {
            // Para cada animal, buscar consultas associadas
            List<Consulta> consultasDoAnimal = ConsultaDAO.getInstance().retrieveByAnimalId(animal.getId());
            for (Consulta consulta : consultasDoAnimal) {
                // Para cada consulta, buscar exames associados
                exames.addAll(ExameDAO.getInstance().retrieveByIdConsulta(consulta.getId()));
            }
        }

        // Atualiza o modelo da tabela com os exames encontrados
        ExameTableModel modeloExame = new ExameTableModel(exames);
        setTableModel(examesTable, modeloExame);
        modeloExame.fireTableDataChanged();
    } else {
        JOptionPane.showMessageDialog(null, "Nenhum cliente foi selecionado.");
    }
}

public static void mostrarTodosRegistros(JTable consultasTable, JTable pagamentosTable, JTable tratamentosTable, JTable examesTable) {
    // Limpa a seleção do cliente global
    clienteSelecionado = null;

    // Limpa o campo de texto associado ao cliente selecionado
    if (clienteSelecionadoTextField != null) {
        clienteSelecionadoTextField.setText("");
    }

    // Atualiza a tabela de consultas para mostrar todas as consultas
    List<Consulta> todasConsultas = ConsultaDAO.getInstance().retrieveAll();
    ConsultaTableModel modeloConsulta = new ConsultaTableModel(todasConsultas);
    setTableModel(consultasTable, modeloConsulta);
    modeloConsulta.fireTableDataChanged();

    // Atualiza a tabela de pagamentos para mostrar todos os pagamentos
    List<Pagamento> todosPagamentos = PagamentoDAO.getInstance().retrieveAll();
    PagamentoTableModel modeloPagamento = new PagamentoTableModel(todosPagamentos);
    setTableModel(pagamentosTable, modeloPagamento);
    modeloPagamento.fireTableDataChanged();

    // Atualiza a tabela de tratamentos para mostrar todos os tratamentos
    List<Tratamento> todosTratamentos = TratamentoDAO.getInstance().retrieveAll();
    TratamentoTableModel modeloTratamento = new TratamentoTableModel(todosTratamentos);
    setTableModel(tratamentosTable, modeloTratamento);
    modeloTratamento.fireTableDataChanged();

    // Atualiza a tabela de exames para mostrar todos os exames
    List<Exame> todosExames = ExameDAO.getInstance().retrieveAll();
    ExameTableModel modeloExame = new ExameTableModel(todosExames);
    setTableModel(examesTable, modeloExame);
    modeloExame.fireTableDataChanged();
}

   
}
