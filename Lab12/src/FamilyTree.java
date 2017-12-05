public class FamilyTree
{
    private class Node
    {
        private String name;
        private Node father;
        private Node mother;

        public Node(String name, Node father, Node mother)
        {
            this.name = name;
            this.father = null;
            this.mother = null;
        }
    }

    private Node root;

    public FamilyTree(String ego)
    {
       root = new Node(ego,null,null);
    }

    public boolean isEqual(Node a, Node b)
    {
        if(a == null || b == null)
        {
            return (a == b);
        }
        else
        {
            return(a.equals(b));
        }
    }

    private Node find(String name)
    {
        return(find(name,root));
    }

    private Node find(String name, Node root)
    {
        if(root!=null)
        {
            if (name.equals(root.name))
            {
                return root;
            }
            else
            {
               Node tempF = find(name, root.father);
               Node tempM = find(name, root.mother);
               if(tempF!=null)
               {
                   return tempF;
               }
               if(tempM!=null)
               {
                    return tempM;
               }
            }
        }
        return null;
    }
    public boolean isDescendant(String ego, String ancestor)
    {
        return isDescendant(find(ego),find(ancestor));
    }

    private boolean isDescendant(Node root, Node ancestor)
    {
        if (root != null) {
            if (root == ancestor) {
                return true;
            }
            boolean fTemp = isDescendant(root.father, ancestor);
            boolean mTemp = isDescendant(root.mother, ancestor);

            return fTemp || mTemp;
        }
        return false;
    }

    public void addParents(String ego, String father, String mother)
    {
        Node temp = find(ego);
        if(isEqual(temp,null))
        {
            throw new IllegalArgumentException();
        }
        else
        {
            temp.father = new Node(father,null,null);
            temp.mother = new Node(mother,null,null);
        }
    }



}


//  POTTERY. Driver class, for testing. Each comment shows a point value (for a
//  total of 40 points) and what it should print.

class Pottery
{

//  MAIN. Harry Potter and the Hairier Pottery.

    public static void main(String [] args)
    {
        FamilyTree family = new FamilyTree("Al");

        family.addParents("Al",    "Harry",  "Ginny");
        family.addParents("Harry", "James",  "Lily" );
        family.addParents("Ginny", "Arthur", "Molly");

        try
        {
            family.addParents("Joanne", "Peter", "Anne");
        }
        catch (IllegalArgumentException ignore)
        {
            System.out.println("No Joanne.");  //  2 No Joanne.
        }

        System.out.println(family.isDescendant("Joanne", "Joanne"));  //  2 false

        System.out.println(family.isDescendant("Al", "Al"));          //  2 true
        System.out.println(family.isDescendant("Al", "Harry"));       //  2 true
        System.out.println(family.isDescendant("Al", "Ginny"));       //  2 true
        System.out.println(family.isDescendant("Al", "James"));       //  2 true
        System.out.println(family.isDescendant("Al", "Lily"));        //  2 true
        System.out.println(family.isDescendant("Al", "Arthur"));      //  2 true
        System.out.println(family.isDescendant("Al", "Molly"));       //  2 true
        System.out.println(family.isDescendant("Al", "Joanne"));      //  2 false

        System.out.println(family.isDescendant("Harry", "Harry"));    //  2 true
        System.out.println(family.isDescendant("Harry", "Al"));       //  2 false
        System.out.println(family.isDescendant("Harry", "James"));    //  2 true
        System.out.println(family.isDescendant("Harry", "Lily"));     //  2 true
        System.out.println(family.isDescendant("Harry", "Ginny"));    //  2 false
        System.out.println(family.isDescendant("Harry", "Arthur"));   //  2 false
        System.out.println(family.isDescendant("Harry", "Molly"));    //  2 false
        System.out.println(family.isDescendant("Harry", "Joanne"));   //  2 false

        System.out.println(family.isDescendant("Ginny", "Arthur"));   //  2 true
        System.out.println(family.isDescendant("Arthur", "Ginny"));   //  2 false
    }
}
