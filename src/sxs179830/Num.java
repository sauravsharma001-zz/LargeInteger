
// Starter code for lp1.
// Version 1.0 (8:00 PM, Wed, Sep 5).

// Change following line to your NetId
package sxs179830;

import java.util.*;


public class Num  implements Comparable<Num> {

    static long defaultBase = 1000;  // Change as needed
    long base = defaultBase;  // Change as needed
    long[] arr;  // array to store arbitrarily large integers
    boolean isNegative;  // boolean flag to represent negative numbers
    int size;  // actual number of elements of array that are used;  number is stored in arr[0..len-1]

    /**
     * Convert string to array of long in Num Object with defaultBase
     * @param s number in base 10, as String
     */
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

    /**
     * Convert long value in Num object with defaultBase
     * @param x Long value
     */
    public Num(long x) {
        if(x < 0) {
            this.isNegative = true;
            x = x * -1;
        }
        if(x == 0) {
            this.size = 1;
            this.arr = new long[this.size];
            this.arr[0] = 0;
        } else {
            this.size = (int) Math.ceil(Math.log10(x) / Math.log10(defaultBase - 1));
            if(this.size == 0) this.size = 1;
            this.arr = new long[size];
            int i = 0;
            while (x > 0) {
                arr[i] = x % defaultBase;
                x /= defaultBase;
                i++;
            }
        }
    }

    /**
     *
     * @param arr array of number
     * @param base base of number given
     * @param isNegative represent the sign of number
     */
    public Num(long[] arr, long base, boolean isNegative) {
        int size = 0, t = 0;
        for(int i = arr.length-1;  i > 0; i--) {
            if(arr[i] == 0)
                t++;
            else
                break;
        }
        this.size = arr.length - t;
        this.arr = new long[this.size];
        for(int k = 0; k < this.size; k++) {
            this.arr[k] = arr[k];
        }
        this.base = base;
        this.isNegative = isNegative;
    }

    /**
     * Add two Num Object and return the value
     * @param a first Num
     * @param b second Num
     * @return sum of the two Num
     */
    public static Num add(Num a, Num b) {

        StringBuilder sb = new StringBuilder();
        Num addition = null;
        // XOR Operation, if one of the number is negative
        if(a.isNegative ^ b.isNegative) {
            if(a.isNegative) {
                a.isNegative = false;
                addition = subtract(a, b);
                addition.isNegative = a.compareTo(b) > 0 ? true : false ;
                a.isNegative = true;
            }
            else {
                b.isNegative = false;
                addition = subtract(a, b);
                addition.isNegative = a.compareTo(b) > 0 ? false : true;
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

    /**
     * Add two Num Object and return the value without considering the sign of the number
     * @param a first Num
     * @param b second Num
     * @return sum of the two Num
     */
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
            carry = temp >= a.base() ? (int) ( temp / a.base()) : 0;
            i++;
        }
        if(carry != 0)
            arr[i] = 1;
        return new Num(arr, a.base(), false);
    }

    /**
     * Calculate difference of two Num Object and return the value
     * @param a first Num
     * @param b second Num
     * @return difference of the two Num
     */
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

    /**
     * Multiply two Num Object and return the value without considering the sign of the number
     * @param a first Num
     * @param b second Num
     * @return product of the two Num
     */
    public static Num product(Num a, Num b) {
        //productList has list of individual multiplication value.
        List<long[]> productList = new ArrayList<long[]>();
        for(int i = 0; i< b.arr.length; i++) {
            long carry =0;
            long[] res = new long[a.arr.length + 1];
            for(int j = 0; j< a.arr.length; j++) {
                long product = b.arr[i] * a.arr[j];
                product += carry;
                carry = product / a.base;
                res[j] = product % a.base;
            }
            if(carry !=0) {
                res[a.arr.length]= carry;
            }
            productList.add(rightShiftBy(res, i));
        }
        int index = 1;
        boolean isNeg = false;
        if(a.isNegative == b.isNegative) {
            isNeg = false;
        }else {
            isNeg = true;
        }
        Num finalResult = new Num(productList.get(0), a.base, isNeg);
        //add all elements of productList
        while(index < productList.size()) {
            Num nextToAdd = new Num(productList.get(index), a.base, isNeg);
            finalResult = add(finalResult, nextToAdd);
            index++;
        }
        return finalResult;
    }

    /**
     * shifting array to manage least significant digit in multiplication.
     * @param arr original array
     * @param times time to be shifted
     * @return update long[]
     */
    public static long[] rightShiftBy(long[] arr, int times) {
//    	System.out.println("times" + times);
        long[] res = new long[arr.length + times];
        for(int i = arr.length-1; i>=0; i--) {
            res[i+times] = arr[i];
        }
        while(times > 0) {
            times--;
            res[times] = 0;
        }
        return res;
    }

    /**
     * Using divide and conquer, power is calculated
     * @param a given num value
     * @param n given exponent
     * @return return num value for a raised to n
     */
    public static Num power(Num a, long n) {
        if(n < 0) {
            throw new ArithmeticException("Negative exponent");
        }
        if(n == 0)
            return new Num(0);
        if(n == 1)
            return a;
        else if(n%2 == 0)
            return product(power(a, n/2), power(a, n/2));
        else
            return product(a, product(power(a, n/2), power(a, n/2)));
    }

    /**
     * Division between two Num Object and return the value without considering the sign of the number
     * @param a first Num
     * @param b second Num
     * @return dividend
     */
    public static Num divide(Num a, Num b) {
        boolean isNeg = false;
        if(a.isNegative == b.isNegative) {
            isNeg = false;
        }else {
            isNeg = true;
        }
        int c = a.compareTo(b);
        if(c == -1) {
            return new Num(0);
        }
        else if(c == 0) {
            Num number = new Num(1);
            number.isNegative = isNeg;
            return number;
        }
        else {
            Num minValue = new Num("0");
            Num maxValue = new Num(a.arr, a.base, false);
            Num temp = add(minValue,maxValue).by2();
            Num ans = null;
            Num prodRes;
            int comparision;
        	while(minValue.compareTo(maxValue) < 0) {
                temp = add(minValue,maxValue).by2();
                prodRes = product(temp, b);
                comparision = prodRes.compareTo(a);
        		if(comparision == 0) {
        			return temp;
        		} else if(comparision == -1) {
        			minValue = temp;
        			ans = temp;
        		} else {
        			maxValue = temp;
        		}
        	}
        	return ans;
        }
    }

    // return a%b
    public static Num mod(Num a, Num b) {
        Num quotient = divide(a, b);
        Num result = subtract(a, product(quotient, b));
        return result;
    }

    /**
     * Use binary search
     * @param a given number
     * @return square root of the given number
     */
    public static Num squareRoot(Num a) {
        if(a == null) throw new NullPointerException();
        if(a.isNegative) throw new ArithmeticException();
        if(a.compareTo(new Num(0)) == 0 || a.compareTo(new Num(1)) == 0) return a;

        Num start = new Num(2);
        Num end = a.by2();
        Num mid, oldMid = null, ans = null;
        int comp;
        sqrt_loop:
        while(start.compareTo(end) < 0) {
            mid = Num.add(start, end).by2();
            comp = Num.product(mid, mid).compareTo(a);
            if(mid.equals(oldMid)){
                break sqrt_loop;
            } else if(comp == 0) {
                return mid;
            } else if(comp < 0) {
                start = mid;
                ans = mid;
            } else {
                end = mid;
            }
            oldMid = mid;
        }
        return ans;
    }

    /**
     * Utility functions
     * compare "this" to "other": return +1 if this is greater, 0 if equal, -1 otherwise
     * @param other value to compare with
     * @return +1 if this is greater, 0 if equal, -1 otherwise
     */
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
                        if(j<=0)
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
        return -1;
    }

    /**
     * Output using the format "base: elements of list ..."
     * For example, if base=100, and the number stored corresponds to 10965,
     * then the output is "100: 65 9 1"
     */
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

    /**
     * Return number to a string in base 10
     * @return String value of the number
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        if(isNegative) {
            sb.append("-");
            index = 1;
        }
        Num temp = convertBaseToDecimal();
        for(int i = temp.size()-1; i >=0; i--) {
            sb.append(temp.arr[i]);
        }
        while(sb.charAt(index) == '0' && sb.length() > 1) {
            sb.deleteCharAt(index);
        }
        return sb.toString();
    }

    /**
     * Return the base on which the number is stored
     * @return the base on which number is stored
     */
    public long base() { return base; }

    /**
     * Return the size of the array in which number is stored
     * @return size of the array
     */
    public int size() { return size; }


    /**
     * @param b Object of Num Class to compare with
     * @return true if both Num object stores same number else returns false
     */
    public boolean equals(Num b) {
        if(b == this)  return true;
        if(b == null) return false;

        return this.toString().equals(b.toString());
    }


    // Return number equal to "this" number, in base=newBase
    public Num convertBase(int newBase) {
        Num convertedNum = new Num(0);
        this.base = (long)newBase;
        String s = "";
        int i = 0;
        List<long[]> modList = new ArrayList<>();
        if(this.base < this.defaultBase) {
            while(this.compareTo(new Num(0)) > 0) {
                Num nextNum = mod(this, new Num(this.base));
                modList.add(nextNum.arr);
                Num div = new Num(this.base);
                this.arr = divide(this, div).arr;
            }
            Collections.reverse(modList);

            for(long[] l : modList) {
                s += Long.toString(l[0]);
            }
        }
        return new Num(s);
    }

    public long changeBase(long number, long fromBase, long toBase) {
        long convertedNumber = 0;
        long nextLong;
        int i = 0;
        while (number != 0) {
            nextLong = number  % toBase;
            convertedNumber = (long) (convertedNumber + nextLong * Math.pow(fromBase, i));
            number = number / 10;
            i++;
        }
        return convertedNumber;
    }


    public Num convertBaseToDecimal() {
        if(this.base == 10) return this;
        if(this == null) return null;

        StringBuilder sb = new StringBuilder();
        long carry = 0, tempDob;
        Num temp;
        Num result = new Num("0");
        for(int i =0; i < this.size(); i++) {
            tempDob = (this.arr[i] * (long) Math.pow(this.base, i));
            temp = new Num(String.valueOf(tempDob));
            result = Num.add(temp, result);
        }
        return result;
    }

    /**
     * Divide by 2, for using in binary search
     * @return num divided by 2
     */
    public Num by2() {
        long[] result = new long[this.size()];
        int i = this.size()-1, carry = 0;
        while(i >= 0) {
            result[i] = (int) Math.floor((carry * this.base() + this.arr[i]) / 2);
            carry = this.arr[i] % 2 == 0 ? 0 : 1;
            i--;
        }
        return new Num(result, this.base(), this.isNegative);
    }

    /**
     * Calculate difference of two Num Object and return the value
     * @param operator The operation to be done
     * @param value2 First operand
     * @param value1 Second operand
     * @return Result of operation on the two operands
     */
    public static Num performOperation(String operator, Num value2, Num value1) {
        Num result = null;
        switch (operator) {
            case "*":
                result = product(value1, value2);
                break;
            case "+":
                result = add(value1, value2);
                break;
            case "-":
                result = subtract(value1, value2);
                break;
            case "/":
                result = divide(value1, value2);
                break;
            case "%":
                result = mod(value1, value2);
                break;
            case "^":
                result = power(value1,Long.valueOf(value2.toString()));
                break;
            default:
                break;
        }
        return result;
    }

    // Evaluate an expression in postfix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluatePostfix(String[] expr) {
        Set<String> operators = new HashSet<>(Arrays.asList("*", "+", "-", "/", "%", "^"));
        Stack<String> operandStack = new Stack<>();
        Num result = null;
        int strLen = expr.length;
        int i=0;
        while (strLen>0) {
            String nextString = expr[i];
            if(operators.contains(nextString)) {
                result = performOperation(nextString,new Num(operandStack.pop()),new Num(operandStack.pop()));
                operandStack.push(result.toString());
            } else {
                operandStack.push(nextString);
            }
            i++;
            strLen--;
        }
        return result;
    }

    /**
     * Compare braces with other operators
     * @param op1 operator1
     * @param op2 operator2
     * @return  return true if op2 has high or equal priority than op1, else false
     */    public static boolean hasPrecedence(String op1, String op2)
    {
        if (op2.equals("(") || op2.equals(")"))
            return false;
        if ((op1.equals("*") || (op1.equals("/") || op1.equals("^")) && (op2.equals("+") || op2.equals("-"))))
            return false;
        else
            return true;
    }

    // Evaluate an expression in infix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "(", ")", "0", or
    // a number: [1-9][0-9]*.  There is no unary minus operator.
    public static Num evaluateInfix(String[] expr) {
        Set<String> operators = new HashSet<>(Arrays.asList("*", "+", "-", "/", "%", "^"));
        Stack<String> operandStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();
        Num result = null;

        int strLen = expr.length;
        int i=0;
        while (strLen>0) {
            String nextString = expr[i];
            if(operators.contains(nextString)) {
                while(!operatorStack.empty() && hasPrecedence(nextString,operatorStack.peek())) {
                    result = performOperation(nextString,new Num(operandStack.pop()),new Num(operandStack.pop()));
                    operandStack.push(result.toString());
                }
                operatorStack.push(nextString);

            } else if(nextString.equals("(")) {
                operatorStack.push(nextString);
            } else if(nextString.equals(")")) {
                while(!operatorStack.peek().equals("(")) {
                    result = performOperation(operatorStack.pop(),new Num(operandStack.pop()),new Num(operandStack.pop()));
                    operandStack.push(result.toString());
                }
                operatorStack.pop();
            } else {
                operandStack.push(nextString);
            }

            i++;
            strLen--;
        }

        while (!operatorStack.empty()) {
            result = performOperation(operatorStack.pop(),new Num(operandStack.pop()),new Num(operandStack.pop()));
            operandStack.push(result.toString());
        }
        return new Num(operandStack.pop());
    }


    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        Num num1 = new Num(131412);
        Num num2 = new Num(100);
        System.out.println("--------Menu Options Usage--------");
        System.out.println("Add: 1 <x>");
        System.out.println("Subtract: 2");
        System.out.println("Product: 3 <x>");
        System.out.println("Divide: 4");
        System.out.println("Power: 5 <val>");
        System.out.println("Modulus: 6");
        System.out.println("Square Root: 7");
        System.out.println("Compare: 8");
        System.out.println("Change Base: 9 <base>");
        System.out.println("Print Number (as List): 10");
        System.out.println("Print Number (as string): 11");
        System.out.println("Evaluate Infix: 12");
        System.out.println("Evaluate Postfix: 13");
        System.out.println("Exit: 14");
        System.out.println("----------------------------");

        while_loop:
        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1: // Add two Number.
                    System.out.println("Adding " + num1 + " & " + num2 + ": " + Num.add(num1, num2));
                    break;
                case 2: // Subtract two Number
                    System.out.println("Subtracting " + num1 + " from " + num2 + ": " + Num.subtract(num1, num2));
                    break;
                case 3: // Multiplying two Number
                    System.out.println("Multiplying " + num1 + " & " + num2 + ": " + Num.product(num1, num2));
                    break;
                case 4: // Division of two Number
                    System.out.println("Dividing " + num1 + " by " + num2 + ": " + Num.divide(num1, num2));
                    break;
                case 5: // Num raised to another Number
                    long power = in.nextLong();
                    System.out.println(num1 + " raised to " + power + ": " + Num.power(num1, power));
                    break;
                case 6: // modulus of two number
                    System.out.println(num1 + " % " + num2 + ": " + Num.mod(num1, num2));
                    break;
                case 7: // Sqrt of Number
                    System.out.println("Square root of " + num1 + ": " + Num.squareRoot(num1));
                    break;
                case 8: // Comparing of two Number
                    int c = num1.compareTo(num2);
                    System.out.println("Out of " + num1 + " & " + num2 + ", " + (c == 1 ? num1 + " is bigger." :  ( c == -1 ? num2 + " is bigger." : " both are same")));
                    break;
                case 9: // Num raised to another Number
                    int base = in.nextInt();
                	System.out.println(num1 + " after changing base: " + num1.convertBase(base));
                  break;
                case 10: // Printing Num as List
                    num1.printList();
                    break;
                case 11: // Printing Num as String
                    System.out.println(num1);
                    break;
                case 12: // Infix Evaluation ( 1 + ( ( 2 + 3 ) % ( 4 * 5 ) ) ) = 12
                    Num res = evaluateInfix(new String[]{"(", "1", "+", "(", "(", "2", "+", "3", ")", "%", "(", "4", "*", "5", ")", ")", ")"});
                    System.out.println(res.toString().replaceFirst("^0+(?!$)", ""));
                    break;
                case 13: // Postfix Evaluation
                    Num result = evaluatePostfix(new String[]{"5","20","%"});
                    System.out.println(result.toString().replaceFirst("^0+(?!$)", ""));
                    break;
                default: // Exit loop
                    break while_loop;
            }
        }
    }
}
