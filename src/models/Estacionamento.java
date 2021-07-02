//Classe modelo responsavel por definir os atributos do objeto
package models;

import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Estacionamento {
    private final SimpleStringProperty modelo;
    private final SimpleStringProperty cor;
    private final SimpleStringProperty vaga;
    private final SimpleStringProperty placa;
    private final SimpleStringProperty data;
    private final SimpleStringProperty horaEntrada;
    private final SimpleStringProperty horaSaida;
    public static double valorHora = 10;

//Construtores
    public Estacionamento(String modelo, String cor, String vaga, String placa, String data, String horaEntrada, String horaSaida) {
        this.modelo = new SimpleStringProperty(modelo);
        this.cor = new SimpleStringProperty(cor);
        this.vaga = new SimpleStringProperty(vaga);
        this.placa = new SimpleStringProperty(placa);
        this.data = new SimpleStringProperty(data);
        this.horaEntrada = new SimpleStringProperty(horaEntrada);
        this.horaSaida = new SimpleStringProperty(horaSaida);
    }

//Getters and Setters, responsaveis pelo acesso aos atributos
    public String getModelo() {
        return modelo.get();
    }

    public SimpleStringProperty modeloProperty() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo.set(modelo);
    }

    public String getCor() {
        return cor.get();
    }

    public SimpleStringProperty corProperty() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor.set(cor);
    }

    public String getVaga() {
        return vaga.get();
    }

    public SimpleStringProperty vagaProperty() {
        return vaga;
    }

    public void setVaga(String vaga) {
        this.vaga.set(vaga);
    }

    public String getPlaca() {
        return placa.get();
    }

    public SimpleStringProperty placaProperty() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa.set(placa);
    }

    public String getData() {
        return data.get();
    }

    public SimpleStringProperty dataProperty() {
        return data;
    }

    public void setData(String data) {
        this.data.set(data);
    }

    public String getHoraEntrada() {
        return horaEntrada.get();
    }

    public SimpleStringProperty horaEntradaProperty() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada.set(horaEntrada);
    }

    public String getHoraSaida() {
        return horaSaida.get();
    }

    public SimpleStringProperty horaSaidaProperty() {
        return horaSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida.set(horaSaida);
    }

    public static double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        this.valorHora = valorHora;
    }


//Metodo que organiza a String que salva os dados
    public String toCsv() {
        return modelo.getValue()+ ";" + cor.getValue() + ";" + vaga.getValue() + ";" + placa.getValue() + ";" + data.getValue() + ";" + horaEntrada.getValue() + ";" + horaSaida.getValue() + "\n";
    }


//Metodo usado para calcular o valor/hora
    public static double getTotal(String horaEntrada, String horaSaida) {

        String[] horaEntradaSeparada = horaEntrada.split(":");
        String[] horaSaidaSeparada = horaSaida.split(":");

        double tot;

        tot = (Integer.parseInt(horaSaidaSeparada[0]) - Integer.parseInt(horaEntradaSeparada[0])) * 60 + (Integer.parseInt(horaSaidaSeparada[1]) - Integer.parseInt(horaEntradaSeparada[1]));
        tot = tot/60 * getValorHora();

        return tot;
    }


//Metodo usado para pegar e formatar a data do computador
    public static String GetData(){
        DateTimeFormatter data = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        return data.format(now);
    }


//Metodo usado para pegar e formatar a hora do computador
    public static String GetHora(){
        DateTimeFormatter hora = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return hora.format(now);
    }
}
