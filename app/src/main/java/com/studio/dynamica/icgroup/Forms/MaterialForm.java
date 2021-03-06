package com.studio.dynamica.icgroup.Forms;

import java.util.ArrayList;
import java.util.List;

public class MaterialForm {
    String name;
    String id;
    int num1, num2;
    List<OrderForm> orderForms;
    public MaterialForm(String n, String i, int n1, int n2){
        name=n;id=i;num1=n1;num2=n2;
        orderForms=new ArrayList<>();
    }
    public MaterialForm(String n, String i, int n1, int n2,List<OrderForm> of){
        name=n;id=i;num1=n1;num2=n2;
        orderForms=of;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<OrderForm> getOrderForms() {
        return orderForms;
    }

    public int getNum2() {
        return num2;
    }

    public int getNum1() {
        return num1;
    }

}
