//Classe controle do programa
package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Estacionamento;
import models.dao.EstacionamentoDAO;
import javax.swing.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

//Variaveis da classe
    private Stage stage = new Stage();
    ToastNotification toast = new ToastNotification();
    public static ArrayList<Estacionamento> carros = new ArrayList<>();
    public static ArrayList<Estacionamento> carrosfiltrados = new ArrayList<>();
    private static EstacionamentoDAO ED = new EstacionamentoDAO();
    private boolean editing = false;
    public int index;
    public Estacionamento carro_selecionado;

//Variaveis injetadas do FXML
    @FXML
    AnchorPane pane;
    @FXML
    DatePicker filtro_data;
    @FXML
    TextField model, color, plate, parking, filtro_vaga;
    @FXML
    Button btn_save, btn_filtrar, btn_excluir, btn_saida;
    @FXML
    CheckBox filtro_estacionado;
    @FXML
    TableView table;
    @FXML
    TableColumn<Estacionamento, String> table_data;
    @FXML
    TableColumn<Estacionamento, String> table_modelo;
    @FXML
    TableColumn<Estacionamento, String> table_cor;
    @FXML
    TableColumn<Estacionamento, String> table_placa;
    @FXML
    TableColumn<Estacionamento, String> table_vaga;
    @FXML
    TableColumn<Estacionamento, String> table_entrada;
    @FXML
    TableColumn<Estacionamento, String> table_saida;


//Metodo que inicializa e controla os nodes do programa
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.setFocusTraversable(false);

//Comandos que associam os atributos do objeto aos campos da tabela
        table_data.setCellValueFactory(new PropertyValueFactory<>("data"));
        table_modelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        table_cor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        table_placa.setCellValueFactory(new PropertyValueFactory<>("placa"));
        table_vaga.setCellValueFactory(new PropertyValueFactory<>("vaga"));
        table_entrada.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));
        table_saida.setCellValueFactory(new PropertyValueFactory<>("horaSaida"));
        btn_save.setOnMouseEntered(mouseEvent -> {
            btn_save.setStyle("-fx-background-color: #00A600;-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #fff;");

        });
        btn_save.setOnMouseExited(mouseEvent -> {
            btn_save.setStyle("-fx-background-color: #008000;-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #fff;");
        });
        btn_excluir.setOnMouseEntered(mouseEvent -> {
            btn_excluir.setStyle("-fx-background-color: #ff0000;-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #fff;");

        });
        btn_excluir.setOnMouseExited(mouseEvent -> {
            btn_excluir.setStyle("-fx-background-color: #cd0000;-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #fff;");
        });
        btn_saida.setOnMouseEntered(mouseEvent -> {
            btn_saida.setStyle("-fx-background-color: #ffde2d;-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 ); -fx-text-fill: #fff;");

        });
        btn_saida.setOnMouseExited(mouseEvent -> {
            btn_saida.setStyle("-fx-background-color: #FFD700;-fx-background-radius: 6, 5; -fx-background-insets: 0, 1; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 ); " +
                    "-fx-text-fill: #fff;");
        });

//Carrega o arquivo salvo na tabela
        abrir();
        table.getItems().addAll(carros);
        pane.setOnMouseClicked(mouseEvent -> {

            btn_saida.setVisible(false);
            btn_excluir.setVisible(false);
            model.setText("");
            color.setText("");
            plate.setText("");
            parking.setText("");
            editing = false;
            table.getItems().clear();
            table.getItems().addAll(carros);
        });


//Ações do botão salvar
        btn_save.setOnAction(actionEvent -> {
            Salvar();
            model.setText("");
            color.setText("");
            plate.setText("");
            parking.setText("");
        });


//Ações do botão filtrar
        btn_filtrar.setOnAction(actionEvent -> {
            Filtrar();
            table.getItems().removeAll(carros);
            table.getItems().removeAll(carrosfiltrados);
            table.getItems().addAll(carrosfiltrados);
            carrosfiltrados.clear();
            filtro_vaga.setText("");
            filtro_estacionado.setSelected(false);
            filtro_data.setValue(null);
        });


//Comandos para editar, dar saida ou excluir um carro
        table.setOnMouseClicked(mouseEvent -> {
                    carro_selecionado = (Estacionamento) table.getSelectionModel().getSelectedItem();
                    index = table.getSelectionModel().getFocusedIndex();
                    model.setText(carro_selecionado.getModelo());
                    color.setText(carro_selecionado.getCor());
                    plate.setText(carro_selecionado.getPlaca());
                    parking.setText(carro_selecionado.getVaga());
                    if (carro_selecionado.getHoraSaida().equals("Estacionado")) {
                        btn_saida.setVisible(true);
                        btn_excluir.setVisible(false);

                    } else {
                        btn_excluir.setVisible(true);
                        btn_saida.setVisible(false);
                    }
                    editing = true;
                }
        );


//Ações do botão saida
        btn_saida.setOnAction(actionEvent -> {
            table.getItems().set(index, new Estacionamento(carro_selecionado.getModelo(), carro_selecionado.getCor(), carro_selecionado.getVaga(), carro_selecionado.getPlaca(), carro_selecionado.getData(), carro_selecionado.getHoraEntrada(), Estacionamento.GetHora()));
            carros.set(index, new Estacionamento(carro_selecionado.getModelo(), carro_selecionado.getCor(), carro_selecionado.getVaga(), carro_selecionado.getPlaca(), carro_selecionado.getData(), carro_selecionado.getHoraEntrada(), Estacionamento.GetHora()));
            model.setText("");
            color.setText("");
            plate.setText("");
            parking.setText("");
            EstacionamentoDAO.salvar(carros);
            DecimalFormat df = new DecimalFormat("0.00");
            JOptionPane.showMessageDialog(null, "Valor Total: R$" + df.format(Estacionamento.getTotal(carro_selecionado.getHoraEntrada(), Estacionamento.GetHora())), "Valor Total", JOptionPane.INFORMATION_MESSAGE);
            btn_saida.setVisible(false);
            editing = false;
        });


//Ações do botão salvar
        btn_excluir.setOnAction(actionEvent -> {
            carros.remove(index);
            table.getItems().remove(index);
            EstacionamentoDAO.salvar(carros);
            btn_excluir.setVisible(false);
            editing = false;
            model.setText("");
            color.setText("");
            plate.setText("");
            parking.setText("");
            toastNotification("Excluido com sucesso");
        });
    }


//Metodo que coloca os dados do arquivo na lista
    public static void abrir() {
        carros = ED.abrir();
    }


//Metodo que salva ou edita um carro da lista
    private void Salvar() {

        if (editing) {
            Estacionamento carro_editado = new Estacionamento(model.getText(), color.getText(), parking.getText(), plate.getText(), carro_selecionado.getData(), carro_selecionado.getHoraEntrada(), carro_selecionado.getHoraSaida());
            table.getItems().set(index, carro_editado);
            carros.set(index, carro_editado);
            EstacionamentoDAO.salvar(carros);
            editing = false;
            btn_saida.setVisible(false);
            toastNotification("Editado com Sucesso");


        } else {
            Estacionamento carro = new Estacionamento(model.getText(), color.getText(), parking.getText(), plate.getText(), Estacionamento.GetData(), Estacionamento.GetHora(), "Estacionado");
            table.getItems().add(0, carro);
            carros.add(0, carro);
            EstacionamentoDAO.salvar(carros);
            toastNotification("Salvo com Sucesso");
        }
    }


//Metodo responsavel por controlar os filtros
    private void Filtrar() {
        String data = "";
        LocalDate dataDate;
        DateTimeFormatter data_format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String vaga = filtro_vaga.getText();
        if (filtro_data.getValue() != null) {
            dataDate = filtro_data.getValue();
            data = data_format.format(dataDate);
        }
        for (Estacionamento ed : carros) {

            if(filtro_estacionado.isSelected()){
                if(!filtro_vaga.getText().isBlank()){
                    if(data != ""){
                        if(ed.getHoraSaida().equals("Estacionado") && ed.getVaga().equals(vaga) && ed.getData().equals(data)){
                            carrosfiltrados.add(ed);

                        }else{
                        }

                    }else if(ed.getHoraSaida().equals("Estacionado") && ed.getVaga().equals(vaga)){
                        carrosfiltrados.add(ed);

                    }else{
                    }

                }else if(ed.getHoraSaida().equals("Estacionado")){
                    carrosfiltrados.add(ed);
                }

            }else{
                if(!filtro_vaga.getText().isBlank()){
                    if(data != ""){
                        if(ed.getVaga().equals(vaga) && ed.getData().equals(data)){
                            carrosfiltrados.add(ed);

                        }else{
                        }


                    }else if(ed.getVaga().equals(vaga)){
                        carrosfiltrados.add(ed);

                    }else{
                    }

                }else if(data != ""){
                    if(ed.getData().equals(data)){
                        carrosfiltrados.add(ed);

                    }else{
                    }

                }else{
                    carrosfiltrados.add(ed);
                }
            }
        }
    }

//Metodo que executa o metodo da classe ToastNotification
        public void toastNotification(String mensagem) {
            int toastMsgTime = 1000;
            int fadeInTime = 500;
            int fadeOutTime = 500;
            toast.makeText(stage, mensagem, toastMsgTime, fadeInTime, fadeOutTime);
        }
}
