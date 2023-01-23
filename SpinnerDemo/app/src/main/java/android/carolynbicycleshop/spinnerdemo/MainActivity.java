package android.carolynbicycleshop.spinnerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner dogSpinner=(Spinner)findViewById(R.id.dog_spinner);
        ArrayAdapter<CharSequence> dogAdapter=ArrayAdapter.createFromResource(this,R.array.dogs_array,android.R.layout.simple_spinner_item);
        dogAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        dogSpinner.setAdapter(dogAdapter);
        dogSpinner.setSelection(1);
        Object testSelection=dogSpinner.getSelectedItem();
        int index=dogSpinner.getSelectedItemPosition();
        
        Spinner catSpinner=(Spinner) findViewById(R.id.cat_spinner);
        ArrayList<Cats> myCats=new ArrayList<>();
        myCats.add(new Cats("persian","white"));
        myCats.add(new Cats("siamese","tan"));
        myCats.add(new Cats("ragdoll","black"));
        ArrayAdapter<Cats> catAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,myCats);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        catSpinner.setAdapter(catAdapter);
        catSpinner.setSelection(1);
        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,myCats.get(i).toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            // another interface callback
            }
        });

        Object testSelection2=catSpinner.getSelectedItem();
        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          showPopUpMenu(view);
                                      }
                                  });
    }
    private void showPopUpMenu(View view){
        PopupMenu popup= new PopupMenu(this,view);
        popup.inflate(R.menu.popup);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.goldfish:
                        Toast.makeText(MainActivity.this,"goldfish!",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.betafish:
                        Toast.makeText(MainActivity.this,"betafish!",Toast.LENGTH_LONG).show();
                        return true;
                    default:return false;
                }
            }
        });
        popup.show();
    }
}