package com.example.khanhpham.calculator;

import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.*;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    public EditText hienThi;
    public TextView ketQua;
    public ImageButton changeLandscape;
    public ImageButton changePortrait;
    private ToggleButton radToDeg;
    private Button btnGcd, btnLcm;
    String output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //edit text
        hienThi = findViewById(R.id.txthienThi);
        hienThi.setShowSoftInputOnFocus(false);  //khong hien ban phim mac dinh khi cham vao edit text
        ketQua = findViewById(R.id.txtketqua);
        ketQua.setShowSoftInputOnFocus(false);
        //Toggle button
        radToDeg = findViewById(R.id.radianAndDegrees);
        //Button
        btnGcd = findViewById(R.id.btnUCLN);
        btnLcm = findViewById(R.id.btnBCNN);
        //Image Button
        changeLandscape = findViewById(R.id.btnLandscape);
        changePortrait = findViewById(R.id.btnPortrait);

        hienThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getString(R.string.display).equals(hienThi.getText().toString())){
                    hienThi.setText("");
                }
            }
        });
    }

    //Cap nhat text
    public void updateText(String strAdd){
        String oldStr = hienThi.getText().toString();
        int viTriConTro = hienThi.getSelectionStart();
        String leftStr = oldStr.substring(0,viTriConTro);
        String rightStr = oldStr.substring(viTriConTro);
        if(getString(R.string.display).equals(hienThi.getText().toString())){
            hienThi.setText(strAdd);
            hienThi.setSelection(viTriConTro + strAdd.length());
        }else {
            hienThi.setText(String.format("%s%s%s", leftStr, strAdd, rightStr));
            hienThi.setSelection(viTriConTro + strAdd.length());
        }
    }
    //chuyen sang che do landscape
    public void setChangeLandscape(View view){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    //chuyen sang che do portrait
    public void setChangePortrait(View view){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    public void xoa(View view){
        hienThi.setText("");
        ketQua.setText("");
    }
    public void ngoac(View view){
        int viTriConTro = hienThi.getSelectionStart();
        int textLength = hienThi.getText().length();
        int ngoacMo = 0;
        int ngoacDong = 0;
        for(int i=0; i<viTriConTro; i++){
            if(hienThi.getText().toString().substring(i, i+1).equals("(")){
                ngoacMo += 1;
            }
            if(hienThi.getText().toString().substring(i, i+1).equals(")")){
                ngoacDong += 1;
            }
        }
        if(ngoacMo == ngoacDong || hienThi.getText().toString().substring(textLength-1, textLength).equals("(")){
            updateText("(");
        }
        else if(ngoacDong < ngoacMo && !hienThi.getText().toString().substring(textLength-1, textLength).equals("(")){
            updateText(")");
        }
        hienThi.setSelection(viTriConTro + 1);
    }
    public void loaiBo(View view){
        int viTri = hienThi.getSelectionStart();
        int textLength = hienThi.getText().length();
        if(viTri != 0 && textLength != 0){
            SpannableStringBuilder selection = (SpannableStringBuilder) hienThi.getText();
            selection.replace(viTri-1, viTri, "");
            hienThi.setText(selection);
            hienThi.setSelection(viTri-1);
        }
    }
    public void bang(View view){
        String duLieu = hienThi.getText().toString();
        duLieu = duLieu.replaceAll("÷","/");
        duLieu = duLieu.replaceAll("×", "*");
        Expression expression = new Expression(duLieu);
        String kq = String.valueOf(expression.calculate());
        output = toDecimal(kq);
        hienThi.setText(output);
        hienThi.setSelection(output.length());
        ketQua.setText(output);
    }
    //Chuyen doi radian - degrees
    public void setRadToDeg(View view){
        radToDeg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String duLieu = ketQua.getText().toString();
                Double deg, rad;
                if (isChecked) { //che do degrees
                    deg = 0.0174532925 * Double.valueOf(duLieu);
                    ketQua.setText(String.valueOf(deg));
                } else {          //che do radian
                    rad = 57.2957795 * Double.valueOf(duLieu);
                    ketQua.setText(String.valueOf(rad));
                }
            }
        });
    }

    //xoa dau phay dong
    private String toDecimal(String number){
        String n[] = number.split("\\.");
        if(n.length>1){
            if(n[1].equals("0")){
                number = n[0];
            }
        }
        return number;
    }

    public void luuGiaTri(View view){
        if(output!=null) {
            ketQua.setText(output);
            hienThi.setText(output);
            hienThi.setSelection(output.length());
        }else{
            ketQua.setText("");
            hienThi.setText("");
        }
    }
    public void phanTram(View view){
        updateText("%");
    }
    public void canBacHai(View view){
        updateText("√(");
    }
    public void chia(View view){
        updateText("÷");
    }
    public void nhan(View view){
        updateText("×");
    }
    public void tru(View view){
        updateText("-");
    }
    public void cong(View view){
        updateText("+");
    }
    public void soKhong(View view){
        updateText("0");
    }
    public void soMot(View view){
        updateText("1");
    }
    public void soHai(View view){
        updateText("2");
    }
    public void soBa(View view){
        updateText("3");
    }
    public void soBon(View view){
        updateText("4");
    }
    public void soNam(View view){
        updateText("5");
    }
    public void soSau(View view){
        updateText("6");
    }
    public void soBay(View view){
        updateText("7");
    }
    public void soTam(View view){
        updateText("8");
    }
    public void soChin(View view){
        updateText("9");
    }
    public void soPi(View view){
        updateText("π");
    }
    public void soE(View view){
        updateText("e");
    }
    public void mu(View view){
        updateText("^");
    }
    public void giaiThua(View view){
        updateText("!");
    }
    public void amDuong(View view){
        updateText("-");
    }
    public void dauPhay(View view){
        updateText(".");
    }
    public void sin(View view) {
        updateText("sin(");
    }
    public void cos(View view) {
        updateText("cos(");
    }
    public void tan(View view) {
        updateText("tan(");
    }
    public void arcsin(View view) {
        updateText("arcsin(");
    }
    public void arccos(View view) {
        updateText("arccos(");
    }
    public void arctan(View view) {
        updateText("arctan(");
    }
    public void logarit(View view) {
        updateText("lg(");
    }
    public void ln(View view) {
        updateText("ln(");
    }
    public void triTuyetDoi(View view) {
        updateText("abs(");
    }
    public void luyThua(View view) {
        updateText("^");
    }
    public void binhPhuong(View view) {
        updateText("^(2)");
    }
    public void canBacBa(View view) {
        updateText("∛(");
    }
    public void nghichDao(View view) {
        updateText("1÷");
    }
    public void UCLN(View view){
        updateText("gcd(");
    }
    public void BCNN(View view){
        updateText("lcm(");
    }
    public void chamPhay(View view){
        updateText(";");
    }

    //find GCD
    private int gcd(int a, int b){
        while (b!=0){
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}