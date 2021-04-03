package dev.hellojoy.photolistproject_2.network;

import java.util.List;

import dev.hellojoy.photolistproject_2.models.Photo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("photos")
    Call<List<Photo>> getPhotos();

    @GET("photos/{id}")
    Call<Photo> getPhoto(@Path("id") int id);

}
