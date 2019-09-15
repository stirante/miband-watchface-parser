package com.stirante.watchface.miband;

import com.google.gson.Gson;
import com.stirante.watchface.miband.parser.Watchface;
import com.stirante.watchface.miband.parser.WatchfaceResource;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) throws IOException {
        launch(args);
//        File f = new File("example.bin");
//        //File f1 = new File("test.bin");
//        FileInputStream in = new FileInputStream(f);
//        //FileOutputStream out = new FileOutputStream(f1);
//        Watchface w = new Watchface();
//        w.read(in);
//        System.out.println(w);
//        ArrayList<WatchfaceResource> resources = w.getResources();
//        File dir = new File("images");
//        dir.mkdir();
////        for (int i = 0; i < resources.size(); i++) {
////            Image resource = resources.get(i).getImage();
////            ImageIO.write((RenderedImage) resource, "png", new File(dir, i + "." + resources.get(i).getType().name() + ".png"));
////
////        }
//        in.close();
//        MiBand4 band = new MiBand4(w);
//        band.fromWatchface();
//        System.out.println(new Gson().toJson(band));
//        Canvas c = new Canvas(120, 240);
//        band.render(c.getGraphicsContext2D());
//        //w.write(out);
//        //out.flush();
//        //out.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        File f = new File("circle.bin");
        FileInputStream in = new FileInputStream(f);
        Watchface w = new Watchface();
        w.read(in);
        in.close();
        MiBand4 band = new MiBand4(w);
        band.fromWatchface();
        System.out.println(new Gson().toJson(band));
        Canvas c = new Canvas(120, 240);
        band.render(c.getGraphicsContext2D());

        Pane pane = new Pane(c);
        Scene scene = new Scene(pane, 120, 240);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
