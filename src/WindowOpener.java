import javax.swing.*;
import java.awt.*;
import java.io.File;

public class WindowOpener extends Component {
    public String openWindow(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());

            return selectedFile.getAbsolutePath();
        }
        return "";
    }
}
