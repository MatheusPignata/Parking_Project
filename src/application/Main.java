//Classe responsavel por executar a tela.
package application;

import controllers.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

//Metodo que executa o arquivo .fxml
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/tela_programa.fxml"));
        primaryStage.getIcons().add(new Image("./view/logo.png"));
        primaryStage.setTitle("STOPCar");
        primaryStage.setScene(new Scene(root, 1210, 620));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        Controller.abrir();
        
    }
}
