package kr.co.teada.ex37datastorageinternel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {

    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et=findViewById(R.id.et);
        tv=findViewById(R.id.tv);

    }

    public void clickSave(View view) {

        //EditText에 있는 글씨를 얻어와서 내부 저장소에 파일로 저장
        String data=et.getText().toString(); //toString : 스트링으로 바꿔서 내놔
        et.setText("");

        //내부 저장소에 Data.txt라는 이름의 파일에 문자열 데이터 저장
        //액티비티 클래스에 이미 내부저장소(Internal Storage)에 File로 저장할 수 있는 Stream 생성 기능(메소드) 있어.
        //안드로이드는 앱에서 사용하는 내부저장소의 경로 고정되어있어. 내부저장소 여기밖에 못 써 (핸드폰마다 약간씩 달라 .."data/data/앱의 패키지명/files" 에 저장)

        try {
            FileOutputStream fos=openFileOutput("Data.txt", MODE_APPEND);
            PrintWriter writer=new PrintWriter(fos); //보조 문자열 스트림

            writer.println(data); //마치 System.out.println()처럼..사용 : 줄 바꿔서 쓰고 싶을 때
            writer.flush();
            writer.close();
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();


        } catch (FileNotFoundException e) {
           Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickLoad(View view) {

        try {
            FileInputStream fis=openFileInput("Data.txt"); //이미 내부에 있어서 ,new 아니고 open
            InputStreamReader isr=new InputStreamReader(fis); //문자스트림으로 바꿔
            BufferedReader reader=new BufferedReader(isr);

            StringBuffer buffer=new StringBuffer();

            String line=reader.readLine(); //println으로 했기 때문에 한줄 씩 읽어
            while(line!=null){
                buffer.append(line+"\n");
                line=reader.readLine();
            }
            tv.setText(buffer.toString());

            reader.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
