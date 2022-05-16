package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "goods")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_id")
    private long goods_id;
    @NotNull
    private int seller_id;
    @NotNull
    private String goods_name;
    private String detail;
    private String tag;
    private boolean is_deleted =false;
    @NotNull
    private int price = 0;
    private String picture;//图片链接
    @NotNull
    private int selled_num = 0;
    @NotNull
    private int rest_num = 0;

    public Goods() {

    }


    @Override
    public String toString() {
        return "Goods{" +
                "goods_id=" + goods_id +
                ", seller_id=" + seller_id +
                ", goods_name='" + goods_name + '\'' +
                ", detail='" + detail + '\'' +
                ", tag='" + tag + '\'' +
                ", is_deleted=" + is_deleted +
                ", price=" + price +
                ", picture='" + picture + '\'' +
                ", selled_num=" + selled_num +
                ", rest_num=" + rest_num +
                '}';
    }

    public int getSelled_num() {
        return selled_num;
    }

    public void setSelled_num(int selled_num) {
        this.selled_num = selled_num;
    }

    public int getRest_num() {
        return rest_num;
    }

    public void setRest_num(int rest_num) {
        this.rest_num = rest_num;
    }

    public Goods(int seller_id, String goods_name, String detail, String tag, int price, String picture, int rest_num) {
        this.seller_id = seller_id;
        this.goods_name = goods_name;
        this.detail = detail;
        this.tag = tag;
        this.price = price;
        this.picture=picture;
        this.rest_num = rest_num;
    }
    public Goods(int seller_id, String goods_name, String detail, String tag, int price,  int rest_num) {
        this.seller_id = seller_id;
        this.goods_name = goods_name;
        this.detail = detail;
        this.tag = tag;
        this.price = price;

        this.rest_num = rest_num;
    }
    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(long goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
