package dev.hellojoy.photolistproject_2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import dev.hellojoy.photolistproject_2.R;
import dev.hellojoy.photolistproject_2.models.Photo;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder>{

    private List<Photo> list;
    private Context context;
    private OnClickListener listener;

    public PhotoAdapter(List<Photo> list,Context context,OnClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }


    public interface OnClickListener{
        void OnPhotoItemClick(Photo photo);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Photo photo = list.get(position);
        GlideUrl url = new GlideUrl(photo.thumbnailUrl, new LazyHeaders.Builder()
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit / 537.36(KHTML, like Gecko) Chrome  47.0.2526.106 Safari / 537.36")
                .build());
        Glide.with(context).load(url).into(holder.imageView);
        holder.tvTitle.setText(photo.title);

        holder.itemView.setOnClickListener(view -> {
            listener.OnPhotoItemClick(photo);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView imageView;
        View item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            imageView = itemView.findViewById(R.id.image);
            item = itemView.findViewById(R.id.item);
        }
    }


}
