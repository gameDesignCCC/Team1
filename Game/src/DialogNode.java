import java.util.Arrays;

public class DialogNode {
    private int id;             // The ID of the Node
    private int nextNode1;      // The ID of the Node to go to when clicking option 1
    private int nextNode2;      // The ID of the Node to go to when clicking option 2
    private String text;        // The text of the text box
    private String option1;     // The text for the first option
    private String option2;     // The text for the second option
    private boolean twoOptions; // Whether or not there are 2 options

    /**
     * Constructor
     * Example line:
     * 0:{text:This is a node^option1:Option1 text^option2:Option2 text^nextNode1:1^nextNode2:2}
     * @param param line to parse
     */
    public DialogNode(String param) {
        int index = param.indexOf(":");
        String id = param.substring(0, index);
        this.id = Integer.parseInt(id);

        int first = param.indexOf('{');
        int last = param.lastIndexOf('}');
        String map = param.substring(first+1,last);
        String[] splitMap = map.split("\\^");
        System.out.println(Arrays.toString(splitMap));

        for(String pair :  splitMap) {
            index = pair.indexOf(':');
            String key = pair.substring( 0, index);
            String value = pair.substring(index + 1, pair.length());

            if (key.equals("text")) {
                text = value;
            } else if (key.equals("option1")) {
                option1 = value;
            } else if (key.equals("option2")) {
                option2 = value;
            } else if (key.equals("nextNode1")) {
                nextNode1 = Integer.parseInt(value);
            } else if (key.equals("nextNode2")) {
                nextNode2 = Integer.parseInt(value);
            } else if (key.equals("twoOptions")) {
                twoOptions = value.equals("t");
            } else {
                System.out.println("Skipping invalid key: " + key + ".");
            }
        }
    }

    /**
     * Gets the Node ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the next node for option 1
     * @return nextNode1
     */
    public int getNextNode1() {
        return nextNode1;
    }

    /**
     * Gets the next node for option 2
     * @return nextNode2
     */
    public int getNextNode2() {
        return nextNode2;
    }

    /**
     * Gets option 1
     * @return option1
     */
    public String getOption1() {
        return option1;
    }

    /**
     * Gets option 2
     * @return option2
     */
    public String getOption2() {
        return option2;
    }

    /**
     * Gets the text box text
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * Gets whether or not there are 2 options
     * @return twoOptions
     */
    public boolean isTwoOptions() {
        return twoOptions;
    }
}
