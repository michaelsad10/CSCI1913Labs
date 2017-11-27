public class Deque<Base>
{

    private class Node
    {
        private Node left;
        private Node right;
        private Base object;

        public Node( Node left, Node right, Base object)
        {
            this.left = left;
            this.right = right;
            this.object = object;
        }
    }

    Node head;

    public Deque()
    {
        head = new Node(null,null,null);
        head.right = head;
        head.left = head;
    }

    public void enqueueFront(Base object)
    {
        if(isEmpty())
        {
            Node where = new Node(head,head,object);
            head.left = where;
            head.right = where;
        }
        else
        {
            Node where = head.right;
            head.right = new Node(head,where,object);
            where.left = head.right;

        }
    }

    public void enqueueRear(Base object)
    {
        if(isEmpty())
        {
            head.right = new Node(head,head,object);
            Node where = head.right;
            head.left = where;
        }
        else
        {
            Node where = head.left;
            head.left = new Node(where,head,object);
            where.right = head.left;
        }
    }

    public Base dequeueFront()
    {
        if(isEmpty())
        {
            throw new IllegalStateException();
        }
        else
        {
            Node where = head.right;
            if(where.right == head) // If there is only one Node
            {
                head.right = head;
                head.left = head;
                return where.object;
            }
            else // If there is more than one Node
            {
                Base value = where.object;
                where = where.right;
                head.right = where;
                where.left = head;
                return value;
            }
        }
    }

    public Base dequeueRear()
    {
        if(isEmpty())
        {
            throw new IllegalStateException();
        }
        else
        {
            Node where = head.left;
            if(where.left == head) // If there is only one Node
            {
                head.right = head;
                head.left = head;
                return where.object;
            }
            else // If there is more than one Node
            {
                Base value = where.object;
                where = where.left;
                head.left = where;
                where.right=head;
                return value;
            }
        }
    }

    public boolean isEmpty()
    {
        if(head.right == head && head.left == head)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}




//  OBSERVATION DEQUE. Test the class DEQUE. 40 points total.

class ObservationDeque
{

//  MAIN. Test the DEQUE on various example arguments.

    public static void main(String [] args)
    {
        Deque<String> deque = new Deque<String>();

        System.out.println(deque.isEmpty());       // true                2 points.

        try
        {
            System.out.println(deque.dequeueFront());
        }
        catch (IllegalStateException ignore)
        {
            System.out.println("No dequeueFront.");  //  No dequeueFront.   2 points.
        }

        try
        {
            System.out.println(deque.dequeueRear());
        }
        catch (IllegalStateException ignore)
        {
            System.out.println("No dequeueRear.");   //  No dequeueRear.    2 points.
        }

//  Enqueueing to the rear and dequeueing from the rear makes the DEQUE act
//  like a stack.

        deque.enqueueRear("A");
        deque.enqueueRear("B");
        deque.enqueueRear("C");

        System.out.println(deque.isEmpty());       //  false              2 points.

        System.out.println(deque.dequeueRear());   //  C                  2 points.
        System.out.println(deque.dequeueRear());   //  B                  2 points.
        System.out.println(deque.dequeueRear());   //  A                  2 points.

        System.out.println(deque.isEmpty());       //  true               2 points.

//  Enqueueing to the rear and dequeueing from the front makes the DEQUE act
//  like a queue.

        deque.enqueueRear("A");
        deque.enqueueRear("B");
        deque.enqueueRear("C");

        System.out.println(deque.dequeueFront());  //  A                  2 points.
        System.out.println(deque.dequeueFront());  //  B                  2 points.
        System.out.println(deque.dequeueFront());  //  C                  2 points.

        System.out.println(deque.isEmpty());       //  true               2 points.

//  Enqueueing to the front and dequeueing from the front makes the DEQUE act
//  like a stack.

        deque.enqueueFront("A");
        deque.enqueueFront("B");
        deque.enqueueFront("C");

        System.out.println(deque.dequeueFront());  //  C                  2 points.
        System.out.println(deque.dequeueFront());  //  B                  2 points.
        System.out.println(deque.dequeueFront());  //  A                  2 points.

        System.out.println(deque.isEmpty());       //  true               2 points.

//  Enqueueing to the front and dequeueing from the rear makes the DEQUE act
//  like a queue.

        deque.enqueueFront("A");
        deque.enqueueFront("B");
        deque.enqueueFront("C");

        System.out.println(deque.dequeueRear());   //  A                  2 points.
        System.out.println(deque.dequeueRear());   //  B                  2 points.
        System.out.println(deque.dequeueRear());   //  C                  2 points.

        System.out.println(deque.isEmpty());       //  true               2 points.
    }
}