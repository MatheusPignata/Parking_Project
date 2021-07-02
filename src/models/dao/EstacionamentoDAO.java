//Classe responsavel por salvar os dados do programa no arquivo
package models.dao;

import models.Estacionamento;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class EstacionamentoDAO {

    private BufferedReader br;
    private static BufferedWriter bw;
    private ArrayList<Estacionamento> itens;
    private Estacionamento item;
    private static final String archive = "./bd/Estacionamento.csv";
    public ArrayList<Estacionamento> abrir() {

        itens = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(archive));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] line_true = line.split(";");
                item = new Estacionamento(line_true[0], line_true[1], line_true[2], line_true[3], line_true[4], line_true[5], line_true[6]);
                itens.add(item);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Erro: " + e);
        }
        return itens;
    }

    public static boolean salvar(ArrayList<Estacionamento> et){
        try{
            bw = new BufferedWriter(new FileWriter(archive, false));
            for(Estacionamento i:  et){
                bw.write(i.toCsv());
            }
            bw.close();
            return true;
        }catch(Exception e) {
            System.out.println("Erro: " + e);
            return false;
        }
    }
}

