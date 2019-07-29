package com.kursx.parallator.menu;

import com.kursx.parallator.Logger;
import com.kursx.parallator.Toast;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class HelpMenu {

    public final Menu menu;

    public HelpMenu(Stage stage) {
        menu = new Menu("Помощь");
        MenuItem about = new MenuItem("О Программе");
        about.setOnAction(event -> Toast.makeText(stage, "Parallator v1.0 by KursX \n kursxinc@gmail.com", 5000));
        menu.getItems().addAll(about);
    }
}
