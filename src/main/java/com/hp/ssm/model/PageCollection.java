package com.hp.ssm.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PageCollection<T> {
    private int totalCount;
    private int pageNo;
    private int totalPages;
    private List<T> items;
}
