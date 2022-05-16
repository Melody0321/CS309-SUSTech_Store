package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "goods_img")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Goods_img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goods_img_id")
    private long goods_img_id;
    @NotNull
    private long goods_id;
    @NotNull
    private String picture;
    @NotNull
    private Boolean deleted;

    public Goods_img(long goods_id, String picture, Boolean deleted) {
        this.goods_id = goods_id;
        this.picture = picture;
        this.deleted = deleted;
    }
    public Goods_img(){}

    public long getGoods_img_id() {
        return goods_img_id;
    }

    public void setGoods_img_id(long goods_img_id) {
        this.goods_img_id = goods_img_id;
    }

    public long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(long goods_id) {
        this.goods_id = goods_id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
