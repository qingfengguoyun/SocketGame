package com.myPokeGame.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NativePage<T> {

    // 每页大小
    Long pageSize;

    // 总页数
    Long totalPages;

    //当前页
    Long currentPage;

    // 具体内容
    List<T> data=new LinkedList<>();

    // 内容总数
    Long totolCount;

    public static<T> NativePage<T> convert(IPage<T> iPage){
        NativePage<T> nativePage = new NativePage<>();
        nativePage.setData(iPage.getRecords());
        nativePage.setTotalPages(iPage.getPages());
        nativePage.setPageSize(iPage.getSize());
        nativePage.setTotolCount(iPage.getTotal());
        nativePage.setCurrentPage(iPage.getCurrent());
        return nativePage;
    }

}
