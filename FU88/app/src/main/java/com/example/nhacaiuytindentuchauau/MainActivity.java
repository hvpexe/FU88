package com.example.nhacaiuytindentuchauau;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView txtDiem;
    CheckBox cbOne, cbTwo, cbThree;
    SeekBar sbOne, sbTwo, sbThree;
    ImageButton ibPlay;
    int soDiem = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

        //an thanh seekbar
        sbOne.setEnabled(false);
        sbTwo.setEnabled(false);
        sbThree.setEnabled(false);

        txtDiem.setText(String.valueOf(soDiem));

        CountDownTimer countDownTimer = new CountDownTimer(30000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                int number = 5;
                Random random = new Random();
                int one     = random.nextInt(number);
                int two     = random.nextInt(number);
                int three   = random.nextInt(number);

                checkWinner(this, sbOne, "ONE WIN", cbOne);
                checkWinner(this, sbTwo, "TWO WIN", cbTwo);
                checkWinner(this, sbThree, "THREE WIN", cbThree);

                sbOne.setProgress(sbOne.getProgress() + one);
                sbTwo.setProgress(sbTwo.getProgress() + two);
                sbThree.setProgress(sbThree.getProgress() + three);
            }

            @Override
            public void onFinish() {

            }
        };

        ibPlay.setOnClickListener(v -> {
            if (cbOne.isChecked() || cbThree.isChecked() || cbTwo.isChecked()) {
                sbOne.setProgress(0);
                sbTwo.setProgress(0);
                sbThree.setProgress(0);
                ibPlay.setVisibility(View.INVISIBLE);
                countDownTimer.start();

                setEnabledAllCheckBox(false);
            } else {
                Toast.makeText(MainActivity.this, "Vui lòng đặt cược trước khi chơi! ", Toast.LENGTH_SHORT).show();
            }
        });

        setOnlyOneChoice(cbOne, cbTwo, cbThree);
        setOnlyOneChoice(cbTwo, cbOne, cbThree);
        setOnlyOneChoice(cbThree, cbOne, cbTwo);
    }

    private void setOnlyOneChoice(CheckBox cb, CheckBox cbUncheck1, CheckBox cbUncheck2) {
        cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cbUncheck1.setChecked(false);
                cbUncheck2.setChecked(false);
            }
        });
    }

    private void checkWinner(CountDownTimer countDownTimer, SeekBar sb, String message, CheckBox cb) {
        if (sb.getProgress() >= sb.getMax()) {
            countDownTimer.cancel();
            ibPlay.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

            //kiem tra dat cuoc
            if (cb.isChecked()) {
                soDiem += 10;
                Toast.makeText(MainActivity.this, "Bạn đoán chính xác", Toast.LENGTH_SHORT).show();
            } else {
                soDiem -= 5;
                Toast.makeText(MainActivity.this, "Bạn đoán sai rồi", Toast.LENGTH_SHORT).show();
            }

            txtDiem.setText(String.valueOf(soDiem));
            setEnabledAllCheckBox(true);
        }
    }
    private void setEnabledAllCheckBox(boolean enabled) {
        cbOne.setEnabled(enabled);
        cbTwo.setEnabled(enabled);
        cbThree.setEnabled(enabled);
    }
    private void AnhXa() {
        txtDiem = (TextView) findViewById(R.id.textviewDiemSo);
        ibPlay  = (ImageButton) findViewById(R.id.buttonPlay);
        cbOne   = (CheckBox) findViewById(R.id.checkboxOne);
        cbTwo   = (CheckBox) findViewById(R.id.checkboxTwo);
        cbThree = (CheckBox) findViewById(R.id.checkboxThree);
        sbOne   = (SeekBar) findViewById(R.id.seekbarOne);
        sbTwo   = (SeekBar) findViewById(R.id.seekbarTwo);
        sbThree = (SeekBar) findViewById(R.id.seekbarThree);
    }
}