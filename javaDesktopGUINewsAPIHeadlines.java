import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class javaDesktopGUINewsAPIHeadlines {

    private static final String NEWS_API_URL = "https://newsapi.org/v2/top-headlines?country=in&apiKey=api-key"; // api key is hidden as privacy concern  

    public static void main(String[] args) {
        JFrame frame = new JFrame("Top Headlines");
        JPanel panel = new JPanel();
        JTextArea textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        panel.add(textArea);

        JButton button = new JButton("Refresh");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String json = readJsonFromUrl(NEWS_API_URL);
                    JsonParser parser = new JsonParser();
                    JsonObject obj = parser.parse(json).getAsJsonObject();
                    JsonArray articles = obj.getAsJsonArray("articles");
                    String headlines = "";
                    for (JsonElement article : articles) {
                        JsonObject articleObj = article.getAsJsonObject();
                        String title = articleObj.get("title").getAsString();
                        headlines += title + "\n\n";
                    }
                    textArea.setText(headlines);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(button);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static String readJsonFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        Scanner scanner = new Scanner(is);
        scanner.useDelimiter("\\A");
        String json = scanner.hasNext() ? scanner.next() : "";
        scanner.close();
        is.close();
        return json;
    }
}
