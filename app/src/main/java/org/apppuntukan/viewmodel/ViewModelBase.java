package org.apppuntukan.viewmodel;

import androidx.lifecycle.ViewModel;

abstract class ViewModelBase extends ViewModel {
    public abstract <T> void updateData(T[] newData);
}
