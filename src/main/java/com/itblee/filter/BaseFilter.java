package com.itblee.filter;

import java.io.Serializable;

public abstract class BaseFilter implements Serializable {
    private Long id;

    public BaseFilter() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
