package com.example.nvclothes.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class OrderClientEntityPK implements Serializable {

    private Long clientId;

    private Long orderGroupId;


}
