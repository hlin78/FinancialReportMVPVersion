package database;

import java.util.ArrayList;
import java.util.List;

import model.Transaction;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This class describes the public methods needed for 
 * generating the financial report. These methods allows
 * the report to be opened or closed and to get the total
 * amount and spending list of the financial report.
 * @version 1.0
 * @author Team 23
 */
public class FinancialReportGenerator {
/**
 * string variable for log cat.
 */
    public static final String LOGTAG = "CLOVER";

/**
 * database open helper.
 */
    SQLiteOpenHelper dbhelper;
    
/**
 * database.
 */
    SQLiteDatabase database;
	
/**
 * Constructor for FinancialReportGenerator.
 * 
 * @param context context of the account source
 */
    public FinancialReportGenerator(final Context context) {
        dbhelper = new FinancialDBOpenHelper(context);
    }
	
/**
 * void method to open the account source.
 */
    public void open() {
    	Log.i(LOGTAG, "Account Databases opened");
    	database = dbhelper.getWritableDatabase();
    }

/**
 * void method to close the account source.
 */
    public void close() {
    	Log.i(LOGTAG, "Account Databases closed");
    	database.close();
    }
	
/**
 * getSpendingList method.
 * 
 * @param date the date of the report
 * @param userid the ID of the user
 * @return the spending list of the report
 */
    public List<Transaction> getSpendingList(final String date, final String userid) {
    	final List<Transaction> trs = new ArrayList<Transaction>();
    	final Cursor cursor = database.query(FinancialDBOpenHelper.TABLE_TRANS, FinancialTransactionSource.TRANSCOLUMNS,
				FinancialDBOpenHelper.COLUMN_TRTYPE + " = " + "'Withdrawl' AND "
				+ "strftime('%Y%m'," + FinancialDBOpenHelper.COLUMN_TRDATE + ") = " + "'" + date + "' AND " 
				+ FinancialDBOpenHelper.COLUMN_TRUSERID + " = " + "'" + userid + "'", null, null, null, null);
    	return FinancialTransactionSource.cursorTransaction(cursor, trs);
    }
	
/**
 * getTotal method.
 * 
 * @param list the list of transaction
 * @return total the total amount of the transaction
 */
    public Double getTotal(final List<Transaction> list) {
    	final List<Transaction> trs = list;
    	double total = 0;
        for (int i = 0; i < trs.size(); i++) {
            total += trs.get(i).getAmount();
    	}
    	return total;	
    }
}
