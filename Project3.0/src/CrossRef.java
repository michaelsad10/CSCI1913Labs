import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//TEST is all the way at the bottom
class CrossRef
{
    private class Node
    {
        private String name;
        private List values;
        private List last;
        private Node left;
        private Node right;


        Node(String name, List values, List last)
        {
            this.name = name;
            this.values = values;
            this.last = last;
            this.left = null;
            this.right = null;
        }

        public String toString()
        {
            StringBuilder line = new StringBuilder();
            StringBuilder stringBuilder = new StringBuilder();
            while(values!=null)
            {
                line.append(String.format("%05d", values.value)).append(" ");
                values = values.next;
            }
            stringBuilder.append(String.format("%-30s %5s", name, line));
            return stringBuilder.toString();
        }
    }



    private class List
    {

        public List next;
        public int value;

        List(int value, List next)
        {
            this.value = value;
            this.next = next;
        }

    }

    Node root;

    CrossRef()
    {
        root = null;
    }


    public void addBST(String name, int line) {
        List last = new List(line, null);
        if (root == null) // If the root is empty
        {
            root = new Node(name, last, last);
        } else {
            Node temp = root;
            while (true) {
                int test = name.compareTo(temp.name);
                if (test > 0) // Go right
                {
                    if (temp.right == null) {
                        temp.right = new Node(name, last, last);
                        return;
                    } else {
                        temp = temp.right;
                    }
                } else if (test < 0) // go left
                {
                    if (temp.left == null) {
                        temp.left = new Node(name, last, last);
                        return;
                    } else {
                        temp = temp.left;
                    }
                } else
                {
                    if (temp.last.value != line)
                    {
                        temp.last.next = new List(line, null);
                        temp.last = temp.last.next;
                    }
                    return;
                }
            }
        }
    }

    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        inOrder(root,stringBuilder);
        return stringBuilder.toString();
    }

    private void inOrder(Node root,StringBuilder stringBuilder)
    {
        if(root!=null)
        {
            inOrder(root.left,stringBuilder);
            stringBuilder.append(root.toString()).append("\n");
            inOrder(root.right,stringBuilder);
        }

    }


}


class CrossTest {
    public static void main(String[] args) {
        String path;

        if (args.length > 1)
        {
            throw new IllegalArgumentException("Only 1 argument");
        }
        else
        {
            path = args[0];
            Nomenclator reader = new Nomenclator(path, true);
            CrossRef crossReference = new CrossRef();

            while (reader.hasNext()) {
                crossReference.addBST(reader.nextName(), reader.nextNumber());
            }

            System.out.println(crossReference.toString());
        }

    }
}



/**
 * Correct version of Nomenclator
 */
class Nomenclator
{
    private char ch;                 //  Current CHAR from READER.
    private static final char eof = (char) 0x00;  //  End of file sentinel.
    private static final char eol = (char) 0x0A;  //  End of line sentinel.
    private int index;              //  Index into LINE.
    private String line;               //  Current LINE from READER.
    private boolean listing;            //  Are we listing the file?
    private String name;               //  Current name.
    private int number;             //  Current line number.
    private String path;               //  Pathname to READER's file.
    private BufferedReader reader;             //  Read CHARs from here.

    /**
     * Correct version of Nomenclator
     */
//  Constructor. Initialize a new NOMENCLATOR that reads from a text file whose
//  pathname is PATH. If we can't open it then throw an exception. LISTING says
//  whether we should copy the file to standard output as we read it.

    public Nomenclator(String path, boolean listing)
    {
        try
        {
            index = 0;
            line = "";
            this.listing = listing;
            number = 0;
            this.path = path;
            reader = new BufferedReader(new FileReader(path));
            skipChar();
        } catch (IOException ignore)
        {
            throw new IllegalArgumentException("Can't open '" + path + "'.");
        }
    }

//  HAS NEXT. Test if there's another name waiting to be read. If so, then read
//  it, so NEXT NAME and NEXT NUMBER can return it and its line number later.

    public boolean hasNext()
    {
        while (true)
        {
            if (Character.isJavaIdentifierStart(ch))
            {
                skipName();
                return true;
            }
            else if (Character.isDigit(ch))
            {
                skipNumber();
            } else
            {
                switch (ch)
                {
                    case eof:
                    {
                        return false;
                    }
                    case '"':
                    case '\'': {
                        skipDelimited();
                        break;
                    }
                    case '/': {
                        skipComment();
                        break;
                    }
                    default: {
                        skipChar();
                        break;
                    }
                }
            }
        }
    }

//  NEXT NAME. If HAS NEXT was true, then return the next name. If HAS NEXT was
//  false, then return an undefined string.

    public String nextName() {
        return name;
    }

//  NEXT NUMBER. If HAS NEXT was true, then return the line number on which the
//  next name appears. If HAS NEXT was false, then return an undefined INT.

    public int nextNumber() {
        return number;
    }

//  SKIP CHAR. If no more CHARs remain unread in LINE, then read the next line,
//  adding an EOL at the end. If no lines can be read, then read a line with an
//  EOF char in it. Otherwise just read the next char from LINE and return it.

    private void skipChar() {
        if (index >= line.length()) {
            index = 0;
            number += 1;
            try {
                line = reader.readLine();
                if (line == null) {
                    line = "" + eof;
                } else {
                    if (listing) {
                        System.out.format("%05d ", number);
                        System.out.println(line);
                    }
                    line += eol;
                }
            } catch (IOException ignore) {
                line = "" + eof;
            }
        }
        ch = line.charAt(index);
        index += 1;
    }

//  SKIP COMMENT. We end up here if we read a '/'. If it is followed by another
//  '/', or by a '*', then we skip a comment. We must skip comments so that any
//  names that appear in them will be ignored.

    private void skipComment() {
        skipChar();
        if (ch == '*') {
            skipChar();
            while (true) {
                if (ch == '*') {
                    skipChar();
                    if (ch == '/') {
                        skipChar();
                        return;
                    }
                } else if (ch == eof) {
                    return;
                } else {
                    skipChar();
                }
            }
        } else if (ch == '/') {
            skipChar();
            while (true) {
                if (ch == eof) {
                    return;
                } else if (ch == eol) {
                    skipChar();
                    return;
                } else {
                    skipChar();
                }
            }
        }
    }

//  SKIP DELIMITED. Skip a string constant or a character constant, so that any
//  names that appear inside them will be ignored.  Throw an exception if there
//  is a missing delimiter at the end.

    private void skipDelimited() {
        char delimiter = ch;
        skipChar();
        while (true) {
            if (ch == delimiter) {
                skipChar();
                return;
            } else {
                switch (ch) {
                    case eof:
                    case eol: {
                        throw new IllegalStateException("Bad string in '" + path + "'.");
                    }
                    case '\\': {
                        skipChar();
                        if (ch == eol || ch == eof) {
                            throw new IllegalStateException("Bad string in '" + path + "'.");
                        } else {
                            skipChar();
                        }
                        break;
                    }
                    default: {
                        skipChar();
                        break;
                    }
                }
            }
        }
    }

//  SKIP NAME. Skip a name, but convert it to a STRING, stored in NAME.

    private void skipName() {
        StringBuilder builder = new StringBuilder();
        while (Character.isJavaIdentifierPart(ch)) {
            builder.append(ch);
            skipChar();
        }
        name = builder.toString();
    }

//  SKIP NUMBER. Skip something that might be a number. It starts with a digit,
//  followed by zero or more letters and digits. We must do this so the letters
//  aren't treated as names.

    private void skipNumber() {
        skipChar();
        while (Character.isJavaIdentifierPart(ch)) {
            skipChar();
        }
    }

//  MAIN. Get a file pathname from the command line. Read a series of names and
//  their line numbers from the file, and write them one per line. For example,
//  the command "java Nomenclator Nomenclator.java" reads names from the source
//  file you are now looking at. This method is only for debugging!

    public static void main(String[] args) {
        Nomenclator reader = new Nomenclator(args[0], true);
        while (reader.hasNext()) {
            System.out.println(reader.nextNumber() + " " + reader.nextName());
        }
    }
}




///// I used my lab13 code as a test which is below

/*class PriorityQueue<Base> {
    private class Node
    {
        private Base object;
        private int rank;
        private Node left;
        private Node right;
        private Node(Base object, int rank)
        {
            this.object = object;
            this.rank = rank;
            this.left = null;
            this.right = null;
        }
    }
    private Node root;
    public PriorityQueue()
    {
        root = new Node(null,7);
    }
    public Base dequeue()
    {
        if(isEmpty())
        {
            throw new IllegalStateException();
        }
        else
        {
            Node above = root;
            Node below = root.left;
            while(below.left!=null)
            {
                above = below;
                below = below.left;
            }
            above.left = below.right;
            return below.object;
        }
    }
    public void enqueue(Base object, int rank)
    {
        if(rank<0)
        {
            throw new IllegalArgumentException();
        }
        else if (root == null)
        {
            root = new Node(object,rank);
        }
        else
        {
            Node temp = root;
            while(true)
            {
                int test = rank - temp.rank;
                if(test<=0) // go left
                {
                    if(temp.left == null)
                    {
                        temp.left = new Node(object, rank);
                        return;
                    }
                    else
                    {
                        temp = temp.left;
                    }
                }
                else if(test>0) // go right
                {
                    if(temp.right == null)
                    {
                        temp.right = new Node(object, rank);
                        return;
                    }
                    else
                    {
                        temp = temp.right;
                    }
                }
            }
        }
    }
    public boolean isEmpty()
    {
        return root.left == null && root.right == null;
    }
}*/


//// This is the output I got
/*
    Base                               00001 00005 00010 00025 00044
    IllegalArgumentException           00049
    IllegalStateException              00029
    Node                               00003 00007 00008 00010 00018 00022 00033 00034 00053 00057 00065 00077
    PriorityQueue                      00001 00020
    above                              00033 00037 00040
    below                              00034 00035 00037 00038 00040 00041
    boolean                            00088
    class                              00001 00003
    dequeue                            00025
    else                               00031 00051 00055 00068 00073 00080
    enqueue                            00044
    if                                 00027 00047 00051 00061 00063 00073 00075
    int                                00006 00010 00044 00060
    isEmpty                            00027 00088
    left                               00007 00014 00034 00035 00038 00040 00063 00065 00070 00090
    new                                00022 00029 00049 00053 00065 00077
    null                               00014 00015 00022 00035 00051 00063 00075 00090
    object                             00005 00010 00012 00041 00044 00053 00065 00077
    private                            00003 00005 00006 00007 00008 00010 00018
    public                             00020 00025 00044 00088
        rank                           00006 00010 00013 00044 00047 00053 00060 00065 00077
        return                         00041 00066 00078 00090
        right                          00008 00015 00040 00075 00077 00082 00090
        root                           00018 00022 00033 00034 00051 00053 00057 00090
        temp                           00057 00060 00063 00065 00070 00075 00077 00082
        test                           00060 00061 00073
        this                           00012 00013 00014 00015
        throw                          00029 00049
        true                           00058
        void                           00044
        while                          00035 00058
        */