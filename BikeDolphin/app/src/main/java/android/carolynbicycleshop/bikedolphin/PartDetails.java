package android.carolynbicycleshop.bikedolphin;

import androidx.appcompat.app.AppCompatActivity;

import android.carolynbicycleshop.bikedolphin.Database.Repository;
import android.carolynbicycleshop.bikedolphin.Entities.Part;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class PartDetails extends AppCompatActivity {
    String name;
    Double price;
    int partID;
    int prodID;
    EditText editName;
    EditText editPrice;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.partName);
        editName.setText(name);
        price = getIntent().getDoubleExtra("price", -1.0);
        editPrice = findViewById(R.id.partPrice);
        editPrice.setText(Double.toString(price));
        partID = getIntent().getIntExtra("id", -1);
        prodID = getIntent().getIntExtra("prodID", -1);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_partdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
//                Intent intent=new Intent(PartDetails.this,MainActivity.class);
//                startActivity(intent);
//                return true;

            case R.id.partsave:
                Part part;
                if (partID == -1) {
                    repository=new Repository(getApplication());
                    if (repository.getAllParts().size() == 0)
                        partID = 1;
                    else
                        partID = repository.getAllParts().get(repository.getAllParts().size() - 1).getPartID() + 1;
                    part = new Part(partID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), prodID);
                    repository.insert(part);
                } else {
                    part = new Part(partID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), prodID);
                    repository.update(part);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
