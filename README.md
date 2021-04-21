# MVVM_architecture
 
![alt text][img1]

![alt text][img2]






**git checkout ProductViewModel_Extends_ViewModel:** The version of this example where the ProductViewModel class extends the "androidx.lifecycle.ViewModel"

**git checkout ProductViewModel_DOES_NOT_Extend_ViewModel** The version of this example where the ProductViewModel class DOES NOT *extends* the "androidx.lifecycle.ViewModel", but with lifecycle awareness managing.


## **Why "ProductViewModel" class extends the "androidx.lifecycle.ViewModel"?**

A ViewModel is always created in association with a scope of (fragment/activity) and will be retained as long as the scope is alive.

In other words, this means that a ViewModel will not be destroyed if its owner is destroyed for a configuration change (e.g. screen rotation). The new instance of the owner will just re-connected to the existing ViewModel.

That is also why we initiate "ProductViewModel" inside the MainActivity as flws:

```java 
productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
```

instead of using its constructor 
```java 
productViewModel = new ProductViewModel();
```

## **Lifecycle awareness even if "ProductViewModel" does not extends ViewModel:**
    If we intiate an instance of "ProductViewModel" by its constructor, the "ProductViewModel" will not be aware of the (fragment/activity)'s lifecycle. Which means that in case of configuration change (e.g. screen rotation), the state of ProductViewModel will start over and the data will not be persistent.

    To fix that we have to assert that ProductViewModel is recreated only in case it is started for the first time.
    One way to do that is by checking if (savedInstanceState == null) then create new instance of ProductViewModel, Otherwise retrieve the prev saved instance of "ProductViewModel", and also we have to save the "ProductViewModel's instance" inside the overriden method "protected void onSaveInstanceState(@NonNull Bundle outState) {..}"
    Note: In order to save an instance of ProductViewModel, it has either to:
        - implement the "Parcelable interface with writeToParcel() and createFromParcel() methods".
        - or use "Parceler" framework which simplify the management of Parcelable objects.By adding the Parceler dependency to app/gradle, and annotate ProductViewModel and (any member fields' classes if necessary) with @Parcel.
![alt text][img3]

 
[img1]: android-mvvm-architecture.png
[img2]: android-mvvm-tutorial.png
[img3]: android-mvvm-persisting-state-with-ViewModel.png
