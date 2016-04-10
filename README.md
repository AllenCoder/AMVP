# AMVP
A simple Model-View-Presenter library for Android

Presenters are retained using a singleton cache.

Please, check the sample projects.

## Minimum API: 11

## Build

Add the following to your build.gradle:

        repositories{
            maven { url "https://jitpack.io" }
        }
        
        dependencies {
            compile 'com.github.rubensousa:AMVP:{currentVersion}'
        }


## How to use

1. Create your CustomView interface:

        public interface CustomView extends MvpView<CustomPresenter>{
        
        }

2. Create your CustomPresenter interface:

        public interface CustomPresenter extends Presenter<CustomView>{
        
        }

3. Extend your activity from MvpAppCompatActivity or MvpActivity:

        public class MainActivity extends MvpAppCompatActivity<CustomView, CustomPresenter> implements CustomView {
        
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
            }
       
            @Override
            public CustomPresenter createPresenter() {
                return new CustomPresenterImpl();
            }
        }
        
4. Or your fragment from MvpFragment or MvpSupportFragment:

        public class MainFragment extends MvpSupportFragment<CustomView, CustomPresenter> implements CustomView {
      
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                return super.onCreateView(inflater, container, savedInstanceState);
            }
       
            @Override
            public CustomPresenter createPresenter() {
                return new CustomPresenterImpl();
            }
        }
        
5. Use getPresenter() in your View to get a reference to the current Presenter.


        public class MainFragment extends MvpSupportFragment<CustomView, CustomPresenter> implements CustomView {
      
            ...
            
            public void onClick(){
                getPresenter().doSomething();
            }
        }

6. (Optional) Extend your CustomPresenterImpl from AbstractPresenterImpl to avoid having to implement all of Presenter's methods each time.
        
        
That's it. Presenter's creation, caching and destruction gets handled automatically for you.

## Notes:

1. Override getPresenterKey() in your View to change the default key used to cache the presenter.
2. Only use the Presenter after onPostCreate() or onViewStateRestored()
3. Initialize the Presenter's state on onCreate(Bundle savedInstanceState)



## Networking sample

In this sample, you can find an example on how to persist network tasks.

The github public API is used to fetch users data: https://developer.github.com/v3/users/#get-all-users

The key is in the class MvpCallback, that receives life cycle events from the Presenter.


## Sample dependencies

##### Butterknife: https://github.com/square/okhttp
##### Retrofit: https://github.com/square/retrofit
##### Okhttp: https://github.com/square/okhttp
##### Gson: https://github.com/google/gson
##### Picasso: https://github.com/square/picasso
##### Icepick: https://github.com/frankiesardo/icepick
##### CircleImageView: https://github.com/hdodenhof/CircleImageView


## License

    Copyright 2016 Rúben Sousa
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.