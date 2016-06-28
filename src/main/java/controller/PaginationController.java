package controller;

/**
 * Created by Oleksandr Dudkin.
 */
public class PaginationController {
    private int totalItems;
    private int itemsPerPage;
    private int currentPageNumber;

    public PaginationController(int totalItems, int itemsPerPage, int currentPageNumber) {
        this.totalItems = totalItems;
        this.itemsPerPage = itemsPerPage;
        this.currentPageNumber = currentPageNumber;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public int getTotalItems() {
        return totalItems;
    }


    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public String makePagingLinks(String pageLocation, String addToRequest) {
        String ret = "";
        if (totalItems <= itemsPerPage)
            return ret;
        int totalPages = (totalItems / itemsPerPage);
        if (totalItems % itemsPerPage != 0)
            totalPages++;
        if(currentPageNumber > totalPages)
            currentPageNumber = 1;
        if (totalItems <= currentPageNumber * itemsPerPage)
            currentPageNumber = totalPages;
        int start = currentPageNumber - 3;
        if (start <= 0)
            start = 1;
        int end = currentPageNumber + 3;
        if (end >= totalPages)
            end = totalPages;
        if (start > 1){
            //ret += "<a href='" + pageLocation + "&page=1"
            ret += "<a href='" + pageLocation + "?page=1"
                    + addToRequest + "\'>1</a> ";
        }
        if (start > 2)
            ret += "... ";
        for (int i = start; i <= end; i++) {
            if (i == currentPageNumber){
                ret += "<span style=\"font-weight: bold;" +
                        "color: red;\">" + (i) + "</span>";
            }else{
                //ret += "<a href='" + pageLocation + "&page="
                ret += "<a href='" + pageLocation + "?page="
                        + (i) + addToRequest + "'>" + (i) + "</a>";
            }
            if (i < totalPages)
                ret += " ";
        }
        if (end + 1 < totalPages)
            ret += "... ";
        if (end < totalPages) {
            if (totalPages == currentPageNumber){
                ret += "<strong>" + totalPages + "</strong>";
            }else{
                //ret += "<a href='" + pageLocation + "&page="
                ret += "<a href='" + pageLocation + "?page="
                        + totalPages + addToRequest + "'>"
                        + totalPages + "</a>";
            }
        }
        ret += "  ";
        return ret;
    }

}
