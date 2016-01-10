package vda.home.qstquizz_2;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FileSelectActivity extends AppCompatActivity {

    String filePath;
    List<String> FilePathRouteRecorder = new ArrayList<String>();
    List<String> FilesLister = new ArrayList<String>();

    @Nullable
    static Boolean isDir(String path) {
        File file = new File(path);
        if (file.exists()) // First, make sure the path exists
        {
            // This will tell you if it is a directory
            return file.isDirectory();
        } else {
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_select);

        final ListView lw = (ListView) this.findViewById(R.id.listOfFiles);
        final AssetManager am = FileSelectActivity.this.getAssets();
        String[] tt = null;
        try {
            tt = am.list("KROK");
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }
        FilePathRouteRecorder.add("KROK\\");
        FilesLister = Arrays.asList(tt);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FilesLister);
        lw.setAdapter(adapter);
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                filePath = FilePathRouteRecorder.toString() + FilesLister.get(position);
                if (!isDir(filePath)) {
                    Intent answerIntent = new Intent();
                    answerIntent.putExtra("vda.home.qstquizz_2.PATH", filePath);
                    setResult(RESULT_OK, answerIntent);
                    finish();
                } else if (isDir(filePath)) {
                    String[] tt = null;
                    try {
                        tt = am.list(filePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                        finish();
                    }
                    FilesLister = Arrays.asList(tt);
                    FilePathRouteRecorder.add(FilesLister.get(position) + "\\");
                    adapter.notifyDataSetChanged();
                } else {
                    if (isDir(filePath) == null) {
                        onDestroy();
                    }
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        throw new NullPointerException("Invalid file path. It does not exist. Quitting activity...");
    }

}
