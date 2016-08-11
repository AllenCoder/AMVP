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

package rubensousa.github.com.sampledi.utils;


import java.util.ArrayList;

import rubensousa.github.com.sampledi.data.model.User;

public class MockUtils {

    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Google", "www.google.com", "https://lh4.googleusercontent.com/-IhVc_Wxy6dY/AAAAAAAAAAI/AAAAAAAAFUw/YGRzJd5jolg/photo.jpg"));
        users.add(new User("Facebook", "www.facebook.com", "https://www.facebook.com/images/fb_icon_325x325.png"));
        users.add(new User("Twitter", "www.twitter.com", "https://pbs.twimg.com/profile_images/622211370247827456/VD0SebK3.png"));
        users.add(new User("Youtube", "www.youtube.com", "https://s.ytimg.com/yts/img/yt_1200-vfl4C3T0K.png"));
        return users;
    }
}
