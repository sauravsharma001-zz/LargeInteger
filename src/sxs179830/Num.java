
// Starter code for lp1.
// Version 1.0 (8:00 PM, Wed, Sep 5).

// Change following line to your NetId
package sxs179830;

import java.util.Scanner;

public class Num  implements Comparable<Num> {

    static long defaultBase = 1000;  // Change as needed
    long base = 1000;  // Change as needed
    long[] arr;  // array to store arbitrarily large integers
    boolean isNegative;  // boolean flag to represent negative numbers
    int size;  // actual number of elements of array that are used;  number is stored in arr[0..len-1]

    public Num(String s) {
        if(s != null && s.length() > 0) {
            if (s.trim().charAt(0) == '-') {
                this.isNegative = true;
                s = s.substring(1);
            }
            int logVal = (int) Math.log10(defaultBase);
            size = (s.length() / logVal) + 1;
            int i = 0;
            this.arr = new long[size];
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
            x = x * -1;
        }
        this.size = (int) Math.ceil(Math.log10(x)/Math.log10(base));
        this.arr = new long[size];
        int i = 0;
        while(x % base > 0) {
            arr[i] = x % base;
            x /= base;
            i++;
        }
    }

    public Num(long[] arr, long base, boolean isNegative) {
        this.arr = arr;
        this.base = base;
        this.isNegative = isNegative;
        this.size = arr.length;
    }

    public static Num add(Num a, Num b) {

        StringBuilder sb = new StringBuilder();
        Num addition = null;
        // XOR Operation, if one of the number is negative
        if(a.isNegative ^ b.isNegative) {
            if(a.isNegative) {
                a.isNegative = false;
                addition = subtract(a, b);
                addition.isNegative = true;
                a.isNegative = true;
            }
            else {
                b.isNegative = false;
                addition = subtract(a, b);
                b.isNegative = true;
            }
        } else {
           addition = addWithNoSign(a, b);
            // If both are negative then add '-' in front
            if(a.isNegative)
                addition.isNegative = true;
        }
        return addition;
    }

    public static Num addWithNoSign(Num a, Num b) {
        long[] arr = new long[a.size() > b.size() ? a.size() + 1: b.size() + 1];
        int i = 0, carry = 0;
        long temp;
        while(i < a.size() || i < b.size()) {
            temp = carry;
            if(i < a.size())
                temp += a.arr[i];
            if(i < b.size())
                temp += b.arr[i];
            arr[i] = temp % a.base();
            System.out.println(i + "  " + arr[i]);
            carry = temp >= a.base() ? (int) ( temp / a.base()) : 0;
            i++;
        }
        if(carry != 0)
            arr[i] = 1;
        return new Num(arr, a.base(), false);
    }

    public static Num subtract(Num a, Num b) {
        StringBuilder sb = new StringBuilder();
        Num val = null;
        // XOR Operation, if one of the number is negative
        if(a.isNegative ^ b.isNegative) {
            val = addWithNoSign(a, b);
            val.isNegative = a.isNegative;
        } else {
            if(a.compareTo(b) == 0) {
                return new Num("0");
            }
            Num biggerNum = null;
            Num smallerNum = null;
            boolean isNegativeA = a.isNegative, flag = true;
            a.isNegative = false;
            b.isNegative = false;
            long[] arr;
            if(a.compareTo(b) >  0) {
                biggerNum = a;
                smallerNum = b;
                flag = isNegativeA;
            } else {
                biggerNum = b;
                smallerNum = a;
                flag = !isNegativeA;
            }
            arr = new long[biggerNum.size()];
            int i = 0;
            long carry = 0;
            while(i < biggerNum.size() && i < smallerNum.size()) {
                if(biggerNum.arr[i] - carry < smallerNum.arr[i]) {
                    arr [i] = biggerNum.arr[i] + biggerNum.base() - smallerNum.arr[i] - carry;
                    carry = 1;
                }   else {
                    arr[i] = biggerNum.arr[i] - smallerNum.arr[i] - carry;
                    carry = 0;
                }
                i++;
            }
            while(i < biggerNum.size()) {
                arr[i] = biggerNum.arr[i] - carry;
                carry = 0;
                i++;
            }
            a.isNegative = isNegativeA;
            b.isNegative = isNegativeA;
            val = new Num(arr, a.base(), flag);
        }
        return val;
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
        if(this.isNegative ^ other.isNegative) {
            return this.isNegative ? -1 : 1;
        }
        else {
            int flag;
            if(this.size() == other.size()) {
                int j = this.size() - 1;
                while(j >= 0) {
                    if(this.arr[j] == other.arr[j]) {
                        j--;
                        if(j==0)
                            return 0;
                    } else {
                        flag = this.arr[j] > other.arr[j] ? 1 : -1;
                        return this.isNegative ? flag*-1 : flag;
                    }
                }
            } else {
                flag = (this.size > other.size() ? 1 : -1);
                return this.isNegative ? flag*-1 : flag;
            }
        }
        System.out.println("reas");
        return -1;
    }

    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    public void printList() {
        System.out.print(base + ": ");
        StringBuilder res = new StringBuilder();
        if(this.isNegative)
            res.append("-");
        for(int i = size-1; i >= 0; i--) {
            if(i == size - 1) {
                if(arr[i] != 0)
                    res.append(arr[i] + " ");
            }   else {
                res.append(arr[i] + " ");
            }
        }
        System.out.println(res);
    }

    // Return number to a string in base 10
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(isNegative) {
            sb.append("-");
        }
        Num temp = convertBase(10);
        for(int i = temp.size()-1; i >=0; i--) {
            if(!(i == size-1 && arr[i] == 0))
                sb.append(temp.arr[i]);
        }
        return sb.toString();
    }

    public long base() { return base; }

    public int size() { return size; }

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

        Scanner in = new Scanner(System.in);
        Num num1 = new Num(999);
        Num num2 = new Num("1");
//        System.out.println("--------Menu Options Usage--------");
//        System.out.println("Add: 1 <x>");
//        System.out.println("Subtract: 2");
//        System.out.println("Product: 3 <x>");
//        System.out.println("Divide: 4");
//        System.out.println("Power: 5 <val>");
//        System.out.println("Modulus: 6");
//        System.out.println("Square Root: 7");
//        System.out.println("Compare: 8");
////        System.out.println("Change Base: 9 <base>");
//        System.out.println("Print Number (as List): 10");
//        System.out.println("Print Number (as string): 11");
//        System.out.println("Evaluate Infix: 12");
//        System.out.println("Evaluate Postfix: 13");
//        System.out.println("Exit: 14");
//        System.out.println("----------------------------");
//
//        while_loop:
//        while (in.hasNext()) {
//            int com = in.nextInt();
//            switch (com) {
//                case 1: // Add two Number.
//                    System.out.println("Adding " + num1 + " & " + num2 + ": " + Num.add(num1, num2));
//                    break;
//                case 2: // Subtract two Number
//                    System.out.println("Subtracting " + num1 + " from " + num2 + ": " + Num.subtract(num1, num2));
//                    break;
//                case 3: // Multiplying two Number
//                    System.out.println("Multiplying " + num1 + " & " + num2 + ": " + Num.product(num1, num2));
//                    break;
//                case 4: // Division of two Number
//                    System.out.println("Dividing " + num1 + " by " + num2 + ": " + Num.divide(num1, num2));
//                    break;
//                case 5: // Num raised to another Number
//                    System.out.println(num1 + " raised to " + num2 + ": " + Num.power(num1, in.nextLong()));
//                    break;
//                case 6: // modulus of two number
//                    System.out.println(num1 + " % " + num2 + ": " + Num.mod(num1, num2));
//                    break;
//                case 7: // Sqrt of Number
//                    System.out.println("Square root of " + num1 + ": " + Num.squareRoot(num1));
//                    break;
//                case 8: // Division of two Number
//                    System.out.println("Dividing " + num1 + " by " + num2 + ": " + Num.divide(num1, num2));
//                    break;
////                case 9: // Num raised to another Number
////                    System.out.println(num1 + " after changing base: " + Num.convertBase(num1, in.nextLong()));
////                    break;
//                case 10: // Printing Num as List
//                    num1.printList();
//                    break;
//                case 11: // Printing Num as String
//                    System.out.println(num1);
//                    break;
//                default: // Exit loop
//                    break while_loop;
//            }
//        }
    }
}
