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
     * @param param line to parse
     */
    public DialogNode(String param) {
        int index = param.indexOf(":");
        String id = param.substring(0, index);
        this.id = Integer.parseInt(id);

        int first = param.indexOf('{');
        int last = param.lastIndexOf('}');
        String map = param.substring(first+1,last);
        String[] splitMap = map.split("^");

        for(String pair :  splitMap) {
            index = pair.indexOf(':');
            String key = pair.substring( 0, index);
            String value = pair.substring(index + 1, pair.length());

            if (key == "text") {
                text = value;
            } else if (key == "option1") {
                option1 = value;
            } else if (key == "option2") {
                option2 = value;
            } else if (key == "nextNode1") {
                nextNode1 = Integer.parseInt(value);
            } else if (key == "nextNode2") {
                nextNode2 = Integer.parseInt(value);
            } else if (key == "twoOptions") {
                twoOptions = value.equals("t");
            }
        }
    }

    /**
     * Gets the Node ID
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the next node for option 1
     * @return
     */
    public int getNextNode1() {
        return nextNode1;
    }

    /**
     * Gets the next node for option 2
     * @return
     */
    public int getNextNode2() {
        return nextNode2;
    }

    /**
     * Gets option 1
     * @return
     */
    public String getOption1() {
        return option1;
    }

    /**
     * Gets option 2
     * @return
     */
    public String getOption2() {
        return option2;
    }

    /**
     * Gets the text box text
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * Gets whether or not there are 2 options
     * @return
     */
    public boolean isTwoOptions() {
        return twoOptions;
    }
}
