package com.itblee.converter.impl;

import com.itblee.repository.entity.BaseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractConverter<T extends BaseEntity> {

    @Autowired
    private ModelMapper modelMapper;

    protected <E> E convert(Object object, Class<E> convertTo) {
    	if (object == null)
    	    return null;
    	if (object.getClass() == convertTo)
    	    return convertTo.cast(object);
        return modelMapper.map(object, convertTo);
    }

    protected <K, V> List<K> convert(Collection<V> collection, Class<K> convertTo) {
        List<K> results = new ArrayList<>();
        for (V element : collection)
            results.add(convert(element, convertTo));
        return results;
    }

}
