package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "shopping_cart_goods")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Shopping_Cart_Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_cart_goods_id")
    private long shopping_cart_goods_id;
    @NotNull
    private long goods_id;
    @NotNull
    private int seller_id;
    @NotNull
    private int customer_id;
    @NotNull
    private int goods_num;
    @NotNull
    private long total_price;
    @NotNull
    private boolean is_deleted=false;

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Shopping_Cart_Goods() {

    }

    public Shopping_Cart_Goods(long goods_id, int goods_num, int seller_id, int customer_id, long total_price) {
        this.goods_id = goods_id;
        this.goods_num = goods_num;
        this.seller_id = seller_id;
        this.customer_id = customer_id;
        this.total_price = total_price;
        this.is_deleted=false;
    }

    public long getTotal_price() {
        return total_price;
    }

    public void setTotal_price(long total_price) {
        this.total_price = total_price;
    }

    public long getShopping_cart_goods_id() {
        return shopping_cart_goods_id;
    }

    public void setShopping_cart_goods_id(long shopping_cart_goods_id) {
        this.shopping_cart_goods_id = shopping_cart_goods_id;
    }

    public long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(long goods_id) {
        this.goods_id = goods_id;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }


}
