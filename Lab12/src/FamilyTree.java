public class FamilyTree
{
    private class Node
    {
        private String name;
        private Node father;
        private Node mother;

        private Node(String name, Node father, Node mother)
        {
            this.name = name;
            this.father = null;
            this.mother = null;
        }
    }

    private Node root;
    private Node head;

    public FamilyTree(String ego)
    {
        root.name = ego;
        root.father = null;
        root.mother = null;
        head = root;
    }
    private Node find(String name)
    {

    }

    private Node find(String name, Node root)
    {
        Node temp = root;
        if(root!=null)
        {
            if(temp.name==name)
            {
                return temp;
            }
            else
            {
                temp = root.father;
                find(name,temp.father);
                find(name,temp.mother);
            }
        }
        return null;
    }

    public void addParents(String ego, String father, String mother)
    {

    }

    public boolean isDescendant(String ego, String ancestor)
    {

    }

    private boolean isDescendant(Node root, Node ancestor)
    {

    }
}
