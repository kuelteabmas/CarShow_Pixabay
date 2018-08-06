package com.devapp.dev_05.tutorecyclerviewvolleyjsonparsing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

// Adapter Class
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context mContext;
    private ArrayList<ItemDetail> mItemList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Constructor for our adapter
    public ItemAdapter(Context context, ArrayList<ItemDetail> itemList) {
        mContext = context;
        mItemList = itemList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    // To return a view holder
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_detail, parent, false);
        return new ItemViewHolder(view);
    }

    // To fetch and show the current item of our arraylist
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemDetail currentItem = mItemList.get(position);

        String imageUrl = currentItem.getImageUrl();
        String creatorName = currentItem.getCreator();
        int likeCount = currentItem.getLikeCount();

        holder.mTextViewCreator.setText(creatorName);
        holder.mTextViewLikes.setText("Likes: " + likeCount);
        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerInside()
                .into(holder.mImageView);

    }

    // Will return the exact amount in our adapter as we have in our ArrayList
    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    // ViewHolder Class
    public class ItemViewHolder extends RecyclerView.ViewHolder {

        //Defining variables
        public ImageView mImageView;
        public TextView mTextViewCreator;
        public TextView mTextViewLikes;

        // This will get references for our views from ItemDetails layout
        public ItemViewHolder(View itemView) {
            super(itemView);

            // Calling itemView for each variable defined above
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewCreator = itemView.findViewById(R.id.text_view_creator);
            mTextViewLikes = itemView.findViewById(R.id.text_view_likes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // First check to see if listener is not null, empty
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }


    }
}
