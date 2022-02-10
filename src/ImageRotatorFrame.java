
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * class for the JFrame of the program
 */
public class ImageRotatorFrame extends JFrame{

    /**
     * used to toggle the continuous spin on the picture
     */
    private final JCheckBox continuousSpin;

    /**
     * used to inform the user what the units and purpose of the slider is
     */
    private final JLabel sliderText;

    /**
     * used to change the spin speed of the picture, can go from 0-30 seconds per full rotation
     */
    private final JSlider spinSpeed;

    /**
     * used to inform the user what they need to input into the text box
     */
    private final JLabel degreeInputInstruction;

    /**
     * A text box for the user to input how many degrees they want to rotate the picture
     */
    private final JTextField rotateDegree;

    /**
     * button the rotate the picture it's current rotation plus rotation degree
     */
    private final JButton rotateButton;

    /**
     * the picture to be rotated (RotatablePicture is an extension of JLabel defined later)
     */
    private final RotatablePicture picture;

    /**
     * A label to inform the user of any errors that may occur
     */
    private final JLabel errorField;

    /**
     * timer to control the speed at witch the picture is continuously spinning
     */
    Timer timer;

    /**
     * constructor for the frame, initialises all of the components and has
     * handlers for their events.
     */
    public ImageRotatorFrame(){

        super("Image Rotator"); // creates the frame with title Image Rotator
        setLayout(new FlowLayout());

        continuousSpin = new JCheckBox("Start continuous spin");
        add(continuousSpin); // adds a checkbox labeled start continuous spin the the frame

        sliderText = new JLabel("     seconds per full rotation:");
        add(sliderText); // adds a label for the user to understand the slider

        spinSpeed = new JSlider(JSlider.HORIZONTAL,0,30,15); // creates a slider from 0-30 with starting value 15
        spinSpeed.setMajorTickSpacing(5); // sets the major tick marks to be 5 apart
        spinSpeed.setMinorTickSpacing(1); // sets the minor tick marks to be 1 apart
        spinSpeed.setPaintLabels(true); // makes the numbers corresponding to the ticks visible to the user
        spinSpeed.setPaintTicks(true); // makes the tick marks visible to the user
        add(spinSpeed); // adds the slider the frame

        degreeInputInstruction = new JLabel("                     input number of degrees to rotate than press rotate:");
        add(degreeInputInstruction); // adds a label to the frame to inform the user of the input box

        rotateDegree = new JTextField(5);
        rotateDegree.setFocusable(true);
        add(rotateDegree); // adds a text box for the user to input to the frame

        rotateButton = new JButton("rotate");
        add(rotateButton); // adds a rotate button to the frame

        errorField = new JLabel("");
        add(errorField); // adds the initially empty error field to the frame

        Icon bug = new ImageIcon(getClass().getResource("bugPicture.png")); // gets the bug image from the resources folder
        picture = new RotatablePicture(bug);
        add(picture); // adds the picture to the frame

        timer = new Timer(0, new ActionListener() { // creates the timer and implements the action listener for the timer
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                picture.setRotationDegree(picture.getRotationDegree() + Math.PI/180 ); // adds 1 degree in radians to the rotation degree
                picture.repaint(); // rotates the image the corresponding amount
            }
        });

        rotateButton.addActionListener(new ActionListener() { // creates the action listener for the button press
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                errorField.setText(""); // resets error text
                int degree;
                try{
                    degree = Integer.parseInt(rotateDegree.getText()); // makes sure the input is an integer
                }catch (NumberFormatException e){
                    errorField.setText("error: input an integer from 0-360 in the text box to rotate");
                    rotateDegree.setText(""); // makes corresponding error message and returns out of the action listener
                    return;
                }
                if(degree > 360 || degree < 0){ // checks if the degree input is in the 0-360 range
                    errorField.setText("error: input an integer from 0-360 in the text box to rotate");
                    rotateDegree.setText("");
                    return;
                }
                picture.setRotationDegree(degree * Math.PI/180 + picture.getRotationDegree()); // adds the degree to the current rotation in radians
                picture.repaint(); // rotates the picture the corresponding amount
            }
        });

        continuousSpin.addItemListener(new ItemListener() { // crates the item listener for when the checkbox is integrated with
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                errorField.setText(""); // resets the error text
                if(!continuousSpin.isSelected()){
                    timer.stop(); // stops the timer if unchecked
                }else{
                    int delay = (int)(Math.floor((1.0/360.0)*spinSpeed.getValue()*1000.0));
                    timer.setDelay(delay); // sets the delay and starts the timer otherwise
                    timer.start();
                }
            }
        });

        spinSpeed.addChangeListener(new ChangeListener() { // creates the change listener for when the slider is adjusted
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                int delay = (int)(Math.floor((1.0/360.0)*spinSpeed.getValue()*1000.0));
                timer.setDelay(delay); // just sets the delay on the timer to the correct value
            }
        });



    }

    /**
     * private class to define the the rotatable picture rotation
     */
    private class RotatablePicture extends JLabel{

        /**
         * stores the the current rotation in radians
         */
        Double rotationDegree;

        /**
         * constructor for just a picture with no initial rotation
         * @param icon the picture to be used for label
         */
        public RotatablePicture(Icon icon){
            this(icon,0);
        }

        /**
         * constructor for a picture with an initial rotation
         * @param icon the picture to be used for the label
         * @param degree the degree of initial rotation
         */
        public RotatablePicture(Icon icon, int degree){
            super(icon);
            rotationDegree = degree * Math.PI/180;
        }

        /**
         * getter for the rotation degree
         * @return rotationDegree
         */
        public Double getRotationDegree() {
            return rotationDegree;
        }

        /**
         * setter for the rotation degree
         * @param rotationDegree the new value for rotation degree in radians
         */
        public void setRotationDegree(Double rotationDegree) {
            this.rotationDegree = rotationDegree;
        }


        /**
         * overrides the paint component method to have it rotate the correct degree every time it's repainted
         * @param g
         */
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.rotate(rotationDegree, this.getWidth()/2, this.getHeight()/2);
            super.paintComponent(g);
        }
    }
}

