import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Dustin on 1/10/14.
 */
public class Frame extends JFrame{
    private JButton enterUrl;
    private JButton scrape;
    private Document doc;
    public Frame(){
        super("Scraper");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(200, 65));
        this.setLayout(new FlowLayout());
        this.setResizable(false);
        enterUrl = new JButton("Select URL");
        scrape = new JButton("Scrape");

        this.add(enterUrl);
        this.add(scrape);
        scrape.setEnabled(false);



        enterUrl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String url = JOptionPane.showInputDialog("Enter a url","http://www.example.com/");
                doc = null;
                try {
                    doc = Jsoup.connect(url).get();
                    scrape.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        scrape.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                scrapeCards();
            }
        });


    }

    private void scrapeCards() {
        Elements cards = doc.getElementsByTag("span");
        JFileChooser chooser = new JFileChooser();
        File file = null;
        while (file == null){
            int returnValue = chooser.showOpenDialog(this);

            if (returnValue == JFileChooser.APPROVE_OPTION){
                file = chooser.getSelectedFile();
            }

        }
        FileWriter writer = null;

        try {
            writer = new FileWriter(file);
            for (Element e: cards){
                writer.write(e.text() + "\n");
            }
            JOptionPane.showMessageDialog(this,"Scrape Successful!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                  
                 }
            }
        }
    }


    public static void main(String[] args){
        Frame frame = new Frame();
        frame.setVisible(true);
    }

}
