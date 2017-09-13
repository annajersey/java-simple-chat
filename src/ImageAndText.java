import javax.swing.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageAndText implements Serializable {
    private String text;
    private ImageIcon image = new ImageIcon("D:/teddy.jpg");
    ImageAndText(User user){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(new Date());
        text = date+" "+user.getUserName()+": "+user.getMessage();
        image = new ImageIcon("D:/"+user.getUserPick());

    }

    public String getText() {
        return text;
    }

    public ImageIcon getImage() {
        return image;
    }


}