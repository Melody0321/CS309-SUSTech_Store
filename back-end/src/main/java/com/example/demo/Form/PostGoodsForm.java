package com.example.demo.Form;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.example.demo.pojo.MultipartFile1;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.util.FileCopyUtils;


import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class PostGoodsForm {
    public String goods_name;
    public String detail;
    public String tag;
    public int price;

    public int rest_num;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }



    public int getRest_num() {
        return rest_num;
    }

    public void setRest_num(int rest_num) {
        this.rest_num = rest_num;
    }

    public PostGoodsForm(String goods_name, String detail, String tag, int price,  int rest_num) {
        this.goods_name = goods_name;
        this.detail = detail;
        this.tag = tag;
        this.price = price;

        this.rest_num = rest_num;
    }

    @Override
    public String toString() {
        return "PostGoodsForm{" +
                "goods_name='" + goods_name + '\'' +
                ", detail='" + detail + '\'' +
                ", tag='" + tag + '\'' +
                ", price=" + price +

                ", rest_num=" + rest_num +
                '}';
    }
    /*
     * Copyright 2002-2018 the original author or authors.
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *      http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */







}
