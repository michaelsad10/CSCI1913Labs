public class Poly
{
    private Term head;

    public Poly()
    {
        head = new Term(0,0,head);
    }

    public Poly term(int coef, int expo)
    {
        if(expo<0)
        {
            throw new IllegalArgumentException();
        }
        Term left = head;
        Term right = head.next;
        while(right!=null)
        {
            if(right.expo < expo)
            {
                left.next = new Term(coef,expo,right);
                return this;
            }
            else if(right.expo>expo)
            {
                left = right;
                right = right.next;
            }
            else
            {
                throw new IllegalArgumentException();
            }
        }
        left.next = new Term(coef,expo,right);
        return this;
    }

    public Poly plus(Poly that)
    {
        Poly temp = new Poly();

        Term current = head.next;

        while(current!=null)
        {
            temp.term(current.coef,current.expo);
            current = current.next;
        }

        current = that.head.next;

        while(current !=null)
        {
            temp.add(current.coef,current.expo);
            current = current.next;
        }

        return temp;
    }

    private void add(int coef, int expo)
    {
        if(expo<0)
        {
            throw new IllegalArgumentException(); // if Expo is neg
        }
        else
        {
            Term left = head;
            Term right = head.next;

            while(right!=null)
            {
                if (right.expo < expo)
                {
                    left.next = new Term(coef, expo, right);
                    return;
                }
                else if (right.expo == expo)
                {
                    int value = coef + right.coef;
                    if (value == 0)
                    {
                        left.next = right.next;
                        return;
                    }
                    else
                    {
                        right.coef = value;
                        return;
                    }
                }
                    else
                    {
                        left = right;
                        right = right.next;
                    }
            }
        }
    }

    public Poly minus()
    {
        Poly temp = new Poly();
        Term current = head.next;

        while(current!=null)
        {
            temp.term(-current.coef,current.expo);
            current = current.next;
        }
        return temp;
    }

    public String toString()
    {
       if(head.next == null)
       {
           return "0";
       }

       Term temp = head.next;
       StringBuilder builder = new StringBuilder();
       builder.append(temp.coef).append("x").append(temp.expo);
       temp = temp.next;
       while(temp!=null)
        {
            if(temp.coef<0)
            {
                builder.append(" - ").append(-temp.coef).append("x").append(temp.expo);
            }
            else
            {
                builder.append(" + ").append(temp.coef).append("x").append(temp.expo);
            }
            temp = temp.next;

        }

        return builder.toString();
    }

    private class Term
    {
        private int coef;
        private int expo;
        private Term next;

        private Term( int coef, int expo, Term next)
        {
           this.coef = coef;
           this.expo = expo;
           this.next = next;
        }
    }
}


class PollyEsther
{
    public static void main(String[] args)
    {
        Poly p0 = new Poly();
        Poly p1 = new Poly().term(1, 3).term(1, 1).term(1, 2);
        Poly p2 = new Poly().term(2, 1).term(3, 2);
        Poly p3 = p2.minus();
        Poly p4 = new Poly().term(1,4).term(3,2).term(3,1);
        Poly p5 = new Poly().term(2,4).term(2,2).term(2,1);
        Poly p7 = p5.minus();
        Poly p8 = new Poly().term(4,3).term(2,2).term(7,1);
        Poly p9 = new Poly().term(6,3).term(5,2).term(3,1);


        System.out.println(p0);           //  0
        System.out.println(p1);           //  1x3 + 1x2 + 1x1
        System.out.println(p2);           //  3x2 + 2x1
        System.out.println(p3);           //  −3x2 − 2x1
        System.out.println(p4);           // 1x4 + 3x2 + 3x1    ADDED
        System.out.println(p5);           // 2x4 + 2x2 + 2x1    ADDED
        System.out.println(p7);           // -2x4 - 2x2 - 2x1   ADDED
        System.out.println(p8);           // 4x3 + 2x2 + 7x1    ADDED
        System.out.println(p9);           // 6x3 + 5x2 + 3x1    ADDED


        System.out.println(p1.plus(p2));  //  1x3 + 4x2 + 3x1
        System.out.println(p1.plus(p3));  //  1x3 − 2x2 − 1x1
        System.out.println(p4.plus(p5));  //  3x4 + 5x2 + 5x1  ADDED
        System.out.println(p8.plus(p9)); //   10x3 + 7x2 + 10x1 ADDED 
    }
}