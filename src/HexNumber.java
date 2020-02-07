/*
HexNumber

Stores an integer as an array of values. Each value in the values array corresponds to the values in the HEX and BINARY
array. The index in the array translates to what power of 16 the value is multiplied by. For example, the
array {2, 14, 8, 11} has 2 at index 0, 14 at index 1, 8 at index 2, and 11 at index 3. If we treat this value as
unsigned, the calculation to convert it to decimal is:

2*16^0 + 14*16^1 + 8*16^2 + 11*16^3 = 2 + 224 + 2048 + 45056 = 47330;

In HEX and BINARY, this unsigned number converts to:

Hex:    B8E2
Binary: 1011 1000 1110 0010

(Note that the order of powers is reversed when written in Hex or BINARY.)

We are basically storing the number in Hex.
 */

public class HexNumber {

    public final static String[] HEX = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "A", "B", "C", "D", "E", "F"};
    public final static String[] BINARY = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111",
            "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
    public final static int BASE = 16;

    private int nibbles;// must be greater than 0
    private int[] values;
    private boolean sign;

    public HexNumber() {
        this.nibbles = 4;
        this.values = new int[this.nibbles];
        for (int i = 0; i < this.nibbles; i++) this.values[i] = 0;
        sign = true;
    }

    public HexNumber(int[] newValues, boolean newSign) {
        this.nibbles = newValues.length;
        this.values = new int[this.nibbles];
        for (int i = 0; i < this.values.length; i++) {
            this.values[i] = newValues[i];
            if (this.values[i] < 0) this.values[i] = 0;
            if (this.values[i] > HexNumber.BASE - 1) this.values[i] = HexNumber.BASE - 1;
        }
        this.sign = newSign;
    }

    public int getNibbles() {
        return this.nibbles;
    }

    public boolean getSign() {
        return this.sign;
    }

    public String getHexString() {
        String result = new String();
        for (int i = values.length - 1; i >=0; i--) {
            result += HEX[values[i]];
        }
        return result;
    }

    public String getBinaryString() {
        String result = new String();
        for (int i = values.length - 1; i >=0; i--) {
            result += BINARY[values[i]];
            if (i != 0) result += ' ';
        }
        return result;
    }

    public String getDecimalString() {
        return Integer.toString(getDecimalValue());
    }

    public int getDecimalValue() {
        int result = 0;
        for (int i = 0; i < values.length; i++) {
            result += values[i]*Math.pow(HexNumber.BASE, i);
        }
        if (sign && result >= Math.pow(HexNumber.BASE, nibbles)/2) {
            result -= Math.pow(HexNumber.BASE, nibbles);
        }
        return result;
    }
}
