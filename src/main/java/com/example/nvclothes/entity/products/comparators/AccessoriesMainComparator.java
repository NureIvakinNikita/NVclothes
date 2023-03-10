package com.example.nvclothes.entity.products.comparators;

import com.example.nvclothes.entity.products.AccessoriesEntity;

import java.util.Comparator;


public class AccessoriesMainComparator implements Comparator<AccessoriesEntity> {
    @Override
    public int compare(AccessoriesEntity first, AccessoriesEntity second) {
        return Long.compare(first.getId(), second.getId());
    }
}
