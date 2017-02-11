import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * Created by Marcin on 11.02.2017.
 */
public class Controller implements Initializable {
    @FXML
    private Button btnConvert;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnSelectFile;

    @FXML
    private TextArea tASource;

    @FXML
    private TextArea tATarget;

    public void initialize(URL location, ResourceBundle resources) {
        configureButtons();
    }

    void configureButtons() {
        btnConvert.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                tATarget.setText(convertFromTextArea(tASource.getText()));
            }
        });

        btnClear.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                tASource.setText("");
                tATarget.setText("");
            }
        });

        btnSelectFile.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                btnClear.fire();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Txt File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Text Files", "*.txt"));
                File selectedFile = fileChooser.showOpenDialog(null);

                if (selectedFile != null) {
                    try (FileReader fileStream = new FileReader(selectedFile);
                         BufferedReader bufferedReader = new BufferedReader(fileStream)) {
                        String line = null;
                        String allText = "";
                        while ((line = bufferedReader.readLine()) != null) {
                            allText += line + "\n";
                        }
                        tASource.setText(allText);
                        btnConvert.fire();

                    } catch (FileNotFoundException ex) {
                        //exception Handling
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        //exception Handling
                        ex.printStackTrace();
                    }
                } else {

                }
            }
        });

    }

    public String convertFromTextArea(String source) {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
        if (source.length() > 0) {
            try {
                StringReader sReader = new StringReader(source);
                parser p = new parser(new MyLexer(sReader));
                Object result = p.parse().value;
                xml += result.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Puste pole JSON.");
            alert.show();
        }
        return xml;
    }
}
