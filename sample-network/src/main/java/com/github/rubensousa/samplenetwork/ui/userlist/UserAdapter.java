package com.github.rubensousa.samplenetwork.ui.userlist;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.rubensousa.samplenetwork.R;
import com.github.rubensousa.samplenetwork.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    public static final String USERS_STATE = "users_state";

    private ArrayList<User> mUsers;

    public UserAdapter() {
        mUsers = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.urlTextView)
        TextView urlTextView;

        @Bind(R.id.imageView)
        CircleImageView imageView;

        @Bind(R.id.usernameTextView)
        TextView usernameTextView;

        @Bind(R.id.divider)
        View divider;

        User user;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(User user) {
            this.user = user;
            Picasso.with(imageView.getContext()).load(user.avatarUrl).fit().into(imageView);
            usernameTextView.setText(user.username);
            urlTextView.setText(user.url);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mUsers.get(position));
        holder.divider.setVisibility(position == mUsers.size() - 1 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void deleteAll() {
        int size = mUsers.size();
        mUsers.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void setUsers(ArrayList<User> users) {
        boolean empty = mUsers.isEmpty();
        mUsers = users;
        if (empty) {
            notifyItemRangeInserted(0, mUsers.size());
        } else {
            notifyDataSetChanged();
        }
    }

    public void addUsers(ArrayList<User> users) {
        int originalSize = mUsers.size() - 1;
        mUsers.addAll(users);
        notifyItemRangeInserted(originalSize, users.size());
    }

    public void saveState(Bundle outState) {
        outState.putParcelableArrayList(USERS_STATE, mUsers);
    }

    public void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mUsers = savedInstanceState.getParcelableArrayList(USERS_STATE);
            if (mUsers != null) {
                notifyDataSetChanged();
            }
        }
    }

}
