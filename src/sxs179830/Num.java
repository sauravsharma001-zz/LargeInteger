
// Starter code for lp1.
// Version 1.0 (8:00 PM, Wed, Sep 5).

// Change following line to your NetId
package sxs179830;

public class Num  implements Comparable<Num> {

    static long defaultBase = 10;  // Change as needed
    long base = 1000;  // Change as needed
    long[] arr;  // array to store arbitrarily large integers
    boolean isNegative;  // boolean flag to represent negative numbers
    int len;  // actual number of elements of array that are used;  number is stored in arr[0..len-1]

    public Num(String s) {
        if(s != null && s.length() > 0) {
            if (s.trim().charAt(0) == '-') {
                this.isNegative = true;
                s = s.substring(1);
            }
            int logVal = (int) Math.log10(base);
            len = (s.length() / logVal) + 1;
            int i = 0;
            this.arr = new long[len];
            while (s != null && s != "") {
                Long l = Long.valueOf(s.substring(s.length() > 3 ? s.length() - 3 : 0, s.length()));
                s = s.length() > 3 ? s.substring(0, s.length() - logVal) : null;
                arr[i] = l;
                i++;
            }
        }
    }

    public Num(long x) {
        if(x < 0) {
            this.isNegative = true;
        }
        this.len = (int) Math.ceil(Math.log10(x)/Math.log10(base));
        this.arr = new long[len];
        int i = 0;
        while(x % base > 0) {
            arr[i] = x % base;
            x /= base;
            i++;
        }
    }

    public static Num add(Num a, Num b) {

        StringBuilder sb = new StringBuilder();

        // XOR Operation, if one of the number is negative
        if(a.isNegative ^ b.isNegative) {
            if(a.isNegative)
                return subtract(b, a);
            else
                return subtract(a, b);
        } else {
            int i = 0, carry = 0;
            long temp;
            while(i < a.len || i < b.len) {
                temp = carry;
                if(i < a.len)
                    temp += a.arr[i];
                if(i < b.len)
                    temp += b.arr[i];
                sb.insert(0, temp % a.base());
                carry = temp > a.base() ? (int) ( temp / a.base()) : 0;
                i++;
            }
            // If both are negative then add '-' in front
            if(a.isNegative)
                sb.insert(0, "-");
        }
        return new Num(sb.toString());
    }

    public static Num subtract(Num a, Num b) {
        return null;
    }

    public static Num product(Num a, Num b) {
        return null;
    }

    // Use divide and conquer
    public static Num power(Num a, long n) {
        return null;
    }

    // Use binary search to calculate a/b
    public static Num divide(Num a, Num b) {
        return null;
    }

    // return a%b
    public static Num mod(Num a, Num b) {
        return null;
    }

    // Use binary search
    public static Num squareRoot(Num a) {
        return null;
    }


    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
    public int compareTo(Num other) {
        return 0;
    }

    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    public void printList() {
        System.out.print(base + ": ");
        for(int i = len-1; i >= 0; i--) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // Return number to a string in base 10
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(isNegative) {
            sb.append("-");
        }
        Num temp = convertBase(10);
        for(int i = temp.len-1; i >=0; i--) {
            sb.append(temp.arr[i]);
        }
        return sb.toString();
    }

    public long base() { return base; }

    // Return number equal to "this" number, in base=newBase
    public Num convertBase(int newBase) {
//        while (n > 0) {
//            nextDigit = n % newBase;  // write this into arr
//            n = n / b;
//        }
        return this;
    }

    // Divide by 2, for using in binary search
    public Num by2() {
        return null;
    }

    // Evaluate an expression in postfix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluatePostfix(String[] expr) {
        return null;
    }

    // Evaluate an expression in infix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "(", ")", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluateInfix(String[] expr) {
        return null;
    }


    public static void main(String[] args) {
        Num x = new Num(9999);
        Num y = new Num("1234567890");
        Num z = Num.add(x, y);
        System.out.println("sum: " + z);
        Num a = Num.power(x, 8);
        System.out.println(a);
        if(z != null) z.printList();
    }
}
