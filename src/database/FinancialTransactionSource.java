package database;

import java.util.ArrayList;
import java.util.List;

import model.Transaction;
import model.myDate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FinancialTransactionSource {
	public static final String LOGTAG = "CLOVER";

	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;

	public static final String[] TRANSCOLUMNS = {
			FinancialDBOpenHelper.COLUMN_TRNAME,
			FinancialDBOpenHelper.COLUMN_TRTYPE,
			FinancialDBOpenHelper.COLUMN_TRDATE,
			FinancialDBOpenHelper.COLUMN_TRAMOUNT,
			FinancialDBOpenHelper.COLUMN_TRSTATUS,
			FinancialDBOpenHelper.COLUMN_TRRECORD,
			FinancialDBOpenHelper.COLUMN_TBDNAME,
			FinancialDBOpenHelper.COLUMN_TRUSERID
	};

	public FinancialTransactionSource(Context context) {
		dbhelper = new FinancialDBOpenHelper(context);
	}

	public void open() {
		Log.i(LOGTAG, "Transaction Databases opened");
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		Log.i(LOGTAG, "Transaction Databases closed");
		database.close();
	}

	public boolean addTransaction(final Transaction trans) {
		boolean flag = false;
		final ContentValues values = new ContentValues();
		double newbalance = 0;
		values.put(FinancialDBOpenHelper.COLUMN_TRNAME, trans.getName());
		values.put(FinancialDBOpenHelper.COLUMN_TRTYPE, trans.getType());
		values.put(FinancialDBOpenHelper.COLUMN_TRDATE, trans.getDate().getRawDate());
		values.put(FinancialDBOpenHelper.COLUMN_TRAMOUNT, trans.getAmount());
		values.put(FinancialDBOpenHelper.COLUMN_TRSTATUS, trans.getStatus());
		values.put(FinancialDBOpenHelper.COLUMN_TRRECORD, trans.getRecordTime());
		values.put(FinancialDBOpenHelper.COLUMN_TBDNAME, trans.getBkDisName());
		values.put(FinancialDBOpenHelper.COLUMN_TRUSERID, trans.getUserid());

		
		final double total = getBalance(trans.getBkDisName(), trans.getUserid());
		if (trans.getType().equals("Withdrawl")) {
			newbalance = total - trans.getAmount();
		} else {
			newbalance = total + trans.getAmount();
		}
		
		if(newbalance >0){
			database.insert(FinancialDBOpenHelper.TABLE_TRANS, null, values);
			updateBalance(newbalance, trans.getBkDisName(), trans.getUserid());
			flag = true;
		} 
		Log.i(LOGTAG,
				"Add a new transaction " + trans.getName() + " in "
						+ trans.getBkDisName());
		return flag;
	}

	public List<Transaction> getTransactionList(String bankname, String userid){
		List<Transaction> trs = new ArrayList<Transaction>();
		Cursor cursor = database.query(FinancialDBOpenHelper.TABLE_TRANS, TRANSCOLUMNS,
				FinancialDBOpenHelper.COLUMN_TBDNAME + " = " + "'"+ bankname + "' AND " +
				FinancialDBOpenHelper.COLUMN_TRUSERID + " = " + "'"+ userid + "'",
				null, null, null, null);
		Log.i(LOGTAG, "Find " + cursor.getCount() + " rows");
		return cursorTransaction(cursor, trs);
	}
	
	public double getBalance(String disname, String userid) {
		double result = 0;
		Cursor c = database.query(FinancialDBOpenHelper.TABLE_ACCOUNTS,
				FinancialAccountSource.ACCOUNTCOLUMNS, FinancialDBOpenHelper.COLUMN_DISNAME + " = "
						+ "'" + disname + "' AND " + FinancialDBOpenHelper.COLUMN_ACUSERID + " = "
						+ "'"+ userid + "'", null, null, null, null);
		Log.i(LOGTAG, "Find " + c.getCount() + " rows in getBalance");
		if (c != null) {
			c.moveToFirst();
			//Balance is at the third column so index is 2
			result = c.getDouble(2);
		}
		Log.i(LOGTAG, "Get balance $" + result);
		return result;
	}
	
	public void updateBalance(double nb, String disname, String userid){
		ContentValues cd = new ContentValues();
		cd.put(FinancialDBOpenHelper.COLUMN_BALANCE, nb);
		database.update(FinancialDBOpenHelper.TABLE_ACCOUNTS, cd,
				FinancialDBOpenHelper.COLUMN_DISNAME + " = " + "'" + disname +"' AND "
				+ FinancialDBOpenHelper.COLUMN_ACUSERID + " = " + "'"+ userid + "'", null);
		Log.i(LOGTAG, "Update new balance $"+ nb +  " in" + disname);
	}
	
	public void deleteTransaction(String recordTime){
		String[] values = new String[]{recordTime};
		database.delete(FinancialDBOpenHelper.TABLE_TRANS, FinancialDBOpenHelper.COLUMN_TRRECORD + "=?", values);
		Log.i(LOGTAG, "transaction deleted");
	}
	protected static List<Transaction> cursorTransaction(Cursor c, List<Transaction> trs){
		if(c.getCount() >0){
			while(c.moveToNext()){
				Transaction tr = new Transaction();
					tr.setName(c.getString(c.getColumnIndex(FinancialDBOpenHelper.COLUMN_TRNAME)));
					tr.setType(c.getString(c.getColumnIndex(FinancialDBOpenHelper.COLUMN_TRTYPE)));
					tr.setDate(new myDate(c.getString(c.getColumnIndex(FinancialDBOpenHelper.COLUMN_TRDATE))));
					tr.setAmount(c.getDouble(c.getColumnIndex(FinancialDBOpenHelper.COLUMN_TRAMOUNT)));
					tr.setStatus(c.getString(c.getColumnIndex(FinancialDBOpenHelper.COLUMN_TRSTATUS)));
					tr.setRecordTime(c.getString(c.getColumnIndex(FinancialDBOpenHelper.COLUMN_TRRECORD)));
					tr.setBkDisName(c.getString(c.getColumnIndex(FinancialDBOpenHelper.COLUMN_TBDNAME)));
					tr.setUserid(c.getString(c.getColumnIndex(FinancialDBOpenHelper.COLUMN_TRUSERID)));
					trs.add(tr);
			}
		}
		return trs;
	}

	/**
	 * @return
	 */
	public static Context getContext() {
		// TODO Auto-generated method stub
		return FinancialTransactionSource.getContext();
	}
	
}
