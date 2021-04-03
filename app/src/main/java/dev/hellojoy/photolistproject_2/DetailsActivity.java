package dev.hellojoy.photolistproject_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;

import dev.hellojoy.photolistproject_2.models.Photo;
import dev.hellojoy.photolistproject_2.network.ApiInterface;
import dev.hellojoy.photolistproject_2.network.NetworkClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView tvTitle;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageView = findViewById(R.id.image);
        tvTitle = findViewById(R.id.tv_title);

        apiInterface = NetworkClient.getRetrofitClient().create(ApiInterface.class);

        int id = getIntent().getIntExtra("id",0);

        getData(id);

    }

    private void getData(int id) {

        Call<Photo> call = apiInterface.getPhoto(id);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if (response.isSuccessful()){
                    Photo photo = response.body();
                    //Picasso.get().load(photo.url).into(imageView);
                    tvTitle.setText(photo.title);


                    Glide.with(DetailsActivity.this)
                            .asBitmap()
                            .load(photo.url)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    imageView.setImageBitmap(resource);
                                    imageView.buildDrawingCache();
                                }
                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) { }
                            });



                }else{
                    Toast.makeText(DetailsActivity.this, "error parsing", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "something went wrong ! check net connection", Toast.LENGTH_SHORT).show();
            }
        });

    }
}