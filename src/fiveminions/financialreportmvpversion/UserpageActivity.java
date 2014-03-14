package fiveminions.financialreportmvpversion;


import java.util.ArrayList;
import java.util.List;

import views.PieGraph;
import views.PieSlice;

import model.User;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class UserpageActivity extends ListActivity {

	private User user;
	private List<String> menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_page);

		Bundle b = getIntent().getExtras();
		user = b.getParcelable("model.User");

		menu = new ArrayList<String>();
		menu.add("Transactions");
		menu.add("Spending Report");


		setTab();

		PieGraph pg = (PieGraph)findViewById(R.id.pieGraph);
		PieSlice slice = new PieSlice();
		slice.setColor(Color.parseColor("#99CC00"));
		slice.setValue(2);
		slice.setTitle("test");
		pg.addSlice(slice);
		slice = new PieSlice();
		slice.setColor(Color.parseColor("#FFBB33"));
		slice.setValue(3);
		pg.addSlice(slice);
		slice = new PieSlice();
		slice.setColor(Color.parseColor("#AA66CC"));
		slice.setValue(8);
		pg.addSlice(slice);
		display();
	}

	private void setTab() {
		TabHost th = (TabHost) findViewById(R.id.tabhost);
		th.setup();
		TabSpec ts = th.newTabSpec("a");
		ts.setContent(R.id.tab1);
		ts.setIndicator("Spending");
		th.addTab(ts);
		ts = th.newTabSpec("b");
		ts.setContent(R.id.tab2);
		ts.setIndicator("Income");
		th.addTab(ts);
		ts = th.newTabSpec("c");
		ts.setContent(R.id.tab3);
		ts.setIndicator("Cash Flow");
		th.addTab(ts);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.user_page_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.user_page_account:
			Intent intentAcc = new Intent(this, AccountPageActivity.class);
			intentAcc.putExtra("userid", user.getUserID());
			Log.i(MainActivity.LOGTAG, "Pass in userid");
			startActivity(intentAcc);
			break;
		case R.id.user_page_logout:
			Intent intentLogout = new Intent(this, LoginActivity.class);
			startActivity(intentLogout);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void display(){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_view1, menu);
		setListAdapter(adapter);
		Log.i(MainActivity.LOGTAG, "Refresh Account List");
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		switch(position) {
		case 0:
			Intent intent = new Intent(this, TransactionActivity.class);
			intent.putExtra("userid", user.getUserID());
			Log.i(MainActivity.LOGTAG, "Pass in userid");
			startActivity(intent);
			break;
		case 1:
			Intent spendingIntent = new Intent(this, SpendingReportActivity.class);
			spendingIntent.putExtra("userid", user.getUserID());
			Log.i(MainActivity.LOGTAG, "Pass in userid");
			startActivity(spendingIntent);
			break;
		default:
			break;
		}

	}

}
