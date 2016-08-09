/*
 * Copyright 2016 Rúben Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rubensousa.github.com.sampledi.ui.userlist;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import rubensousa.github.com.sampledi.R;
import rubensousa.github.com.sampledi.data.model.User;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    public static final String USERS_STATE = "users_state";

    private ArrayList<User> mUsers;

    public UserAdapter() {
        mUsers = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.urlTextView)
        TextView urlTextView;

        @BindView(R.id.imageView)
        CircleImageView imageView;

        @BindView(R.id.usernameTextView)
        TextView usernameTextView;

        @BindView(R.id.divider)
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
