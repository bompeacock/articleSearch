package com.example.cong.articlesearch.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cong.articlesearch.R;
import com.example.cong.articlesearch.model.SearchRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogActivity extends AppCompatActivity {
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.spSort)
    Spinner spSort;
    @BindView(R.id.cbArts)
    CheckBox cbArts;
    @BindView(R.id.cbFashionAndStyle)
    CheckBox cbFashionAndStyle;
    @BindView(R.id.cbSports)
    CheckBox cbSports;
    @BindView(R.id.btnDate)
    ImageButton btnDate;
    @BindView(R.id.btnSave)
    Button btnSave;
    Intent intent;
    SearchRequest searchRequest;
    List<String> listdesk = new ArrayList<>();
    ArrayAdapter<String> adapter;
    Calendar calendar = Calendar.getInstance();
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);

//        intent = getIntent();
//        searchRequest = (SearchRequest) intent.getSerializableExtra("search");
//        setUpViews(searchRequest);
//        setEvents();

    }

    private void setEvents() {
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {
        searchRequest.setBeginDate(txtDate.getText().toString());
        searchRequest.setSort(spSort.getSelectedItem().toString());
        if(cbSports.isChecked()) searchRequest.setDeskSports(true);
        if(cbFashionAndStyle.isChecked()) searchRequest.setDeskFashionAndStyle(true);
        if(cbArts.isChecked())searchRequest.setDeskArts(true);

        intent.putExtra("searchBack",searchRequest);
        setResult(2,intent);
        finish();
    }

    private void pickDate() {
        DatePickerDialog.OnDateSetListener callback =  new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                txtDate.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };
        DatePickerDialog dialog = new DatePickerDialog(
                DialogActivity.this,
                callback,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void setUpViews(SearchRequest searchRequest) {
        listdesk.add("newest");listdesk.add("oldest");
        adapter = new ArrayAdapter<String>(DialogActivity.this,android.R.layout.activity_list_item,listdesk);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSort.setAdapter(adapter);
        if(searchRequest!=null){
            txtDate.setText(searchRequest.getBeginDate());
            switch (searchRequest.getNewsDesk()){
                case "oldest":
                    spSort.setSelection(1);
                    break;
                default:
                    spSort.setSelection(0);
                    break;
            }
            if(searchRequest.isDeskArts()) cbArts.setChecked(true);
            if(searchRequest.isDeskFashionAndStyle())cbFashionAndStyle.setChecked(true);
            if(searchRequest.isDeskSports()) cbSports.setChecked(true);
        }

    }


}
