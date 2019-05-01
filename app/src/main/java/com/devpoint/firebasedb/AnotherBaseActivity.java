package com.devpoint.firebasedb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AnotherBaseActivity extends AppCompatActivity {

//	private TextView toolBarTitle;
//	private ImageView searchIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setUpToolBar(View toolBarLayout) {
        Toolbar toolbar = (Toolbar) toolBarLayout;

        setSupportActionBar(toolbar);
//		getSupportActionBar().setDisplayShowHomeEnabled(true);

//		toolBarTitle = toolBarLayout.findViewById(R.id.actionBarText);
        //		ImageView backButton = toolBarLayout.findViewById(R.id.backButton);
		/*backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});*/
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//		searchIcon = toolBarLayout.findViewById(R.id.searchIcon);

//		searchIcon.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Intent pla = new Intent(AnotherBaseActivity.this, SearchActivity.class);
//				startActivity(pla);
//			}
//		});
    }


//	protected void setTitle(String title) {
//		toolBarTitle.setText(title);
//	}

/*	protected void hideSearch() {

		searchIcon.setVisibility(View.GONE);
		toolBarTitle.setPadding((int) UnitConvert.dipToPixels(this, 30), 0, (int) UnitConvert.dipToPixels(this, 40), 0);
		searchIcon.setOnClickListener(null);
	}*/

//	protected void showSearch() {

//		searchIcon.setVisibility(View.VISIBLE);
//		toolBarTitle.setPadding((int) UnitConvert.dipToPixels(this, 30), 0, 0, 0);
    //searchIcon.setOnClickListener();
//	}
}
