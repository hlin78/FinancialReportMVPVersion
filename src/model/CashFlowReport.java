package model;

import java.util.ArrayList;
import java.util.List;
/**
 * income report class extends report class.
 * @author Team23
 * @version 1.0
 */
public class CashFlowReport extends AbstractReport {

    /**	
     * constructor.
     */
    public CashFlowReport() {
        super();
    }

    @Override
    public String getTitle(String year, String month) {
        if(!year.equals("") || !month.equals("")){
            reportDate.setYear(Integer.parseInt(year));
            reportDate.setMonth(Integer.parseInt(month));
        }
        return"Cash Flow for " + reportDate.getYear() + " "+ reportDate.getFormatMonth();
    }

    @Override
    public ArrayList<String> getCategoryList() {
        List<String> cate = new ArrayList<String>();
        cate.add("Food");
        cate.add("Rent");
        cate.add("Entertainment");
        cate.add("Shopping");
        cate.add("Other");
        cate.add("Salary");
        cate.add("Birthday");
        cate.add("Parents");
        cate.add("Shoclarship");
        cate.add("Lottery");
        cate.add("Other");
        return (ArrayList<String>) cate;
    }
    
}
