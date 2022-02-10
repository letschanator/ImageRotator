
import javax.swing.JFrame;

/**
 * runner class of the image rotator frame
 */
public class ImageRotatorRunner {

    /**
     * main method to construct a image rotator frame and show it to the user
     * @param args
     */
    public static void main(String args[]){
        ImageRotatorFrame imageRotatorFrame = new ImageRotatorFrame();
        imageRotatorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        imageRotatorFrame.setSize(650,200);
        imageRotatorFrame.setResizable(false);
        imageRotatorFrame.setVisible(true);

    }
}
