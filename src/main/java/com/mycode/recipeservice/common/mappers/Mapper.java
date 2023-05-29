package com.mycode.recipeservice.common.mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;

public interface Mapper<I, O> {
    @NonNull
    O map(@NonNull I var1);

    @NonNull
    default List<O> map(@NonNull Collection<? extends I> input) {
        return input.stream().map(this::map).collect(Collectors.toList());
    }
}
