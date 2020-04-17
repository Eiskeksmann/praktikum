package GUI;

import javax.swing.*;
import java.awt.*;

public class GUIFrame extends JFrame {

    private GUIPanel content;

    public GUIFrame(String title, Dimension d){

        this.setTitle(title);
        switch (title){
            case("SERVER"):
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setContentPane(content = new GUIServerPanel(d.width, d.height));
                break;
            case("LOGIN"):
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setContentPane(content = new GUILoginPanel(d.width, d.height));
                break;
            case("CLIENT"):
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setContentPane(content = new GUIClientPanel(d.width, d.height));
                break;
            case("VIERGEWINNT"):
                //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setContentPane(content = new GUIVierGewinnt(d.width, d.height));
                break;
            case("FUTTERN"):
                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setContentPane(content = new GUIFuttern(d.width, d.height));
                break;
            case("SETTINGS"):
                //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setContentPane(content = new GUIGameSettings(d.width, d.height));
                break;
            case("MINICONFIRM"):
                //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setContentPane(content = new GUIMiniConfirmation(d.width, d.height));
                break;

            default:
                this.setContentPane(content = new GUIMiniSelection(d.width, d.height));
                break;
        }

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public GUIPanel getContent(){return content;}

    public void close(){
        this.dispose();
    }
}
