package com.example.hyoga.splitthecost;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // オブジェクトを取得
                EditText etxtNum     = (EditText)findViewById(R.id.inputPeople);
                EditText etxtMoney   = (EditText)findViewById(R.id.inputMoney);
                TextView txtResult   = (TextView)findViewById(R.id.result);

                //設定を取得
                SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                String frac=pref.getString(SettingPrefActivity.PREF_KEY_FRACTION,"500");
                Boolean roundup=pref.getBoolean(SettingPrefActivity.PREF_KEY_ROUNDUP,false);
                int fracVal=Integer.parseInt(frac);

                // 入力内容を取得
                String strNum   = etxtNum.getText().toString();
                String strMoney = etxtMoney.getText().toString();

                // 数値に変換
                int num   = Integer.parseInt(strNum);
                int money = Integer.parseInt(strMoney);

                // 割り勘計算
                int Result = money / num;

                //切り上げる
                if(roundup && Result%fracVal!=0){
                    Result+=fracVal;
                }

                //端数処理
                Result/=fracVal;
                Result*=fracVal;

                // 結果表示用テキストに設定
                txtResult.setText(Integer.toString(Result));

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_settings:
                Intent intent=new Intent(MainActivity.this,SettingPrefActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
