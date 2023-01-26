import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class wikiGame {
    private JFrame mainFrame;
    private  String searchterm;
    private int WIDTH = 800;
    private int HEIGHT = 700;
    public JPanel P1;
    public JPanel P2;
    public JLabel UrlLabel;
    public JTextField UrlField;
    public JLabel FinderLabel;
    public JTextField FinderField;
    public JTextArea ResultsArea;
    public JScrollPane scroll;
    private boolean ImDone= false;


    public static void main(String[] args) {
        wikiGame a = new wikiGame();
        a.showEventDemo();

    }


    public wikiGame() {
        prepareGUI();

    }

    private void prepareGUI() {


        mainFrame = new JFrame("Eli's Super Java Wiki Link Finder");
        mainFrame.setSize(WIDTH, HEIGHT);

        mainFrame.setLayout(new GridLayout(2, 1));

        //inside P1
        P1 = new JPanel();
        P1.setLayout(new GridLayout(4, 1));
        UrlLabel = new JLabel("     Please input url below:");
        P1.add(UrlLabel);
        UrlField = new JTextField("https://en.wikipedia.org/wiki/Barack_Obama");
        P1.add(UrlField);
        FinderLabel = new JLabel("     Please input url to find:");
        P1.add(FinderLabel);
        FinderField = new JTextField();
        P1.add(FinderField);

        mainFrame.add(P1); //add panel 1 to mainframe grid row 1

        //inside P2
        P2 = new JPanel();
        P2.setLayout(new BorderLayout());

        ResultsArea = new JTextArea(); //create ResultsArea
        ResultsArea.setEditable(true);
        scroll = new JScrollPane(ResultsArea); //create the scroll of Results Area
        ResultsArea.setSize(400, 400);//set parameters of Results area
        ResultsArea.setLayout(null);
        P2.add(scroll, BorderLayout.CENTER);//add scroller to Panel 2

        mainFrame.add(P2); //add panel 2 to mainframe grid row 2


        mainFrame.addWindowListener(new WindowAdapter() { //create the window
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });


    }
    private void showEventDemo() {
//        headerLabel.setText("Control in action: Button");

        JButton goButton = new JButton("OK");

        goButton.setActionCommand("Ok");

        goButton.addActionListener(new ButtonClickListener());

        P2.add(goButton, BorderLayout.NORTH);

        mainFrame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("Ok")) {;
                System.out.println("Ok button clicked");

                searchterm= FinderField.getText();
                HtmlRead(UrlField.getText(),0,"*");
            }
        }
    }

    public void HtmlRead(String test, int count, String step) {
        int xResult = 0;
        int yResult = 0;
        if (ImDone==false) {


            try {
                URL url = new URL(test);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(url.openStream())
                );
                String line;
                while ((line = reader.readLine()) != null&& ImDone==false) {


                    if (line.contains("/wiki/")) {
                        xResult = line.indexOf("/wiki/");
                        yResult = line.indexOf("\"", xResult);
//                    System.out.println(xResult);
//                        String halfString;
//                        halfString = line.substring(xResult);
//                        if (halfString.contains("\"")) {
//                            yResult = line.indexOf("\"");
////                        System.out.println(yResult);
//                        }
//                    System.out.println(line);
                        String Links = line.substring(xResult, yResult);
//                    System.out.println("*****" + Links);

                        if (!Links.contains(".jpg") && Links.contains("/wiki/")) {
                            RecursionProcess("https://en.wikipedia.org" + Links, count, step+"\n"+Links);
                        }
//                        if (Links.contains(searchterm)) {
//                            ResultsArea.append(step+"\n"+Links);
//
//                        }
                    }
                }
                reader.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
    public void RecursionProcess(String TestLink, int counter, String pstep){
        System.out.println(counter+"   "+TestLink);
        if(TestLink.contains(searchterm)| counter==2){
            if(TestLink.contains(searchterm)){
                System.out.println("I found it!");
                ImDone=true;
                ResultsArea.append(pstep);
            }


            if(counter ==2){
                System.out.println("I have gone too deep");
            }
        }
        else{
            HtmlRead(TestLink, counter+1, pstep);

        }



    }






    }



