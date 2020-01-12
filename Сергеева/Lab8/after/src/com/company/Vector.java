package com.company;

class Vector {
    int[] array;
    public Vector (int n) {
        array = new int[n];
    }

    public void setElement (int idx, int value) {
        array[idx] = value;
    }

    public int getElement(int idx) {
        return array[idx];
    }

    public String toString () {
        StringBuffer res = new StringBuffer ();
        res.append ("\n");
        for (int i = 0; i < array.length; i++) {
            res.append (getElement (i));
            res.append (" ");
        }
        res.append ("\n");
        return res.toString ();
    }

    public int getSize () {
        return array.length;
    }

}
