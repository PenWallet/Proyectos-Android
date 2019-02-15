package com.example.ofunes.pennypanphone;

import android.content.Context;
import android.support.v14.preference.MultiSelectListPreference;
import android.support.v7.preference.internal.AbstractMultiSelectListPreference;
import android.util.AttributeSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ofunes on 15/02/19.
 */

public class MyMultiSelectListPreference extends MultiSelectListPreference {
    public MyMultiSelectListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyMultiSelectListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyMultiSelectListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyMultiSelectListPreference(Context context) {
        super(context);
    }

    @Override
    public void setValues( Set<String> values ) {

        //Workaround for https://code.google.com/p/android/issues/detail?id=22807
        final Set<String> newValues = new HashSet<String>();
        newValues.addAll( values );
        super.setValues( newValues );
    }
}
