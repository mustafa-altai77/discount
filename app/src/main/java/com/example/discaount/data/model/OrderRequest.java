package com.example.discaount.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
public class OrderRequest {
    @SerializedName("products")
    List<prd> cartItem;
    @SerializedName("drugs")
    List<drg> cartItemDrug;
    @SerializedName("prescriptions")
    Prescriptions prescriptions;
    //  List<prs> prestion;
    @SerializedName("long")
    double longtuit;
    double lat;
    String address;
    String note;
    String phone;
    public OrderRequest(List<CartItem> cartItem, List<CartItemDrug> cartItemDrug,Prescriptions prescriptions,double longtuit, double lat,String address, String note, String phone) {

        this.cartItemDrug= new ArrayList<>();
        this.cartItem= new ArrayList<>();
        this.prescriptions = prescriptions;
        for(int i=0;i< cartItem.size(); i++)
        {
            this.cartItem.add(new prd(Integer.parseInt(cartItem.get(i).getProduct().getId()),cartItem.get(i).getQuantity()));
        }
        for(int i=0;i< cartItemDrug.size(); i++)
        {
            this.cartItemDrug.add(new drg(cartItemDrug.get(i).getDrug().getId(),cartItemDrug.get(i).getQuantity()));
        }

        this.longtuit = longtuit;
        this.lat = lat;
        this.address =address;
        this.phone = phone;
        this.note = note;
    }
    public OrderRequest(List<CartItem> cartItem, List<CartItemDrug> cartItemDrug,double longtuit, double lat,String address, String note, String phone) {

        this.cartItemDrug= new ArrayList<>();
        this.cartItem= new ArrayList<>();
        for(int i=0;i< cartItem.size(); i++)
        {
            this.cartItem.add(new prd(Integer.parseInt(cartItem.get(i).getProduct().getId()),cartItem.get(i).getQuantity()));
        }
        for(int i=0;i< cartItemDrug.size(); i++)
        {
            this.cartItemDrug.add(new drg(cartItemDrug.get(i).getDrug().getId(),cartItemDrug.get(i).getQuantity()));
        }

        this.longtuit = longtuit;
        this.lat = lat;
        this.address =address;
        this.phone = phone;
        this.note = note;
    }

    @Override
    public String toString() {

        String prdlis="[";
        for (int i=0;i< cartItem.size();i++){
            prdlis+="{ amount : "+cartItem.get(i).getAmount()+",producct_id : "+cartItem.get(i).getProduct_id()+" } , \n";
        }
        prdlis +=" ] ";
        String drglis="[";
        for (int i=0;i< cartItemDrug.size();i++){
            drglis+="{ amount : "+cartItemDrug.get(i).getAmount()+",producct_id : "+cartItemDrug.get(i).getDrug_id()+" } , \n";
        }
        drglis+=" ] ";

        return "OrderRequest{" +
                "cartItem=" + prdlis +
                ", cartItemDrug=" + drglis +
                ", longtuit=" + longtuit +
                ", lat=" + lat +
                ", note='" + note + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
    class  drg{
        int drug_id;
        int amount;

        public drg(int drug_id, int amount) {
            this.drug_id = drug_id;
            this.amount = amount;
        }

        public int getDrug_id() {
            return drug_id;
        }

        public void setDrug_id(int drug_id) {
            this.drug_id = drug_id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }

    class  prd{
        int product_id;
        int amount;

        public prd(int product_id, int amount) {
            this.product_id = product_id;
            this.amount = amount;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }

    class  prs {
        String imagePAth;
        boolean recomended;

        public String getImagePAth() {
            return imagePAth;
        }

        public void setImagePAth(String imagePAth) {
            this.imagePAth = imagePAth;
        }
    }


}