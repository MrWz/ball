package com.xaut.util;

import java.util.ArrayList;
import java.util.List;


public class PageUtils {

    private PageUtils() {
        throw new UnsupportedOperationException();
    }

    public static int getFirstResult(int pageNumber, int pageSize) {
        return pageSize <= 0?0:(pageNumber - 1) * pageSize;
    }

    public static List<Integer> generateLinkPages(int currentPage, int lastPage, int count) {
        int avg = count / 2;
        int startPage = currentPage - avg;
        if(startPage <= 0) {
            startPage = 1;
        }

        int endPage = startPage + count - 1;
        if(endPage > lastPage) {
            endPage = lastPage;
        }

        if(endPage - startPage < count) {
            startPage = endPage - count;
            if(startPage <= 0) {
                startPage = 1;
            }
        }

        ArrayList result = new ArrayList();
        for(int i = startPage; i <= endPage; ++i) {
            result.add(Integer.valueOf(i));
        }

        return result;
    }

    public static int computeLastPage(int totalElements, int pageSize) {
        if(pageSize <= 0) {
            return 1;
        }
        int result = (totalElements % pageSize == 0? totalElements / pageSize:totalElements / pageSize + 1);
        return result <= 1 ? 1 : result;
    }

    public static int computePage(int pageNum, int pageSize, int totalElements) {
        return pageNum <= 1?1:(2147483647 != pageNum && pageNum <= computeLastPage(totalElements, pageSize)?pageNum:computeLastPage(totalElements, pageSize));
    }
}
