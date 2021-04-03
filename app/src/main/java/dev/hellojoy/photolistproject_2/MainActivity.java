package dev.hellojoy.photolistproject_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dev.hellojoy.photolistproject_2.adapters.PhotoAdapter;
import dev.hellojoy.photolistproject_2.models.Photo;
import dev.hellojoy.photolistproject_2.network.ApiInterface;
import dev.hellojoy.photolistproject_2.network.NetworkClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PhotoAdapter.OnClickListener{

    private ApiInterface apiInterface;
    private PhotoAdapter adapter;
    private RecyclerView recyclerView;
    private List<Photo> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.recyclerView);

        apiInterface = NetworkClient.getRetrofitClient().create(ApiInterface.class);

        adapter = new PhotoAdapter(list,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        getData();

    }


    private void getData(){
        list.clear();
        adapter.notifyDataSetChanged();
        Call<List<Photo>> call = apiInterface.getPhotos();
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if(response.isSuccessful()){
                    list.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this, "error parsing", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "something went wrong ! check net connection", Toast.LENGTH_SHORT).show(); }
        });
    }

    @Override
    public void OnPhotoItemClick(Photo photo) {
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra("id",photo.id);
        startActivity(intent);
    }
}