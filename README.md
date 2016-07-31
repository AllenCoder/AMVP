# AMVP
A simple Model-View-Presenter library for Android.

This library was made because I wanted to learn how to implement MVP by myself. I'm using this on my personal projects, but you should take a look at [Mosby](https://github.com/sockeqwe/mosby) for a more complete solution.

Presenters are retained using a singleton cache.

Please, check the sample projects.

## Minimum API: 11

## Build

Add the following to your build.gradle:

```groovy
repositories{
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.rubensousa:AMVP:1.3.0'
}
```

## How to use

##### 1. Create your interfaces:

```java
public interface Custom {

   interface View<P extends Presenter> extends MvpView<P> {

   }
   
   interface Presenter<V extends View> extends MvpPresenter<V>{
   
   }

}
```

##### 2. Extend your activity from MvpAppCompatActivity or MvpActivity:

```java
public class MainActivity extends MvpAppCompatActivity<Custom.View, Custom.Presenter> implements Custom.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public Custom.Presenter createPresenter() {
        return new CustomPresenter();
    }
}
```
        
Or your fragment from MvpFragment or MvpSupportFragment:

```java
public class MainFragment extends MvpSupportFragment<Custom.View, Custom.Presenter> implements Custom.View {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Custom.Presenter createPresenter() {
        return new CustomPresenter();
    }
}
```
        
##### 3. Use getPresenter() in your View to get a reference to the current Presenter.

```java
public class MainFragment extends MvpSupportFragment<Custom.View, Custom.Presenter> implements CustomView {

    ...
    
    public void onClick(){
        getPresenter().doSomething();
    }
}
```

##### 4. (Optional) Extend your CustomPresenter from AbstractPresenter to avoid having to implement all of Presenter's methods each time.

##### 5. (Optional) Create a Interactor. (Extend your Presenter from MvpPresenterInteractor instead)

```java
public interface Custom {

    interface Presenter<V extends View, I extends Interactor> extends MvpPresenterInteractor<V, I> {

    }

    interface Interactor extends MvpInteractor {

    }
}
```

And then, in your Presenter (that extends from AbstractPresenterInteractor):

```java
public class CustomPresenter extends AbstractPresenterInteractor<Custom.View, Custom.Interactor>
        implements Custom.Presenter{

    @Override
    public Custom.Interactor createInteractor() {
        return new CustomInteractor();
    }
    
    @Override
    public void getData() {

        if(isViewAttached()){
            getView().showProgress();
        }
        
        getInteractor().load(new LoadListener() {
            
            @Override
            public void onLoad(){
                if(isViewAttached()){
                    getView().hideProgress();
                }
            }
        });
    }
    
```
        
        
That's it. Presenter's creation, caching and destruction gets handled automatically for you.

## Notes:

1. Override getPresenterKey() in your View to change the default key used to cache the presenter.
2. Only use the Presenter after onPostCreate() or onViewStateRestored(), since the view won't be attached yet.
3. Initialize the Presenter's state on onCreate(Bundle savedInstanceState)



## Networking sample

In this sample, you can find an example on how to persist network tasks.

The github public API is used to fetch users data: https://developer.github.com/v3/users/#get-all-users

This is solved by caching the network response while the view isn't attached. After the view is attached, the network response is then delivered.


## Sample dependencies

##### Butterknife: https://github.com/JakeWharton/butterknife
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
